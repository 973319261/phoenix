package com.koi.po;

import java.io.Serializable;

/**
 * money_soure
 * @author 
 */
public class MoneySoure implements Serializable {
    private Integer moneySoureId;

    private String moneySoureName;

    private static final long serialVersionUID = 1L;

    public Integer getMoneySoureId() {
        return moneySoureId;
    }

    public void setMoneySoureId(Integer moneySoureId) {
        this.moneySoureId = moneySoureId;
    }

    public String getMoneySoureName() {
        return moneySoureName;
    }

    public void setMoneySoureName(String moneySoureName) {
        this.moneySoureName = moneySoureName;
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
        MoneySoure other = (MoneySoure) that;
        return (this.getMoneySoureId() == null ? other.getMoneySoureId() == null : this.getMoneySoureId().equals(other.getMoneySoureId()))
            && (this.getMoneySoureName() == null ? other.getMoneySoureName() == null : this.getMoneySoureName().equals(other.getMoneySoureName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMoneySoureId() == null) ? 0 : getMoneySoureId().hashCode());
        result = prime * result + ((getMoneySoureName() == null) ? 0 : getMoneySoureName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moneySoureId=").append(moneySoureId);
        sb.append(", moneySoureName=").append(moneySoureName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}