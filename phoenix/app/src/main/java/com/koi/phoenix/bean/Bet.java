package com.koi.phoenix.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * bet
 * @author 
 */
public class Bet implements Serializable {
    private Integer betId;

    private Integer issueId;

    private Integer userId;

    private Integer betNum;

    private Date betTime;

    private Integer isWinning;

    private static final long serialVersionUID = 1L;

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getIsWinning() {
        return isWinning;
    }

    public void setIsWinning(Integer isWinning) {
        this.isWinning = isWinning;
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
        Bet other = (Bet) that;
        return (this.getBetId() == null ? other.getBetId() == null : this.getBetId().equals(other.getBetId()))
            && (this.getIssueId() == null ? other.getIssueId() == null : this.getIssueId().equals(other.getIssueId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBetNum() == null ? other.getBetNum() == null : this.getBetNum().equals(other.getBetNum()))
            && (this.getBetTime() == null ? other.getBetTime() == null : this.getBetTime().equals(other.getBetTime()))
            && (this.getIsWinning() == null ? other.getIsWinning() == null : this.getIsWinning().equals(other.getIsWinning()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBetId() == null) ? 0 : getBetId().hashCode());
        result = prime * result + ((getIssueId() == null) ? 0 : getIssueId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBetNum() == null) ? 0 : getBetNum().hashCode());
        result = prime * result + ((getBetTime() == null) ? 0 : getBetTime().hashCode());
        result = prime * result + ((getIsWinning() == null) ? 0 : getIsWinning().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", betId=").append(betId);
        sb.append(", issueId=").append(issueId);
        sb.append(", userId=").append(userId);
        sb.append(", betNum=").append(betNum);
        sb.append(", betTime=").append(betTime);
        sb.append(", isWinning=").append(isWinning);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}