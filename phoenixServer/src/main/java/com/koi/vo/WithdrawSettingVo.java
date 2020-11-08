package com.koi.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawSettingVo implements Serializable {
	private Integer withdrawSettingDetailId;

    private Integer userId;

    private Integer withdrawSettingId;

    private Integer isSwitch;
    
    private BigDecimal withdrawMoney;

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

	public BigDecimal getWithdrawMoney() {
		return withdrawMoney;
	}

	public void setWithdrawMoney(BigDecimal withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}
    
    
}
