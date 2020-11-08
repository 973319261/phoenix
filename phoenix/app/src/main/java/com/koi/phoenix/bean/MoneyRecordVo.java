package com.koi.phoenix.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * money_record
 * @author 
 */
public class MoneyRecordVo implements Serializable {
    private Integer moneyRecordId;

    private Integer userId;

    private Integer moneySoureId;

    private Integer moneyStateId;

    private BigDecimal money;

    private Date createTime;
    
    private Integer moneyType;
    
    private String moneySoureName;
    
    private String moneyStateName;

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

	public String getMoneySoureName() {
		return moneySoureName;
	}

	public void setMoneySoureName(String moneySoureName) {
		this.moneySoureName = moneySoureName;
	}

	public String getMoneyStateName() {
		return moneyStateName;
	}

	public void setMoneyStateName(String moneyStateName) {
		this.moneyStateName = moneyStateName;
	}
    
    
 
}