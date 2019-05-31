package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import com.tensquare.tools.PageResult;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RefreshScope
@RequestMapping("/spit")
public class SpitController {

    private final SpitService spitService;
    @Autowired
    public SpitController(SpitService spitService) {
        this.spitService = spitService;
    }

    // 增加
    @PostMapping("/add")
    public ResponseResult conAdd(@RequestBody Spit spit){
        spitService.addSer(spit);
        return ResponseResult.SUCCESS();
    }

    // 修改
    @PutMapping("/update/{id}")
    public ResponseResult updateCon(@PathVariable String id, @RequestBody Spit spit){
        spitService.updateSer(id, spit);
        return ResponseResult.SUCCESS();
    }

    // 删除
    @DeleteMapping("/dele/{id}")
    public ResponseResult deleteByIdCon(@PathVariable String id){
        spitService.deleteByIdSer(id);
        return ResponseResult.SUCCESS();
    }


    // 查询所有
    @GetMapping("/findall")
    public ResponseResult findAllCon(){
        return ResponseResult.SUCCESS(spitService.findAllSer());
    }

    // 根据ID查询
    @GetMapping("/findbyid/{id}")
    public ResponseResult findByIdCon(@PathVariable String id){
        return ResponseResult.SUCCESS(spitService.findByIdSer(id));
    }

    // 根据上级ID 查询 并分页
    @GetMapping("/findbyparentid/{id}/{page}/{size}")
    public ResponseResult findByParentidCon(@PathVariable String id, @PathVariable int page, @PathVariable int size){
        Page<Spit> spitPage = spitService.findByParentidSer(id, page, size);
        return ResponseResult.SUCCESS(new PageResult<>(spitPage.getTotalElements(), spitPage.getContent()));
    }

    // 修改点赞数
    @PutMapping("/updatethumbup/{id}")
    public ResponseResult updateThumbupCon(@PathVariable String id){
        spitService.updateThumbupSer(id);
        return ResponseResult.SUCCESS();
    }
}
