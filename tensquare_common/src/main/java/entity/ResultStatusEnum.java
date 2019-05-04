package entity;

public enum ResultStatusEnum {

    SUCCESS(true, 20000,"操作成功!"),
    FAILED(false, 40000,"操作失败!"),
    UNAUTHORIZED(false, 40100,"未认证（需要登录）!"),
    NOT_FOUND(false, 40400,"无法找到资源!"),
    INTERNAL_SERVER_ERROR(false, 50000,"抱歉, 系统繁忙, 请稍后再试!"),
    OVERDUE(false, 50100, "Token过期");

    private boolean status;
    private Integer code;
    private String msg;

    ResultStatusEnum(boolean status, Integer code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
