package com.koi.po;

import java.io.Serializable;

/**
 * redeem_code
 * @author 
 */
public class RedeemCode implements Serializable {
    private Integer redeemCodeId;

    private Integer betId;

    private Integer redeemCode;

    private static final long serialVersionUID = 1L;

    public Integer getRedeemCodeId() {
        return redeemCodeId;
    }

    public void setRedeemCodeId(Integer redeemCodeId) {
        this.redeemCodeId = redeemCodeId;
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public Integer getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(Integer redeemCode) {
        this.redeemCode = redeemCode;
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
        RedeemCode other = (RedeemCode) that;
        return (this.getRedeemCodeId() == null ? other.getRedeemCodeId() == null : this.getRedeemCodeId().equals(other.getRedeemCodeId()))
            && (this.getBetId() == null ? other.getBetId() == null : this.getBetId().equals(other.getBetId()))
            && (this.getRedeemCode() == null ? other.getRedeemCode() == null : this.getRedeemCode().equals(other.getRedeemCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRedeemCodeId() == null) ? 0 : getRedeemCodeId().hashCode());
        result = prime * result + ((getBetId() == null) ? 0 : getBetId().hashCode());
        result = prime * result + ((getRedeemCode() == null) ? 0 : getRedeemCode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", redeemCodeId=").append(redeemCodeId);
        sb.append(", betId=").append(betId);
        sb.append(", redeemCode=").append(redeemCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}