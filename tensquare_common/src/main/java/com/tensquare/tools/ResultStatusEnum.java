package com.tensquare.tools;

import java.util.Date;

public enum ResultStatusEnum {

    SUCCESS(new Date(),200,"操作成功!"),
    FAILED(new Date(),400,"操作失败!"),
    UNAUTHORIZED(new Date(),401,"未认证（需要登录）!"),
    NOT_FOUND(new Date(),404,"无法找到资源!"),
    INTERNAL_SERVER_ERROR(new Date(),500,"抱歉, 系统繁忙, 请稍后再试!"),
    OVERDUE(new Date(),501, "Token过期");

    private Integer status;
    private String message;
    private Date date;

    ResultStatusEnum(Date date, Integer status, String message) {
        this.status = status;
        this.message = message;
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
