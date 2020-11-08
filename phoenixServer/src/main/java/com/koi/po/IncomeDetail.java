package com.koi.po;

import java.io.Serializable;

/**
 * income_detail
 * @author 
 */
public class IncomeDetail implements Serializable {
    private Integer incomeDetailId;

    private Integer userId;

    private Integer incomeRecordId;

    private static final long serialVersionUID = 1L;

    public Integer getIncomeDetailId() {
        return incomeDetailId;
    }

    public void setIncomeDetailId(Integer incomeDetailId) {
        this.incomeDetailId = incomeDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIncomeRecordId() {
        return incomeRecordId;
    }

    public void setIncomeRecordId(Integer incomeRecordId) {
        this.incomeRecordId = incomeRecordId;
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
        IncomeDetail other = (IncomeDetail) that;
        return (this.getIncomeDetailId() == null ? other.getIncomeDetailId() == null : this.getIncomeDetailId().equals(other.getIncomeDetailId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIncomeRecordId() == null ? other.getIncomeRecordId() == null : this.getIncomeRecordId().equals(other.getIncomeRecordId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIncomeDetailId() == null) ? 0 : getIncomeDetailId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIncomeRecordId() == null) ? 0 : getIncomeRecordId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", incomeDetailId=").append(incomeDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", incomeRecordId=").append(incomeRecordId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}