package com.tensquare.recruit;

import entity.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BaseExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exception(Exception e, HttpServletRequest request) {
        logger.error("path: "+request.getRequestURI());
        logger.error(e.toString(), e);
        return ResponseResult.FAILED(e.getMessage(), request.getRequestURI());
    }
}