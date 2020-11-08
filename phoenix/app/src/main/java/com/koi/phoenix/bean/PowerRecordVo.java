package com.koi.phoenix.bean;

import java.io.Serializable;
import java.util.Date;

public class PowerRecordVo implements Serializable {
	private Integer profitRecordId;

    private Integer sourceTypeId;

    private Integer userId;

    private Integer profitNum;

    private Date createTime;
    
    private String sourceTypeName;

	public Integer getProfitRecordId() {
		return profitRecordId;
	}

	public void setProfitRecordId(Integer profitRecordId) {
		this.profitRecordId = profitRecordId;
	}

	public Integer getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProfitNum() {
		return profitNum;
	}

	public void setProfitNum(Integer profitNum) {
		this.profitNum = profitNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}
    
    
}
