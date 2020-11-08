package com.koi.phoenix.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * progress
 * @author 
 */
public class Progress implements Serializable {
    private Integer progressId;

    private BigDecimal tudi;

    private BigDecimal tushu;

    private BigDecimal other;

    private static final long serialVersionUID = 1L;

    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public BigDecimal getTudi() {
        return tudi;
    }

    public void setTudi(BigDecimal tudi) {
        this.tudi = tudi;
    }

    public BigDecimal getTushu() {
        return tushu;
    }

    public void setTushu(BigDecimal tushu) {
        this.tushu = tushu;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
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
        Progress other = (Progress) that;
        return (this.getProgressId() == null ? other.getProgressId() == null : this.getProgressId().equals(other.getProgressId()))
            && (this.getTudi() == null ? other.getTudi() == null : this.getTudi().equals(other.getTudi()))
            && (this.getTushu() == null ? other.getTushu() == null : this.getTushu().equals(other.getTushu()))
            && (this.getOther() == null ? other.getOther() == null : this.getOther().equals(other.getOther()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProgressId() == null) ? 0 : getProgressId().hashCode());
        result = prime * result + ((getTudi() == null) ? 0 : getTudi().hashCode());
        result = prime * result + ((getTushu() == null) ? 0 : getTushu().hashCode());
        result = prime * result + ((getOther() == null) ? 0 : getOther().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", progressId=").append(progressId);
        sb.append(", tudi=").append(tudi);
        sb.append(", tushu=").append(tushu);
        sb.append(", other=").append(other);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}