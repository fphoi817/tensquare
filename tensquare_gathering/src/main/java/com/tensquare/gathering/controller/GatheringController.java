package com.tensquare.gathering.controller;

import com.tensquare.gathering.service.GatheringService;
import com.tensquare.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RefreshScope
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;
    @Autowired
    public GatheringController(GatheringService gatheringService){
        this.gatheringService = gatheringService;
    }

    @GetMapping("/{id}")
    public ResponseResult findByIdCon(@PathVariable String id) {
        return ResponseResult.SUCCESS(gatheringService.findByIdSer(id));
    }

}
