package com.koi.po;

import java.io.Serializable;

/**
 * sign_detail
 * @author 
 */
public class SignDetail implements Serializable {
    private Integer signDetailId;

    private Integer userId;

    private Integer signId;

    private Integer isSign;

    private static final long serialVersionUID = 1L;

    public Integer getSignDetailId() {
        return signDetailId;
    }

    public void setSignDetailId(Integer signDetailId) {
        this.signDetailId = signDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
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
        SignDetail other = (SignDetail) that;
        return (this.getSignDetailId() == null ? other.getSignDetailId() == null : this.getSignDetailId().equals(other.getSignDetailId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSignId() == null ? other.getSignId() == null : this.getSignId().equals(other.getSignId()))
            && (this.getIsSign() == null ? other.getIsSign() == null : this.getIsSign().equals(other.getIsSign()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSignDetailId() == null) ? 0 : getSignDetailId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSignId() == null) ? 0 : getSignId().hashCode());
        result = prime * result + ((getIsSign() == null) ? 0 : getIsSign().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", signDetailId=").append(signDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", signId=").append(signId);
        sb.append(", isSign=").append(isSign);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}