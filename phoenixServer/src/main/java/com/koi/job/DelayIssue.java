package com.koi.job;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟期号开奖实体类
 * @author koi
 *
 */
public class DelayIssue implements Delayed{
	  private Integer issueId;//期号ID
	  private String issueName;//期号名称
	  private Date abortTime;//截止时间

	// 这里根据截止（开奖）时间来比较，如果截止时间小的，就会优先被队列提取出来
	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return this.abortTime.compareTo(((DelayIssue)o).abortTime);
	}
	
	// 用来判断是否到期
	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
		return unit.convert(this.abortTime.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public Date getAbortTime() {
		return abortTime;
	}

	public void setAbortTime(Date abortTime) {
		this.abortTime = abortTime;
	}

}
