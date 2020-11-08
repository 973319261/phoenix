package com.koi.po;

import java.io.Serializable;

/**
 * money_state
 * @author 
 */
public class MoneyState implements Serializable {
    private Integer moneyStateId;

    private String moneyStateName;

    private static final long serialVersionUID = 1L;

    public Integer getMoneyStateId() {
        return moneyStateId;
    }

    public void setMoneyStateId(Integer moneyStateId) {
        this.moneyStateId = moneyStateId;
    }

    public String getMoneyStateName() {
        return moneyStateName;
    }

    public void setMoneyStateName(String moneyStateName) {
        this.moneyStateName = moneyStateName;
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
        MoneyState other = (MoneyState) that;
        return (this.getMoneyStateId() == null ? other.getMoneyStateId() == null : this.getMoneyStateId().equals(other.getMoneyStateId()))
            && (this.getMoneyStateName() == null ? other.getMoneyStateName() == null : this.getMoneyStateName().equals(other.getMoneyStateName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMoneyStateId() == null) ? 0 : getMoneyStateId().hashCode());
        result = prime * result + ((getMoneyStateName() == null) ? 0 : getMoneyStateName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moneyStateId=").append(moneyStateId);
        sb.append(", moneyStateName=").append(moneyStateName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}