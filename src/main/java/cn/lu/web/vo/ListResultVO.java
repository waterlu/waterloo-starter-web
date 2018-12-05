package cn.lu.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 列表返回结果
 *
 * @author lu
 * @date 2018/5/11
 */
@Getter
@Setter
@ToString
public class ListResultVO<T> {

    /**
     * 数据总条数
     */
    @ApiModelProperty(value = "数据总条数", required = true, position = 1)
    private Long count;

    /**
     * 当前页数据列表
     */
    @ApiModelProperty(value = "当前页数据列表", required = true, position = 2)
    private List<T> list;

    /**
     * 数据总页数
     */
    @ApiModelProperty(value = "数据总页数", required = true, position = 3)
    private Integer pageCount;

    /**
     * 每页最大数据条数
     */
    @ApiModelProperty(value = "每页数据条数", required = true, position = 4)
    private Integer pageSize;

    /**
     * 当前是第几页
     */
    @ApiModelProperty(value = "当前是第几页(从1开始)", required = true, position = 5)
    private Integer pageNum;

}