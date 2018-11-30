package cn.lu.web.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理类
 *
 * @author lu
 * @date 2018/5/11
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常的统一处理
     *
     * @param be
     * @param response
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseResult<String> handleBusinessException(BizException be, HttpServletResponse response) {
        int errorCode = be.getErrorCode();
        String errorMessage = be.getMessage();
        ResponseData<String> responseData = new ResponseData(errorCode, errorMessage);
        ResponseResult<String> responseResult = new ResponseResult(responseData);
        logger.error("BizException: code=[{}] message=[{}]", errorCode, errorMessage);
        return responseResult;
    }

    /**
     * 参数校验异常的统一处理
     *
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult<String> handleValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuffer buffer = new StringBuffer("");

        // 拼接所有的参数校验错误信息
        if (bindingResult.hasErrors()) {
            for(FieldError error: bindingResult.getFieldErrors()) {
                if (buffer.length() > 0) {
                    buffer.append(",");
                }
                buffer.append(error.getField());
                buffer.append(" ");
                buffer.append(error.getDefaultMessage());
            }
        }

        int errorCode = ResponseCode.PARAM_ERROR.code;
        String errorMessage = buffer.toString();
        ResponseData<String> responseData = new ResponseData(errorCode, errorMessage);
        ResponseResult<String> responseResult = new ResponseResult(responseData);
        logger.error("ValidException: code=[{}] message=[{}]]", errorCode, errorMessage);
        return responseResult;
    }

    /**
     * 正常不应该走到这里，走到这里说明出现了程序中未处理的异常
     *
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<String> handleUnknownException(Exception ex, HttpServletResponse response) {
        int errorCode = ResponseCode.EXCEPTION.code;
        String errorMessage = ex.toString();
        ResponseData<String> responseData = new ResponseData(errorCode, errorMessage);
        ResponseResult<String> responseResult = new ResponseResult(responseData);
        logger.error("UnknownException: code=[{}] message=[{}]]", errorCode, errorMessage);
        return responseResult;
    }
}