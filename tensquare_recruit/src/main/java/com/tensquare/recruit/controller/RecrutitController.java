package com.tensquare.recruit.controller;

import com.tensquare.recruit.service.RecruitService;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RefreshScope
@RequestMapping("/recruit")
public class RecrutitController {

    private final RecruitService recruitService;
    @Autowired
    public RecrutitController(RecruitService recruitService){ this.recruitService = recruitService; }

    // 推荐职位
    @GetMapping("/search/recmmend")
    public ResponseResult findTop4ByStateOrderByCreatetimeDesc(){
        return ResponseResult.SUCCESS(recruitService.findTop4ByStateOrderByCreatetimeDesc());
    }

    // 最新职位
    @GetMapping("/search/newlist")
    public ResponseResult findTop12ByStateNotOrderByCreatetimeDesc(){
        return ResponseResult.SUCCESS(recruitService.findTop12ByStateNotOrderByCreatetimeDesc());
    }
}
