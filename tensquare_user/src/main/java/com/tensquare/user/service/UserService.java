package com.tensquare.user.service;

import com.tensquare.tools.JwtUtil;
import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import com.tensquare.utils.IdWorker;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 发送短信验证码
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void sendSms(String mobile){
        // 1. 生成6位短信验证码
//        Random random = new Random();
//        int max = 999999;
//        int min = 100000;
//        int code = random.nextInt(max);
//        if(code < min){
//            code = code + min;
//        }

        // 2. apache commons lang3 生成随机数
        int random = RandomUtils.nextInt(100000, 999999);
        System.out.println(mobile + "收到的验证是 code = " + random);
        String code = String.valueOf(random);
        // 2. 将验证码放入Redis
        redisTemplate.opsForValue().set("smscode_"+mobile, code+"", 5, TimeUnit.MINUTES);

        // 3.将验证码和手机号发送到rabbitmq 中
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        rabbitTemplate.convertAndSend("tensquare.direct", "tensquare.code", map);
    }


    /**
     * 增加用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, String> add(User user, String code){
        String syscode = redisTemplate.opsForValue().get("smscode_"+user.getMobile());
        if(StringUtils.isEmpty(syscode)){
            throw new RuntimeException("请输入验证码");
        }
        if(!code.equals(syscode)){
            throw new RuntimeException("验证码错误");
        }
        User exits = userDao.findByMobile(user.getMobile());
        if(exits != null){
            throw new RuntimeException("不要重复操作");
        }
        String id = String.valueOf(idWorker.nextId());
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        String encode = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setId(id);
        newUser.setPassword(encode);
        newUser.setFollowcount(0);
        newUser.setFanscount(0);
        newUser.setOnline(0L);
        newUser.setRegdate(new Date());
        newUser.setUpdatedate(new Date());
        newUser.setLastdate(new Date());
        userDao.save(newUser);
        return getStringStringMap(user);
    }


    /**
     * 用户登录
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, String> login(String mobile, String password) {
        User user = userDao.findByMobile(mobile);
        if(user != null && bCryptPasswordEncoder.matches(password, user.getPassword())){
            return getStringStringMap(user);
        }else {
            return null;
        }
    }

    /**
     * 获取token
     */
    private Map<String, String> getStringStringMap(User user) {
        String token = jwtUtil.createJWT(user.getId(), user.getLoginname(), "normal");
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("loginname", user.getLoginname());
        return map;
    }

}
