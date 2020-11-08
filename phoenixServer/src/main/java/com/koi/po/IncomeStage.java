package com.koi.po;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * income_stage
 * @author 
 */
public class IncomeStage implements Serializable {
    private Integer incomeStageId;

    private String incomeStageName;

    private BigDecimal incomeStageNum;

    private BigDecimal incomeStageMoney;

    private static final long serialVersionUID = 1L;

    public Integer getIncomeStageId() {
        return incomeStageId;
    }

    public void setIncomeStageId(Integer incomeStageId) {
        this.incomeStageId = incomeStageId;
    }

    public String getIncomeStageName() {
        return incomeStageName;
    }

    public void setIncomeStageName(String incomeStageName) {
        this.incomeStageName = incomeStageName;
    }

    public BigDecimal getIncomeStageNum() {
        return incomeStageNum;
    }

    public void setIncomeStageNum(BigDecimal incomeStageNum) {
        this.incomeStageNum = incomeStageNum;
    }

    public BigDecimal getIncomeStageMoney() {
        return incomeStageMoney;
    }

    public void setIncomeStageMoney(BigDecimal incomeStageMoney) {
        this.incomeStageMoney = incomeStageMoney;
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
        IncomeStage other = (IncomeStage) that;
        return (this.getIncomeStageId() == null ? other.getIncomeStageId() == null : this.getIncomeStageId().equals(other.getIncomeStageId()))
            && (this.getIncomeStageName() == null ? other.getIncomeStageName() == null : this.getIncomeStageName().equals(other.getIncomeStageName()))
            && (this.getIncomeStageNum() == null ? other.getIncomeStageNum() == null : this.getIncomeStageNum().equals(other.getIncomeStageNum()))
            && (this.getIncomeStageMoney() == null ? other.getIncomeStageMoney() == null : this.getIncomeStageMoney().equals(other.getIncomeStageMoney()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIncomeStageId() == null) ? 0 : getIncomeStageId().hashCode());
        result = prime * result + ((getIncomeStageName() == null) ? 0 : getIncomeStageName().hashCode());
        result = prime * result + ((getIncomeStageNum() == null) ? 0 : getIncomeStageNum().hashCode());
        result = prime * result + ((getIncomeStageMoney() == null) ? 0 : getIncomeStageMoney().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", incomeStageId=").append(incomeStageId);
        sb.append(", incomeStageName=").append(incomeStageName);
        sb.append(", incomeStageNum=").append(incomeStageNum);
        sb.append(", incomeStageMoney=").append(incomeStageMoney);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}