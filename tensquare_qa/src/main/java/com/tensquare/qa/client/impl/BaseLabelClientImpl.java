package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.BaseLabelClient;
import com.tensquare.tools.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public class BaseLabelClientImpl implements BaseLabelClient {
    @Override
    public ResponseResult findById(String labelId) {
        return ResponseResult.FAILED("熔断器启动了",null);
    }
}
