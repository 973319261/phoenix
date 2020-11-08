package com.koi.po;

import java.io.Serializable;

/**
 * soure_type
 * @author 
 */
public class SoureType implements Serializable {
    private Integer sourceTypeId;

    private String sourceTypeName;

    private static final long serialVersionUID = 1L;

    public Integer getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(Integer sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
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
        SoureType other = (SoureType) that;
        return (this.getSourceTypeId() == null ? other.getSourceTypeId() == null : this.getSourceTypeId().equals(other.getSourceTypeId()))
            && (this.getSourceTypeName() == null ? other.getSourceTypeName() == null : this.getSourceTypeName().equals(other.getSourceTypeName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSourceTypeId() == null) ? 0 : getSourceTypeId().hashCode());
        result = prime * result + ((getSourceTypeName() == null) ? 0 : getSourceTypeName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sourceTypeId=").append(sourceTypeId);
        sb.append(", sourceTypeName=").append(sourceTypeName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}