package com.koi.po;

import java.io.Serializable;
import java.util.Date;

/**
 * sign
 * @author 
 */
public class Sign implements Serializable {
    private Integer signId;

    private Date signDay;

    private Integer rewardNum;

    private static final long serialVersionUID = 1L;

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
        Sign other = (Sign) that;
        return (this.getSignId() == null ? other.getSignId() == null : this.getSignId().equals(other.getSignId()))
            && (this.getSignDay() == null ? other.getSignDay() == null : this.getSignDay().equals(other.getSignDay()))
            && (this.getRewardNum() == null ? other.getRewardNum() == null : this.getRewardNum().equals(other.getRewardNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSignId() == null) ? 0 : getSignId().hashCode());
        result = prime * result + ((getSignDay() == null) ? 0 : getSignDay().hashCode());
        result = prime * result + ((getRewardNum() == null) ? 0 : getRewardNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", signId=").append(signId);
        sb.append(", signDay=").append(signDay);
        sb.append(", rewardNum=").append(rewardNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}