package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import com.tensquare.tools.PageResult;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/label")
public class LabelController {

    private final LabelService LABELSERVICE;

    @Value("${ip}")
    private String ip;

    @Autowired
    public LabelController(LabelService labelService) {
        this.LABELSERVICE = labelService;
    }

    @GetMapping("/findAll")
    public ResponseResult findAll() {
        return ResponseResult.SUCCESS(LABELSERVICE.findAll());
    }


    @GetMapping("/findById/{labelId}")
    public ResponseResult findById(@PathVariable String labelId) {
        System.out.println(ip);
        return ResponseResult.SUCCESS(LABELSERVICE.findById(labelId));
    }


    @PostMapping("/save")
    public ResponseResult save(@RequestBody Label label) {
        LABELSERVICE.save(label);
        return ResponseResult.SUCCESS();
    }


    @PutMapping("/update/{labelId}")
    public ResponseResult update(@PathVariable String labelId, @RequestBody Label label) {
        label.setId(labelId);
        LABELSERVICE.update(label);
        return ResponseResult.SUCCESS();
    }


    @DeleteMapping("/deleById/{labelId}")
    public ResponseResult deleById(@PathVariable String labelId) {
        LABELSERVICE.deleById(labelId);
        return ResponseResult.SUCCESS();
    }


    @PostMapping("/search")
    public ResponseResult findSearch(@RequestBody Label label){
        List<Label> list = LABELSERVICE.findSearch(label);
        return ResponseResult.SUCCESS(list);
    }

    @PostMapping("/search/{page}/{size}")
    public ResponseResult pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size){
        Page<Label> pageData = LABELSERVICE.pageQuery(label, page, size);
        return ResponseResult.SUCCESS(new PageResult<Label>(pageData.getTotalElements(), pageData.getContent()));
    }

}
