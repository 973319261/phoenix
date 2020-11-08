package com.koi.po;

import java.io.Serializable;

/**
 * withdraw_setting_detail
 * @author 
 */
public class WithdrawSettingDetail implements Serializable {
    private Integer withdrawSettingDetailId;

    private Integer userId;

    private Integer withdrawSettingId;

    private Integer isSwitch;

    private static final long serialVersionUID = 1L;

    public Integer getWithdrawSettingDetailId() {
        return withdrawSettingDetailId;
    }

    public void setWithdrawSettingDetailId(Integer withdrawSettingDetailId) {
        this.withdrawSettingDetailId = withdrawSettingDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWithdrawSettingId() {
        return withdrawSettingId;
    }

    public void setWithdrawSettingId(Integer withdrawSettingId) {
        this.withdrawSettingId = withdrawSettingId;
    }

    public Integer getIsSwitch() {
        return isSwitch;
    }

    public void setIsSwitch(Integer isSwitch) {
        this.isSwitch = isSwitch;
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
        WithdrawSettingDetail other = (WithdrawSettingDetail) that;
        return (this.getWithdrawSettingDetailId() == null ? other.getWithdrawSettingDetailId() == null : this.getWithdrawSettingDetailId().equals(other.getWithdrawSettingDetailId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getWithdrawSettingId() == null ? other.getWithdrawSettingId() == null : this.getWithdrawSettingId().equals(other.getWithdrawSettingId()))
            && (this.getIsSwitch() == null ? other.getIsSwitch() == null : this.getIsSwitch().equals(other.getIsSwitch()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWithdrawSettingDetailId() == null) ? 0 : getWithdrawSettingDetailId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getWithdrawSettingId() == null) ? 0 : getWithdrawSettingId().hashCode());
        result = prime * result + ((getIsSwitch() == null) ? 0 : getIsSwitch().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", withdrawSettingDetailId=").append(withdrawSettingDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", withdrawSettingId=").append(withdrawSettingId);
        sb.append(", isSwitch=").append(isSwitch);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}