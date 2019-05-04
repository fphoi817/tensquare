package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class BaseController {

    private final LabelService LABELSERVICE;

    @Autowired
    public BaseController(LabelService labelService) {
        this.LABELSERVICE = labelService;
    }

    @GetMapping("/findAll")
    public ResponseResult findAll() {
        return ResponseResult.SUSSCESS(LABELSERVICE.findAll());
    }


    @GetMapping("/findById/{labelId}")
    public ResponseResult findById(@PathVariable String labelId) {
        return ResponseResult.SUSSCESS(LABELSERVICE.findById(labelId));
    }


    @PostMapping("/save")
    public ResponseResult save(@RequestBody Label label) {
        LABELSERVICE.save(label);
        return ResponseResult.SUSSCESS();
    }


    @PutMapping("/update/{labelId}")
    public ResponseResult update(@PathVariable String labelId, @RequestBody Label label) {
        label.setId(labelId);
        LABELSERVICE.update(label);
        return ResponseResult.SUSSCESS();
    }


    @DeleteMapping("/deleById/{labelId}")
    public ResponseResult deleById(@PathVariable String labelId) {
        LABELSERVICE.deleById(labelId);
        return ResponseResult.SUSSCESS();
    }
}
