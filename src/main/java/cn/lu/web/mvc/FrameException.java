package cn.lu.web.mvc;

/**
 * @author lu
 * @date 2018/6/21
 */
public class FrameException extends BizException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param errorCode
     * @param message
     */
    public FrameException(int errorCode, String message) {
        super(errorCode, message);
    }

    /**
     *
     * @param message
     */
    public FrameException(String message) {
        super(ResponseCode.FRAME_FAILED.code, message);
    }
}
