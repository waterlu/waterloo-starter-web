package cn.lu.web.base;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类基类
 *
 * @author lutiehua
 * @date 2018/5/23
 */
public abstract class BaseEntity implements Serializable {

    /**
     * 删除标记
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public BaseEntity() {
        Date now = new Date();
        deleteFlag = 0;
        createTime = now;
        updateTime = now;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
