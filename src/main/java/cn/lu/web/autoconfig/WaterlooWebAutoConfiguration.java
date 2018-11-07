package cn.lu.web.autoconfig;

import cn.lu.web.annotation.EnableWaterlooWeb;
import cn.lu.web.mvc.HttpRequestFilter;
import cn.lu.web.mvc.GlobalExceptionHandler;
import cn.lu.web.util.TraceIdUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author lu
 * @date 2018/5/11
 */
@Configuration
@ConditionalOnBean(annotation = EnableWaterlooWeb.class)
public class WaterlooWebAutoConfiguration {

    /**
     * 配置使用FastJson完成对象转换
     * SpringMVC使用消息转换器(HttpMessageConverter)实现将请求信息转换为对象、将对象转换为响应信息
     * FastJsonHttpMessageConverter实现了HttpMessageConverter接口
     *
     * @return
     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fastConverter;
//        return new HttpMessageConverters(converter);
//    }

    /**
     * HttpRequestFilter是自定义的过滤器，用来输出HTTP请求访问日志
     *
     * @return
     */
    @Bean
    public HttpRequestFilter addFilter(){
        return new HttpRequestFilter();
    }

    /**
     * 配置统一的异常处理
     *
     * @return
     */
    @Bean
    public GlobalExceptionHandler addAdvice(){
        return new GlobalExceptionHandler();
    }
}
