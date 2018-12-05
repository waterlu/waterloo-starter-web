package cn.lu.web.mvc;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "状态码(200表示成功)", required = true, position = 1)
    private int code;

    /**
     * 状态信息
     */
    @ApiModelProperty(value = "错误信息(状态码!=200时有效)", required = true, position = 2)
    private String message = "";

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", required = true, position = 3)
    private T data;

    /**
     * 服务追踪用
     */
    @ApiModelProperty(value = "服务追踪ID", required = true, position = 4)
    private String traceID = "";

    public ResponseData() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
        this.traceID = ThreadContext.get("traceID");
    }

    public ResponseData(ExceptionInfo exceptionInfo) {
        this.code = exceptionInfo.getCode();
        this.message = exceptionInfo.getMessage();
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
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
        this.data = data;
        this.traceID = ThreadContext.get("traceID");
    }


    public boolean isSuccessful() {
        return code == ResponseCode.SUCCESS.getCode();
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
