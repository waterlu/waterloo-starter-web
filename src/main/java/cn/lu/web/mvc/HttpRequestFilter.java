package cn.lu.web.mvc;

import cn.lu.web.util.TraceIdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 输出HTTP请求日志
 *
 * @author lu
 * @date 2018/5/11
 */
public class HttpRequestFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestFilter.class);

    final static String SOURCE_IP = "X-Source_IP";

    final static String TRACE_ID = "X-Trace_ID";

    final static String CALL_SYSTEM_ID = "X-CallSystem_ID";

    /**
     * 过滤监控请求日志
     */
    final Map<String, String> actuatorUriMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        actuatorUriMap.put("/archaius", "/archaius");
        actuatorUriMap.put("/auditevents", "/auditevents");
        actuatorUriMap.put("/autoconfig", "/autoconfig");
        actuatorUriMap.put("/beans", "/beans");
        actuatorUriMap.put("/dump", "/dump");
        actuatorUriMap.put("/env", "/env");
        actuatorUriMap.put("/features", "/features");
        actuatorUriMap.put("/health", "/health");
        actuatorUriMap.put("/heapdump", "/heapdump");
        actuatorUriMap.put("/hystrix.stream", "/hystrix.stream");
        actuatorUriMap.put("/info", "/info");
        actuatorUriMap.put("/jolokia", "/jolokia");
        actuatorUriMap.put("/logfile", "/logfile");
        actuatorUriMap.put("/loggers", "/loggers");
        actuatorUriMap.put("/mappings", "/mappings");
        actuatorUriMap.put("/metrics", "/metrics");
        actuatorUriMap.put("/pause", "/pause");
        actuatorUriMap.put("/refresh", "/refresh");
        actuatorUriMap.put("/restart", "/restart");
        actuatorUriMap.put("/resume", "/resume");
        actuatorUriMap.put("/service-registry/instance-status", "/service-registry/instance-status");
        actuatorUriMap.put("/trace", "/trace");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String uri = request.getRequestURI();
            if (actuatorUriMap.containsKey(uri)) {
                // 忽略监控请求
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 来源IP
            ServletRequest requestWrapper = new HttpRequestReaderWrapper(request);
            String ip = request.getHeader(SOURCE_IP);
            if(null == ip){
                ip = request.getRemoteAddr();
            }

            // 调用方
            Map<String, Object> paramMap = getParam(requestWrapper);
            String method = request.getMethod();
            String callSystemID = request.getHeader(CALL_SYSTEM_ID);
            if(null == callSystemID){
                Object objCallSystemID = paramMap.get("callSystemID");
                // 判断callSystemID为空的情况
                if (null != objCallSystemID) {
                    callSystemID = objCallSystemID.toString();
                }else{
                    callSystemID = "0000";
                }
            }

            // 服务追踪ID
            String traceID = request.getHeader(TRACE_ID);
            if(null == traceID){
                Object objTraceID = paramMap.get("traceID");
                // 判断traceID为空的情况
                if (null != objTraceID) {
                    traceID = objTraceID.toString();
                } else{
                    traceID = UUID.randomUUID().toString();
                }
            }

            ThreadContext.put("traceID", traceID);
            TraceIdUtil.getInstance().set(traceID);

            // 输出请求日志
            logger.info("sourceIP=[{}], callSystemID=[{}], traceID=[{}], uri=[{}], method=[{}], param=[{}]",
                    ip, callSystemID, traceID, uri, method, paramMap);

            long startTime = System.currentTimeMillis();
            filterChain.doFilter(requestWrapper, servletResponse);
            long finishTime = System.currentTimeMillis();
            long time = finishTime - startTime;

            // 输出处理时间
            logger.info("uri=[{}], method=[{}], time=[{}ms]", uri, method, time);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private Map<String, Object> getParam(ServletRequest request) {
        Map<String, Object> paramMap = new HashMap(32);
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    paramMap.put(paramName, paramValue);
                }
            }
        }

        String requestBody = HttpHelper.getBodyString(request);
        if (StringUtils.isEmpty(requestBody)) {
            return paramMap;
        }

        Map<String, Object> bodyParamMap = JSON.parseObject(requestBody, new TypeReference<Map<String, Object>>() {});
        paramMap.putAll(bodyParamMap);
        return paramMap;
    }

    @Override
    public void destroy() {

    }

}