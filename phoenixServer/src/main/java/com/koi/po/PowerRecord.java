package com.koi.po;

import java.io.Serializable;
import java.util.Date;

/**
 * power_record
 * @author 
 */
public class PowerRecord implements Serializable {
    private Integer profitRecordId;

    private Integer sourceTypeId;

    private Integer userId;

    private Integer profitNum;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getProfitRecordId() {
        return profitRecordId;
    }

    public void setProfitRecordId(Integer profitRecordId) {
        this.profitRecordId = profitRecordId;
    }

    public Integer getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(Integer sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProfitNum() {
        return profitNum;
    }

    public void setProfitNum(Integer profitNum) {
        this.profitNum = profitNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PowerRecord other = (PowerRecord) that;
        return (this.getProfitRecordId() == null ? other.getProfitRecordId() == null : this.getProfitRecordId().equals(other.getProfitRecordId()))
            && (this.getSourceTypeId() == null ? other.getSourceTypeId() == null : this.getSourceTypeId().equals(other.getSourceTypeId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getProfitNum() == null ? other.getProfitNum() == null : this.getProfitNum().equals(other.getProfitNum()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProfitRecordId() == null) ? 0 : getProfitRecordId().hashCode());
        result = prime * result + ((getSourceTypeId() == null) ? 0 : getSourceTypeId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProfitNum() == null) ? 0 : getProfitNum().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", profitRecordId=").append(profitRecordId);
        sb.append(", sourceTypeId=").append(sourceTypeId);
        sb.append(", userId=").append(userId);
        sb.append(", profitNum=").append(profitNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}