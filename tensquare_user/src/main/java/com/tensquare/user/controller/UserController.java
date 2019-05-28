package com.tensquare.user.controller;

import com.tensquare.tools.ResponseResult;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
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

}
