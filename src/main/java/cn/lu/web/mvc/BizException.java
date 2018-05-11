package cn.lu.web.mvc;

/**
 * 业务异常的基类
 *
 * @author lu
 * @date 2018/5/11
 */
public class BizException extends RuntimeException {

    private int errorCode = ResponseCode.EXCEPTION.code;

    private String message = "Unknown Exception";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BizException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
