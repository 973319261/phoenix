package com.koi.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DhcRecordVo implements Serializable{
	private Integer dhcRecordId;

    private Integer sourceTypeId;

    private Integer userId;

    private BigDecimal dhcNum;

    private Date createTime;
    
    private String sourceTypeName;

	public Integer getDhcRecordId() {
		return dhcRecordId;
	}

	public void setDhcRecordId(Integer dhcRecordId) {
		this.dhcRecordId = dhcRecordId;
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

	public BigDecimal getDhcNum() {
		return dhcNum;
	}

	public void setDhcNum(BigDecimal dhcNum) {
		this.dhcNum = dhcNum;
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
