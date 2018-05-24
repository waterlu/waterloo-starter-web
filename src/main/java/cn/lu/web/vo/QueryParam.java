package cn.lu.web.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询列表参数的基类
 *
 * @author lutiehua
 * @date 2017/11/14
 */
public class QueryParam extends ParamDTO {

    /**
     * 分页开始下标，默认值为0，从第一页开始
     */
    @ApiModelProperty(value = "分页开始下标", required = true, notes = "默认值0")
    private Integer startRow = 0;

    /**
     * 每页数量，每页默认显示20条数据
     */
    @ApiModelProperty(value = "每页数量", required = true, notes = "默认值20")
    private Integer pageSize = 20;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "逻辑删除标记", required = false)
    private Integer deleteFlag = 0;

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}