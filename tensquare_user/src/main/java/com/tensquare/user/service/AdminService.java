package com.tensquare.user.service;

import com.tensquare.tools.JwtUtil;
import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.Admin;
import com.tensquare.utils.IdWorker;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 注册
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Admin admin){
        String id = String.valueOf(idWorker.nextId());
        admin.setId(id);
        String encode = bCryptPasswordEncoder.encode(admin.getPassword());
        admin.setPassword(encode);
        adminDao.save(admin);
    }

    /**
     * 登录
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, String> findByloginnameAndPassword(String loginname, String password){
        Admin admin = adminDao.findByLoginname(loginname);
        if(admin != null && bCryptPasswordEncoder.matches(password, admin.getPassword())){
            return getStringStringMap(admin);
        }else {
            return null;
        }
    }


    /**
     * 删除用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int dele(String id, HttpServletRequest request) {
        Claims attribute = (Claims) request.getAttribute("admin_claims");
        if(attribute == null){
            return 1;
        }else {
            userDao.deleteById(id);
            return 2;
        }
    }


    /**
     * 获取token
     */
    private Map<String, String> getStringStringMap(Admin admin) {
        String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("loginname", admin.getLoginname());
        return map;
    }

}
