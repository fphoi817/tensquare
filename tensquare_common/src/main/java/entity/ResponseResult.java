package entity;

import java.util.Date;

public class ResponseResult {

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
    private Integer status;
    private String message;
    private Object data;
    private String path;

    private ResponseResult(Date date, Integer status, String message, Object data, String path) {
        this.date = date;
        this.status = status;
        this.message = message;
        this.data = data;
        this.path = path;
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(ResultStatusEnum.SUCCESS.getDate(), ResultStatusEnum.SUCCESS.getStatus(), ResultStatusEnum.SUCCESS.getMessage(), null, null);
    }

    public static ResponseResult SUCCESS(Object data){
        return new ResponseResult(ResultStatusEnum.SUCCESS.getDate(), ResultStatusEnum.SUCCESS.getStatus(), ResultStatusEnum.SUCCESS.getMessage(), data, null);
    }

    public static ResponseResult FAILED(String path){
        return new ResponseResult(ResultStatusEnum.FAILED.getDate(), ResultStatusEnum.FAILED.getStatus(), ResultStatusEnum.FAILED.getMessage(), null, path);
    }

    public static ResponseResult FAILED(Object data, String path){
        return new ResponseResult(ResultStatusEnum.FAILED.getDate(), ResultStatusEnum.FAILED.getStatus(), ResultStatusEnum.FAILED.getMessage(), data, path);
    }

    public static ResponseResult UNAUTHORIZED(String path){
        return new ResponseResult(ResultStatusEnum.UNAUTHORIZED.getDate(), ResultStatusEnum.UNAUTHORIZED.getStatus(), ResultStatusEnum.UNAUTHORIZED.getMessage(), null, path);
    }

    public static ResponseResult NOT_FOUND(String path){
        return new ResponseResult(ResultStatusEnum.NOT_FOUND.getDate(), ResultStatusEnum.NOT_FOUND.getStatus(), ResultStatusEnum.NOT_FOUND.getMessage(), null, path);
    }

    public static ResponseResult INTERNAL_SERVER_ERROR(String path){
        return new ResponseResult(ResultStatusEnum.INTERNAL_SERVER_ERROR.getDate(), ResultStatusEnum.INTERNAL_SERVER_ERROR.getStatus(), ResultStatusEnum.INTERNAL_SERVER_ERROR.getMessage(), null, path);
    }

    public static ResponseResult OVERDUE(String path){
        return new ResponseResult(ResultStatusEnum.OVERDUE.getDate(), ResultStatusEnum.OVERDUE.getStatus(), ResultStatusEnum.OVERDUE.getMessage(), null, path);
    }

    public Object getData() {
        return data;
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

    public String getPath() {
        return path;
    }
}
