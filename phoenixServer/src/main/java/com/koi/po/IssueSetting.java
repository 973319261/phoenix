package com.koi.po;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * issue_setting
 * @author 
 */
public class IssueSetting implements Serializable {
    private Integer issueSettingId;

    private Integer issueNum;

    private String prizeName;

    private BigDecimal prizeMoney;

    private String prizeImg;

    private Integer initBetNum;

    private Integer maxBetNum;

    private Integer startHour;

    private Integer startMinute;

    private Integer startSecond;

    private Integer intervalHour;

    private static final long serialVersionUID = 1L;

    public Integer getIssueSettingId() {
        return issueSettingId;
    }

    public void setIssueSettingId(Integer issueSettingId) {
        this.issueSettingId = issueSettingId;
    }

    public Integer getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(Integer issueNum) {
        this.issueNum = issueNum;
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

    public Integer getInitBetNum() {
        return initBetNum;
    }

    public void setInitBetNum(Integer initBetNum) {
        this.initBetNum = initBetNum;
    }

    public Integer getMaxBetNum() {
        return maxBetNum;
    }

    public void setMaxBetNum(Integer maxBetNum) {
        this.maxBetNum = maxBetNum;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getStartSecond() {
        return startSecond;
    }

    public void setStartSecond(Integer startSecond) {
        this.startSecond = startSecond;
    }

    public Integer getIntervalHour() {
        return intervalHour;
    }

    public void setIntervalHour(Integer intervalHour) {
        this.intervalHour = intervalHour;
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
        IssueSetting other = (IssueSetting) that;
        return (this.getIssueSettingId() == null ? other.getIssueSettingId() == null : this.getIssueSettingId().equals(other.getIssueSettingId()))
            && (this.getIssueNum() == null ? other.getIssueNum() == null : this.getIssueNum().equals(other.getIssueNum()))
            && (this.getPrizeName() == null ? other.getPrizeName() == null : this.getPrizeName().equals(other.getPrizeName()))
            && (this.getPrizeMoney() == null ? other.getPrizeMoney() == null : this.getPrizeMoney().equals(other.getPrizeMoney()))
            && (this.getPrizeImg() == null ? other.getPrizeImg() == null : this.getPrizeImg().equals(other.getPrizeImg()))
            && (this.getInitBetNum() == null ? other.getInitBetNum() == null : this.getInitBetNum().equals(other.getInitBetNum()))
            && (this.getMaxBetNum() == null ? other.getMaxBetNum() == null : this.getMaxBetNum().equals(other.getMaxBetNum()))
            && (this.getStartHour() == null ? other.getStartHour() == null : this.getStartHour().equals(other.getStartHour()))
            && (this.getStartMinute() == null ? other.getStartMinute() == null : this.getStartMinute().equals(other.getStartMinute()))
            && (this.getStartSecond() == null ? other.getStartSecond() == null : this.getStartSecond().equals(other.getStartSecond()))
            && (this.getIntervalHour() == null ? other.getIntervalHour() == null : this.getIntervalHour().equals(other.getIntervalHour()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIssueSettingId() == null) ? 0 : getIssueSettingId().hashCode());
        result = prime * result + ((getIssueNum() == null) ? 0 : getIssueNum().hashCode());
        result = prime * result + ((getPrizeName() == null) ? 0 : getPrizeName().hashCode());
        result = prime * result + ((getPrizeMoney() == null) ? 0 : getPrizeMoney().hashCode());
        result = prime * result + ((getPrizeImg() == null) ? 0 : getPrizeImg().hashCode());
        result = prime * result + ((getInitBetNum() == null) ? 0 : getInitBetNum().hashCode());
        result = prime * result + ((getMaxBetNum() == null) ? 0 : getMaxBetNum().hashCode());
        result = prime * result + ((getStartHour() == null) ? 0 : getStartHour().hashCode());
        result = prime * result + ((getStartMinute() == null) ? 0 : getStartMinute().hashCode());
        result = prime * result + ((getStartSecond() == null) ? 0 : getStartSecond().hashCode());
        result = prime * result + ((getIntervalHour() == null) ? 0 : getIntervalHour().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", issueSettingId=").append(issueSettingId);
        sb.append(", issueNum=").append(issueNum);
        sb.append(", prizeName=").append(prizeName);
        sb.append(", prizeMoney=").append(prizeMoney);
        sb.append(", prizeImg=").append(prizeImg);
        sb.append(", initBetNum=").append(initBetNum);
        sb.append(", maxBetNum=").append(maxBetNum);
        sb.append(", startHour=").append(startHour);
        sb.append(", startMinute=").append(startMinute);
        sb.append(", startSecond=").append(startSecond);
        sb.append(", intervalHour=").append(intervalHour);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}