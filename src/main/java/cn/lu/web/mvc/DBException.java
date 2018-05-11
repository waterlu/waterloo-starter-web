package cn.lu.web.mvc;

/**
 * @author lu
 * @date 2018/5/11
 */
public class DBException extends RuntimeException {

    private final int errorCode;

    private final String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public DBException() {
        this.errorCode = ResponseCode.DB_FAILED.code;
        this.message = "Database Operation Exception";
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
