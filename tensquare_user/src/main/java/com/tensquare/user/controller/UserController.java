package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 获取验证码
     */
    @PostMapping("/sendsms/{mobile}")
    public ResponseResult sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return ResponseResult.SUCCESS();
    }

    /**
     * 用户注册
     */
    @PostMapping("/register/{code}")
    public ResponseResult registerUser(@PathVariable String code, @RequestBody User user){
        userService.add(user, code);
        return ResponseResult.SUCCESS();
    }

}
