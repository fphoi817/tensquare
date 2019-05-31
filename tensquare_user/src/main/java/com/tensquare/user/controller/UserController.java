package com.tensquare.user.controller;

import com.tensquare.tools.ResponseResult;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 获取验证码
     */
    @GetMapping("/sendsms/{mobile}")
    public ResponseResult sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return ResponseResult.SUCCESS();
    }

    /**
     * 用户注册
     */
    @PostMapping("/register/{code}")
    public ResponseResult registerUser(@PathVariable String code, @RequestBody User user){
        return ResponseResult.SUCCESS(userService.add(user, code));
    }

    /**
     * 用户登录
     */
    @GetMapping("/login")
    public ResponseResult loginUser(@RequestParam String mobile, @RequestParam String password){
        Map<String, String> login = userService.login(mobile, password);
        if(login != null){
            return ResponseResult.SUCCESS(login);
        }else {
            return ResponseResult.FAILED("用户名或密码错误");
        }
    }

    /**
     * 更新粉丝数
     */
    @PutMapping("/incfans/{userid}/{x}")
    public void incFanscount(@PathVariable String userid, @PathVariable int x){
        userService.incFanscount(userid, x);
    }

    /**
     * 变更关注数
     */
    @PutMapping("/incfollow/{userid}/{x}")
    public void incFollowcount(@PathVariable String userid, @PathVariable int x){
        userService.incFollowcount(userid, x);
    }
}
