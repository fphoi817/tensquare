package entity;

public class ResponseResult {

    private boolean status;
    private Integer code;
    private String msg;
    private Object data;

    private ResponseResult(boolean status, Integer code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseResult SUSSCESS(){
        return new ResponseResult(ResultStatusEnum.SUCCESS.isStatus(), ResultStatusEnum.SUCCESS.getCode(), ResultStatusEnum.SUCCESS.getMsg(), null);
    }

    public static ResponseResult SUSSCESS(Object data){
        return new ResponseResult(ResultStatusEnum.SUCCESS.isStatus(), ResultStatusEnum.SUCCESS.getCode(), ResultStatusEnum.SUCCESS.getMsg(), data);
    }

    public static ResponseResult FAILED(){
        return new ResponseResult(ResultStatusEnum.FAILED.isStatus(), ResultStatusEnum.FAILED.getCode(), ResultStatusEnum.FAILED.getMsg(), null);
    }

    public static ResponseResult FAILED(String msg){
        return new ResponseResult(ResultStatusEnum.FAILED.isStatus(), ResultStatusEnum.FAILED.getCode(), msg, null);
    }

    public static ResponseResult UNAUTHORIZED(){
        return new ResponseResult(ResultStatusEnum.UNAUTHORIZED.isStatus(), ResultStatusEnum.UNAUTHORIZED.getCode(), ResultStatusEnum.UNAUTHORIZED.getMsg(), null);
    }

    public static ResponseResult NOT_FOUND(){
        return new ResponseResult(ResultStatusEnum.NOT_FOUND.isStatus(), ResultStatusEnum.NOT_FOUND.getCode(), ResultStatusEnum.NOT_FOUND.getMsg(), null);
    }

    public static ResponseResult INTERNAL_SERVER_ERROR(){
        return new ResponseResult(ResultStatusEnum.INTERNAL_SERVER_ERROR.isStatus(), ResultStatusEnum.INTERNAL_SERVER_ERROR.getCode(), ResultStatusEnum.INTERNAL_SERVER_ERROR.getMsg(), null);
    }

    public static ResponseResult OVERDUE(){
        return new ResponseResult(ResultStatusEnum.OVERDUE.isStatus(), ResultStatusEnum.OVERDUE.getCode(), ResultStatusEnum.OVERDUE.getMsg(), null);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
