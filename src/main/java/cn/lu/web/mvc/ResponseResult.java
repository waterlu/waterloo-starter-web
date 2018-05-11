package cn.lu.web.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 请求返回结果
 *
 * @author lu
 * @date 2018/5/11
 */
public class ResponseResult<T> extends ResponseEntity<ResponseData<T>> {

    /**
     * 大多数情况下，即使后台发生异常，只要被捕获到，都返给前端HTTP 200
     * 业务相关错误号封装在ResponseData中
     *
     * @param body
     */
    public ResponseResult (ResponseData<T> body) {
        super(body, HttpStatus.OK);
    }
}
