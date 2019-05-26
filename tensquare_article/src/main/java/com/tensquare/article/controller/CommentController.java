package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody Comment comment){
        commentService.add(comment);
        return ResponseResult.SUCCESS();
    }

    @GetMapping("/article/{articleid}")
    public ResponseResult findByArticleid(@PathVariable String articleid){
        return ResponseResult.SUCCESS(commentService.findByArticleid(articleid));
    }

}
