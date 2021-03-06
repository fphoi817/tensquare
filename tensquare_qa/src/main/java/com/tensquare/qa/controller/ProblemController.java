package com.tensquare.qa.controller;

import com.tensquare.qa.client.BaseLabelClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import com.tensquare.tools.PageResult;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RefreshScope
@RequestMapping("/qa")
public class ProblemController {

    private final ProblemService problemService;
    private final BaseLabelClient baseLabelClient;

    @Autowired
    public ProblemController(ProblemService problemService, BaseLabelClient baseLabelClient){
        this.problemService = problemService;
        this.baseLabelClient = baseLabelClient;
    }

    // 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最新回复时间排序
    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public ResponseResult findNewListByLabelId(@PathVariable String labelid,
                                               @PathVariable int page,
                                               @PathVariable int size){
        Page<Problem> problemList = problemService.findNewListByLabelId(labelid, page, size);
        return ResponseResult.SUCCESS(new PageResult<>(problemList.getTotalElements(), problemList.getContent()));
    }

    // 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最多回复数排序
    @GetMapping("/hotlist/{labelid}/{page}/{size}")
    public ResponseResult findHotListByLabelId(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){
        Page<Problem> problemPage = problemService.findHotListByLabelId(labelid, page, size);
        return ResponseResult.SUCCESS(new PageResult<>(problemPage.getTotalElements(), problemPage.getContent()));
    }

    // 未解决 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最新时间排序
    @GetMapping("/waitlist/{labelid}/{page}/{size}")
    public ResponseResult findWaitListByLabelId(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){
        Page<Problem> problemPage = problemService.findWaitListByLabelId(labelid, page, size);
        return ResponseResult.SUCCESS(new PageResult<>(problemPage.getTotalElements(), problemPage.getContent()));
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody Problem problem){
        problemService.add(problem);
        return ResponseResult.SUCCESS();
    }

    // Spring cloud 连接测试
    @GetMapping("/label/findById/{labelId}")
    public ResponseResult findByLabelId(@PathVariable String labelId){
        return baseLabelClient.findById(labelId);
    }
}
