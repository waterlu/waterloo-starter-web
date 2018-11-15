package cn.lu.web.mvc;

import org.apache.logging.log4j.ThreadContext;

/**
 * 接口返回数据
 *
 * @author lu
 * @date 2018/5/11
 */
public class ResponseData<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String message = "";

    /**
     * 数据
     */
    private T data;

    /**
     * 服务追踪用
     */
    private String traceID = "";

    /**
     * 成功默认返回信息
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "成功";

    public ResponseData() {
        this.code = ResponseCode.SUCCESS.code;
        this.message = DEFAULT_SUCCESS_MESSAGE;
        this.traceID = ThreadContext.get("traceID");
    }

    public ResponseData(int code, String msg) {
        this.code = code;
        this.message = msg;
        this.traceID = ThreadContext.get("traceID");
    }

    public ResponseData(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
        this.traceID = ThreadContext.get("traceID");
    }

    public ResponseData(T data) {
        this.code = ResponseCode.SUCCESS.code;
        this.message = DEFAULT_SUCCESS_MESSAGE;
        this.data = data;
        this.traceID = ThreadContext.get("traceID");
    }


    public boolean isSuccessful() {
        return code == ResponseCode.SUCCESS.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

}
