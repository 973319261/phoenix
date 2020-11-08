package com.koi.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IssueVo implements Serializable {
	private Integer issueId;

    private Integer issueStateId;

    private String issueName;

    private String prizeName;
    
    private BigDecimal prizeMoney;

    private String prizeImg;

    private Integer yetBetNum;

    private Integer allBetNum;

    private Integer luckNumber;

    private Date abortTime;

    private Date lotteryTime;

    private Long blockNumber;
   
    private String issueStateName;
    
    private Integer isWinning;

    private static final long serialVersionUID = 1L;

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public Integer getIssueStateId() {
		return issueStateId;
	}

	public void setIssueStateId(Integer issueStateId) {
		this.issueStateId = issueStateId;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public BigDecimal getPrizeMoney() {
		return prizeMoney;
	}

	public void setPrizeMoney(BigDecimal prizeMoney) {
		this.prizeMoney = prizeMoney;
	}

	public String getPrizeImg() {
		return prizeImg;
	}

	public void setPrizeImg(String prizeImg) {
		this.prizeImg = prizeImg;
	}

	public Integer getYetBetNum() {
		return yetBetNum;
	}

	public void setYetBetNum(Integer yetBetNum) {
		this.yetBetNum = yetBetNum;
	}

	public Integer getAllBetNum() {
		return allBetNum;
	}

	public void setAllBetNum(Integer allBetNum) {
		this.allBetNum = allBetNum;
	}

	public Integer getLuckNumber() {
		return luckNumber;
	}

	public void setLuckNumber(Integer luckNumber) {
		this.luckNumber = luckNumber;
	}

	public Date getAbortTime() {
		return abortTime;
	}

	public void setAbortTime(Date abortTime) {
		this.abortTime = abortTime;
	}

	public Date getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(Date lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public Long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(Long blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getIssueStateName() {
		return issueStateName;
	}

	public void setIssueStateName(String issueStateName) {
		this.issueStateName = issueStateName;
	}

	public Integer getIsWinning() {
		return isWinning;
	}

	public void setIsWinning(Integer isWinning) {
		this.isWinning = isWinning;
	}
    
    
}
