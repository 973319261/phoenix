package com.koi.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * issue
 * @author 
 */
public class Issue implements Serializable {
    private Integer issueId;

    private Integer issueStateId;

    private String issueName;

    private String prizeName;

    private BigDecimal prizeMoney;

    private String prizeImg;

    private Integer yetBetNum;

    private Integer allBetNum;

    private Long blockNumber;

    private Integer luckNumber;

    private Date abortTime;

    private Date lotteryTime;

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

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
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
        Issue other = (Issue) that;
        return (this.getIssueId() == null ? other.getIssueId() == null : this.getIssueId().equals(other.getIssueId()))
            && (this.getIssueStateId() == null ? other.getIssueStateId() == null : this.getIssueStateId().equals(other.getIssueStateId()))
            && (this.getIssueName() == null ? other.getIssueName() == null : this.getIssueName().equals(other.getIssueName()))
            && (this.getPrizeName() == null ? other.getPrizeName() == null : this.getPrizeName().equals(other.getPrizeName()))
            && (this.getPrizeMoney() == null ? other.getPrizeMoney() == null : this.getPrizeMoney().equals(other.getPrizeMoney()))
            && (this.getPrizeImg() == null ? other.getPrizeImg() == null : this.getPrizeImg().equals(other.getPrizeImg()))
            && (this.getYetBetNum() == null ? other.getYetBetNum() == null : this.getYetBetNum().equals(other.getYetBetNum()))
            && (this.getAllBetNum() == null ? other.getAllBetNum() == null : this.getAllBetNum().equals(other.getAllBetNum()))
            && (this.getBlockNumber() == null ? other.getBlockNumber() == null : this.getBlockNumber().equals(other.getBlockNumber()))
            && (this.getLuckNumber() == null ? other.getLuckNumber() == null : this.getLuckNumber().equals(other.getLuckNumber()))
            && (this.getAbortTime() == null ? other.getAbortTime() == null : this.getAbortTime().equals(other.getAbortTime()))
            && (this.getLotteryTime() == null ? other.getLotteryTime() == null : this.getLotteryTime().equals(other.getLotteryTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIssueId() == null) ? 0 : getIssueId().hashCode());
        result = prime * result + ((getIssueStateId() == null) ? 0 : getIssueStateId().hashCode());
        result = prime * result + ((getIssueName() == null) ? 0 : getIssueName().hashCode());
        result = prime * result + ((getPrizeName() == null) ? 0 : getPrizeName().hashCode());
        result = prime * result + ((getPrizeMoney() == null) ? 0 : getPrizeMoney().hashCode());
        result = prime * result + ((getPrizeImg() == null) ? 0 : getPrizeImg().hashCode());
        result = prime * result + ((getYetBetNum() == null) ? 0 : getYetBetNum().hashCode());
        result = prime * result + ((getAllBetNum() == null) ? 0 : getAllBetNum().hashCode());
        result = prime * result + ((getBlockNumber() == null) ? 0 : getBlockNumber().hashCode());
        result = prime * result + ((getLuckNumber() == null) ? 0 : getLuckNumber().hashCode());
        result = prime * result + ((getAbortTime() == null) ? 0 : getAbortTime().hashCode());
        result = prime * result + ((getLotteryTime() == null) ? 0 : getLotteryTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", issueId=").append(issueId);
        sb.append(", issueStateId=").append(issueStateId);
        sb.append(", issueName=").append(issueName);
        sb.append(", prizeName=").append(prizeName);
        sb.append(", prizeMoney=").append(prizeMoney);
        sb.append(", prizeImg=").append(prizeImg);
        sb.append(", yetBetNum=").append(yetBetNum);
        sb.append(", allBetNum=").append(allBetNum);
        sb.append(", blockNumber=").append(blockNumber);
        sb.append(", luckNumber=").append(luckNumber);
        sb.append(", abortTime=").append(abortTime);
        sb.append(", lotteryTime=").append(lotteryTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}