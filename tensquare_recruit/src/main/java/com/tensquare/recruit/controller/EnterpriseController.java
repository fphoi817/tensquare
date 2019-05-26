package com.tensquare.recruit.controller;

import com.tensquare.recruit.service.EnterpriseService;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) { this.enterpriseService = enterpriseService; }

    // 查询热门企业
    @GetMapping("/search/hotlist")
    public ResponseResult hotlist(){
        return ResponseResult.SUCCESS(enterpriseService.findByIshot());
    }

}
