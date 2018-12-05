package cn.lu.web.mvc;

import java.math.BigDecimal;

/**
 * 最简单的返回数据，只有一个字符串
 *
 * @author lu
 * @date 2018/5/11
 */
public class SimpleResponseData extends ResponseData<String> {

    public SimpleResponseData() {
        super();
    }

    public SimpleResponseData(ExceptionInfo exceptionInfo) {
        super(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    public SimpleResponseData(String data) {
        super(data);
    }

    public SimpleResponseData(int value) {
        super(Integer.toString(value));
    }

    public SimpleResponseData(long value) {
        super(Long.toString(value));
    }

    public SimpleResponseData(BigDecimal value) {
        // 注意：toString() 可能使用科学记数法
        super(value.toPlainString());
    }
}
