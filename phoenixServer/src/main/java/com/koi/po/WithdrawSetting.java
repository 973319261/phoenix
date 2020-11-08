package com.koi.po;

import java.io.Serializable;

/**
 * withdraw_setting
 * @author 
 */
public class WithdrawSetting implements Serializable {
    private Integer withdrawSettingId;

    private Long withdrawMoney;

    private static final long serialVersionUID = 1L;

    public Integer getWithdrawSettingId() {
        return withdrawSettingId;
    }

    public void setWithdrawSettingId(Integer withdrawSettingId) {
        this.withdrawSettingId = withdrawSettingId;
    }

    public Long getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(Long withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
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
        WithdrawSetting other = (WithdrawSetting) that;
        return (this.getWithdrawSettingId() == null ? other.getWithdrawSettingId() == null : this.getWithdrawSettingId().equals(other.getWithdrawSettingId()))
            && (this.getWithdrawMoney() == null ? other.getWithdrawMoney() == null : this.getWithdrawMoney().equals(other.getWithdrawMoney()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWithdrawSettingId() == null) ? 0 : getWithdrawSettingId().hashCode());
        result = prime * result + ((getWithdrawMoney() == null) ? 0 : getWithdrawMoney().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", withdrawSettingId=").append(withdrawSettingId);
        sb.append(", withdrawMoney=").append(withdrawMoney);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}