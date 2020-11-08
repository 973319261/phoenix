package com.koi.po;

import java.io.Serializable;

/**
 * dividend_income
 * @author 
 */
public class DividendIncome implements Serializable {
    private Integer dividendIncomeId;

    private Long avgIncomeYesday;

    private Long adIncomeYesday;

    private Long adIncomeAll;

    private static final long serialVersionUID = 1L;

    public Integer getDividendIncomeId() {
        return dividendIncomeId;
    }

    public void setDividendIncomeId(Integer dividendIncomeId) {
        this.dividendIncomeId = dividendIncomeId;
    }

    public Long getAvgIncomeYesday() {
        return avgIncomeYesday;
    }

    public void setAvgIncomeYesday(Long avgIncomeYesday) {
        this.avgIncomeYesday = avgIncomeYesday;
    }

    public Long getAdIncomeYesday() {
        return adIncomeYesday;
    }

    public void setAdIncomeYesday(Long adIncomeYesday) {
        this.adIncomeYesday = adIncomeYesday;
    }

    public Long getAdIncomeAll() {
        return adIncomeAll;
    }

    public void setAdIncomeAll(Long adIncomeAll) {
        this.adIncomeAll = adIncomeAll;
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
        DividendIncome other = (DividendIncome) that;
        return (this.getDividendIncomeId() == null ? other.getDividendIncomeId() == null : this.getDividendIncomeId().equals(other.getDividendIncomeId()))
            && (this.getAvgIncomeYesday() == null ? other.getAvgIncomeYesday() == null : this.getAvgIncomeYesday().equals(other.getAvgIncomeYesday()))
            && (this.getAdIncomeYesday() == null ? other.getAdIncomeYesday() == null : this.getAdIncomeYesday().equals(other.getAdIncomeYesday()))
            && (this.getAdIncomeAll() == null ? other.getAdIncomeAll() == null : this.getAdIncomeAll().equals(other.getAdIncomeAll()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDividendIncomeId() == null) ? 0 : getDividendIncomeId().hashCode());
        result = prime * result + ((getAvgIncomeYesday() == null) ? 0 : getAvgIncomeYesday().hashCode());
        result = prime * result + ((getAdIncomeYesday() == null) ? 0 : getAdIncomeYesday().hashCode());
        result = prime * result + ((getAdIncomeAll() == null) ? 0 : getAdIncomeAll().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dividendIncomeId=").append(dividendIncomeId);
        sb.append(", avgIncomeYesday=").append(avgIncomeYesday);
        sb.append(", adIncomeYesday=").append(adIncomeYesday);
        sb.append(", adIncomeAll=").append(adIncomeAll);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}