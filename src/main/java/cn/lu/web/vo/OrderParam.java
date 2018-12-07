package cn.lu.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 排序参数
 *
 * @author lutiehua
 * @date 2018-12-07
 */
@Data
public class OrderParam {

    /**
     * 升序
     *
     */
    public final static String ASC = "asc";

    /**
     * 降序
     *
     */
    public final static String DESC = "desc";

    /**
     * 排序字段
     *
     */
    @ApiModelProperty(value = "排序字段(通用)", required = false)
    private String columnName;

    /**
     * 排序类型
     *
     */
    @ApiModelProperty(value = "排序类型(通用)", required = false)
    private String orderType;
}
