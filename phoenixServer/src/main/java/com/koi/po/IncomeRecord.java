package com.koi.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * income_record
 * @author 
 */
public class IncomeRecord implements Serializable {
    private Long incomeRecordId;

    private BigDecimal incomeTudi;

    private BigDecimal incomeTusun;

    private Date incomeDay;
    
    private BigDecimal incomeAll;

    private static final Long serialVersionUID = 1L;

    public Long getIncomeRecordId() {
        return incomeRecordId;
    }

    public void setIncomeRecordId(Long incomeRecordId) {
        this.incomeRecordId = incomeRecordId;
    }

    public BigDecimal getIncomeTudi() {
        return incomeTudi;
    }

    public void setIncomeTudi(BigDecimal incomeTudi) {
        this.incomeTudi = incomeTudi;
    }

    public BigDecimal getIncomeTusun() {
        return incomeTusun;
    }

    public void setIncomeTusun(BigDecimal incomeTusun) {
        this.incomeTusun = incomeTusun;
    }

    public Date getIncomeDay() {
        return incomeDay;
    }

    public void setIncomeDay(Date incomeDay) {
        this.incomeDay = incomeDay;
    }
    public BigDecimal getIncomeAll() {
        return incomeAll;
    }

    public void setIncomeAll(BigDecimal incomeAll) {
        this.incomeAll = incomeAll;
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
        IncomeRecord other = (IncomeRecord) that;
        return (this.getIncomeRecordId() == null ? other.getIncomeRecordId() == null : this.getIncomeRecordId().equals(other.getIncomeRecordId()))
            && (this.getIncomeTudi() == null ? other.getIncomeTudi() == null : this.getIncomeTudi().equals(other.getIncomeTudi()))
            && (this.getIncomeTusun() == null ? other.getIncomeTusun() == null : this.getIncomeTusun().equals(other.getIncomeTusun()))
            && (this.getIncomeDay() == null ? other.getIncomeDay() == null : this.getIncomeDay().equals(other.getIncomeDay()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIncomeRecordId() == null) ? 0 : getIncomeRecordId().hashCode());
        result = prime * result + ((getIncomeTudi() == null) ? 0 : getIncomeTudi().hashCode());
        result = prime * result + ((getIncomeTusun() == null) ? 0 : getIncomeTusun().hashCode());
        result = prime * result + ((getIncomeDay() == null) ? 0 : getIncomeDay().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", incomeRecordId=").append(incomeRecordId);
        sb.append(", incomeTudi=").append(incomeTudi);
        sb.append(", incomeTusun=").append(incomeTusun);
        sb.append(", incomeDay=").append(incomeDay);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}