package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.BaseLabelClientImpl;
import com.tensquare.tools.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tensquare-base", fallback = BaseLabelClientImpl.class)
public interface BaseLabelClient {
    @GetMapping("/label/findById/{labelId}")
    ResponseResult findById(@PathVariable("labelId") String labelId);
}
