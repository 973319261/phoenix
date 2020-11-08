package com.koi.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * dhc_record
 * @author 
 */
public class DhcRecord implements Serializable {
    private Integer dhcRecordId;

    private Integer sourceTypeId;

    private Integer userId;

    private BigDecimal dhcNum;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getDhcRecordId() {
        return dhcRecordId;
    }

    public void setDhcRecordId(Integer dhcRecordId) {
        this.dhcRecordId = dhcRecordId;
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

    public BigDecimal getDhcNum() {
        return dhcNum;
    }

    public void setDhcNum(BigDecimal dhcNum) {
        this.dhcNum = dhcNum;
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
        DhcRecord other = (DhcRecord) that;
        return (this.getDhcRecordId() == null ? other.getDhcRecordId() == null : this.getDhcRecordId().equals(other.getDhcRecordId()))
            && (this.getSourceTypeId() == null ? other.getSourceTypeId() == null : this.getSourceTypeId().equals(other.getSourceTypeId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getDhcNum() == null ? other.getDhcNum() == null : this.getDhcNum().equals(other.getDhcNum()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDhcRecordId() == null) ? 0 : getDhcRecordId().hashCode());
        result = prime * result + ((getSourceTypeId() == null) ? 0 : getSourceTypeId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getDhcNum() == null) ? 0 : getDhcNum().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dhcRecordId=").append(dhcRecordId);
        sb.append(", sourceTypeId=").append(sourceTypeId);
        sb.append(", userId=").append(userId);
        sb.append(", dhcNum=").append(dhcNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}