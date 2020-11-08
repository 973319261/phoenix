package com.koi.po;

import java.io.Serializable;
import java.util.Date;

/**
 * dividend
 * @author 
 */
public class Dividend implements Serializable {
    private Integer dividendId;

    private Integer userId;

    private Integer sourceTypeId;

    private Integer dividendNum;

    private Date createTime;

    private Integer auditState;

    private static final long serialVersionUID = 1L;

    public Integer getDividendId() {
        return dividendId;
    }

    public void setDividendId(Integer dividendId) {
        this.dividendId = dividendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(Integer sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public Integer getDividendNum() {
        return dividendNum;
    }

    public void setDividendNum(Integer dividendNum) {
        this.dividendNum = dividendNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
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
        Dividend other = (Dividend) that;
        return (this.getDividendId() == null ? other.getDividendId() == null : this.getDividendId().equals(other.getDividendId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSourceTypeId() == null ? other.getSourceTypeId() == null : this.getSourceTypeId().equals(other.getSourceTypeId()))
            && (this.getDividendNum() == null ? other.getDividendNum() == null : this.getDividendNum().equals(other.getDividendNum()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAuditState() == null ? other.getAuditState() == null : this.getAuditState().equals(other.getAuditState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDividendId() == null) ? 0 : getDividendId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSourceTypeId() == null) ? 0 : getSourceTypeId().hashCode());
        result = prime * result + ((getDividendNum() == null) ? 0 : getDividendNum().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAuditState() == null) ? 0 : getAuditState().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dividendId=").append(dividendId);
        sb.append(", userId=").append(userId);
        sb.append(", sourceTypeId=").append(sourceTypeId);
        sb.append(", dividendNum=").append(dividendNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", auditState=").append(auditState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}