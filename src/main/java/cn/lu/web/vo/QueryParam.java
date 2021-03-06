package cn.lu.web.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

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
    @ApiModelProperty(value = "分页开始下标(通用)", required = false, position = 91, notes = "默认值0")
    private Integer startRow = 0;

    /**
     * 每页数量，每页默认显示20条数据
     */
    @ApiModelProperty(value = "每页数量(通用)", required = false, position = 92, notes = "默认值20")
    private Integer pageSize = 20;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "逻辑删除标记(通用)", required = false, position = 93, notes = "默认查询条件")
    private Integer deleteFlag = 0;

//    private List<OrderParam> orderParamList = new ArrayList<>();
//
//    public List<OrderParam> getOrderParamList() {
//        return this.orderParamList;
//    }
//
//    /**
//     * 返回OrderByClause
//     *
//     * @return
//     */
//    public String getOrderString() {
//        if (orderParamList.size() > 0) {
//            StringBuffer buffer = new StringBuffer();
//            boolean first = true;
//            for (OrderParam orderParam : orderParamList) {
//                if (!first) {
//                    buffer.append(", ");
//                }
//                buffer.append(orderParam.getColumnName());
//                buffer.append(" ");
//                buffer.append(orderParam.getOrderType());
//                first = false;
//            }
//            return buffer.toString();
//        } else {
//            return "";
//        }
//    }

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