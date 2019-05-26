package com.tensquare.article.controller;

import com.tensquare.article.service.ArticleService;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public ResponseResult findById(@PathVariable String id){
        return ResponseResult.SUCCESS(articleService.findById(id));
    }

    // 审核
    @PutMapping("/examine/{id}")
    public ResponseResult examine(@PathVariable String id){
        articleService.examine(id);
        return ResponseResult.SUCCESS();
    }

    // 点赞
    @PutMapping("/thumbup/{id}")
    public ResponseResult updateThumbup(@PathVariable String id){
        articleService.updateThumbup(id);
        return ResponseResult.SUCCESS();
    }

}
