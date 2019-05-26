package com.tensquare.user.controller;

import com.tensquare.tools.ResponseResult;
import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Api(tags = "admin用户模块")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 注册
     */
    @ApiOperation(value = "admin 注册")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody Admin admin){
        adminService.add(admin);
        return ResponseResult.SUCCESS();
    }

    /**
     * 登录
     */
    @ApiOperation(value = "admin 登录")
    @GetMapping("/login")
    public ResponseResult login(@RequestParam String loginname, @RequestParam String password){
        Map<String, String> admin = adminService.findByloginnameAndPassword(loginname, password);
        if(admin != null){
            return ResponseResult.SUCCESS(admin);
        }else {
            return ResponseResult.FAILED("用户名或密码错误");
        }
    }


    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseResult dele(@PathVariable String id, HttpServletRequest request){
        int dele = adminService.dele(id, request);
        if(dele == 1){
            return ResponseResult.UNAUTHORIZED(request.getRequestURI());
        }else {
            return ResponseResult.SUCCESS();
        }
    }
}
