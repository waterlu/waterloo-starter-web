package cn.lu.web.vo;

/**
 * 请求参数的基类
 *
 * @author lutiehua
 * @date 2017/11/13
 */
public class ParamDTO {

    /**
     * 请求来源标识
     */
    private String callSystemID;

    /**
     * 请求追踪标识
     */
    private String traceID;

    public String getCallSystemID() {
        return callSystemID;
    }

    public void setCallSystemID(String callSystemID) {
        this.callSystemID = callSystemID;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }
}