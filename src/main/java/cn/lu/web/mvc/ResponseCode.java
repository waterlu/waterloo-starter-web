package cn.lu.web.mvc;

/**
 * 通用错误编码
 *
 * @author lu
 * @date 2018/5/11
 */
public enum ResponseCode implements ExceptionInfo {
    // 成功
    SUCCESS(200, "Success"),
    // 请求参数错误
    PARAM_ERROR(400, "Bad Request"),
    // 未取到授权
    UNAUTHORIZED(401, "Unauthorized"),
    // 禁止访问
    FORBIDDEN(403, "Forbidden"),
    // 接口不存在
    NOT_FOUND(404, "Not Found"),
    // 未捕获的服务器内部错误
    EXCEPTION(500, "Service Exception"),
    // 服务暂时不可用
    UNAVAILABLE(503, "Service Unavailable"),
    // 数据库操作失败
    DB_FAILED(601, "Database Exception"),
    // 框架异常
    FRAME_FAILED(602, "Framework Exception");

    public int code;

    public String message;

    ResponseCode(int code) {
        this.code = code;
        this.message = "";
    }

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}