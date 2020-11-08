package com.koi.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SignVo implements Serializable{
	private Integer signId;
	
	private Integer userId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date signDay;

    private Integer rewardNum;
    
    private Integer isSign;

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public Date getSignDay() {
		return signDay;
	}

	public void setSignDay(Date signDay) {
		this.signDay = signDay;
	}

	public Integer getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}
	
	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    
    
}
