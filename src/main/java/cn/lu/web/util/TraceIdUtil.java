package cn.lu.web.util;

/**
 * @author lu
 * @date 18-11-7
 */
public class TraceIdUtil {

    private static TraceIdUtil instance = new TraceIdUtil();

    private ThreadLocal<String> localTraceId = new ThreadLocal<>();

    private TraceIdUtil() {
    }

    public static TraceIdUtil getInstance() {
        return instance;
    }

    public void set(String traceId) {
        localTraceId.set(traceId);
    }

    public String get() {
        return localTraceId.get();
    }

}
