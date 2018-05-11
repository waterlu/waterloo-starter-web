package cn.lu.web.vo;

import java.util.List;

/**
 * 列表返回结果
 *
 * @author lu
 * @date 2018/5/11
 */
public class ListResultVO<T> {

    /**
     * 数据总条数
     */
    private Long count;

    /**
     * 当前页数据列表
     */
    private List<T> list;

    /**
     * 数据总页数
     */
    private Integer pageCount;

    /**
     * 每页最大数据条数
     */
    private Integer pageSize;

    /**
     * 当前是第几页
     */
    private Integer pageNum;

}