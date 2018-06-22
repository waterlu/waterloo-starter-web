package cn.lu.web.mvc;

/**
 * 通用错误编码
 *
 * @author lu
 * @date 2018/5/11
 */
public enum ResponseCode {
    // 成功
    SUCCESS(200),
    // 请求参数错误
    PARAM_ERROR(400),
    // 未取到授权
    UNAUTHORIZED(401),
    // 接口不存在
    NOT_FOUND(404),
    // 未捕获的服务器内部错误
    EXCEPTION(500),
    // 服务暂时不可用
    UNAVAILABLE(503),
    // 数据库操作失败
    DB_FAILED(601),
    // 框架异常
    FRAME_FAILED(602);

    public int code;

    ResponseCode(int code) {
        this.code = code;
    }
}