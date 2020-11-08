package com.koi.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * money_record
 * @author 
 */
public class MoneyRecord implements Serializable {
    private Integer moneyRecordId;

    private Integer userId;

    private Integer moneySoureId;

    private Integer moneyStateId;

    private BigDecimal money;

    private Date createTime;

    private Integer moneyType;

    private static final long serialVersionUID = 1L;

    public Integer getMoneyRecordId() {
        return moneyRecordId;
    }

    public void setMoneyRecordId(Integer moneyRecordId) {
        this.moneyRecordId = moneyRecordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMoneySoureId() {
        return moneySoureId;
    }

    public void setMoneySoureId(Integer moneySoureId) {
        this.moneySoureId = moneySoureId;
    }

    public Integer getMoneyStateId() {
        return moneyStateId;
    }

    public void setMoneyStateId(Integer moneyStateId) {
        this.moneyStateId = moneyStateId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
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
        MoneyRecord other = (MoneyRecord) that;
        return (this.getMoneyRecordId() == null ? other.getMoneyRecordId() == null : this.getMoneyRecordId().equals(other.getMoneyRecordId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getMoneySoureId() == null ? other.getMoneySoureId() == null : this.getMoneySoureId().equals(other.getMoneySoureId()))
            && (this.getMoneyStateId() == null ? other.getMoneyStateId() == null : this.getMoneyStateId().equals(other.getMoneyStateId()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getMoneyType() == null ? other.getMoneyType() == null : this.getMoneyType().equals(other.getMoneyType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMoneyRecordId() == null) ? 0 : getMoneyRecordId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getMoneySoureId() == null) ? 0 : getMoneySoureId().hashCode());
        result = prime * result + ((getMoneyStateId() == null) ? 0 : getMoneyStateId().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getMoneyType() == null) ? 0 : getMoneyType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moneyRecordId=").append(moneyRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", moneySoureId=").append(moneySoureId);
        sb.append(", moneyStateId=").append(moneyStateId);
        sb.append(", money=").append(money);
        sb.append(", createTime=").append(createTime);
        sb.append(", moneyType=").append(moneyType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}