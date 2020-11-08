package com.koi.phoenix.bean;

import java.io.Serializable;
import java.util.Date;

public class BetVo implements Serializable{

    private String nickName;

    private String avatarUrl;

    private Integer betNum;

    private Date betTime;
    
    private static final long serialVersionUID = 1L;

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getBetNum() {
		return betNum;
	}

	public void setBetNum(Integer betNum) {
		this.betNum = betNum;
	}

	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}
    
    
}
