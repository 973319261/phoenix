package com.koi.po;

import java.io.Serializable;

/**
 * pet_info_detail
 * @author 
 */
public class PetInfoDetail implements Serializable {
    private Integer petInfoDetailId;

    private Integer userId;

    private Integer petInfoId;

    private Integer petGoldLevel;

    private Integer isCreate;

    private Integer isLocked;

    private static final long serialVersionUID = 1L;

    public Integer getPetInfoDetailId() {
        return petInfoDetailId;
    }

    public void setPetInfoDetailId(Integer petInfoDetailId) {
        this.petInfoDetailId = petInfoDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPetInfoId() {
        return petInfoId;
    }

    public void setPetInfoId(Integer petInfoId) {
        this.petInfoId = petInfoId;
    }

    public Integer getPetGoldLevel() {
        return petGoldLevel;
    }

    public void setPetGoldLevel(Integer petGoldLevel) {
        this.petGoldLevel = petGoldLevel;
    }

    public Integer getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(Integer isCreate) {
        this.isCreate = isCreate;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
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
        PetInfoDetail other = (PetInfoDetail) that;
        return (this.getPetInfoDetailId() == null ? other.getPetInfoDetailId() == null : this.getPetInfoDetailId().equals(other.getPetInfoDetailId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPetInfoId() == null ? other.getPetInfoId() == null : this.getPetInfoId().equals(other.getPetInfoId()))
            && (this.getPetGoldLevel() == null ? other.getPetGoldLevel() == null : this.getPetGoldLevel().equals(other.getPetGoldLevel()))
            && (this.getIsCreate() == null ? other.getIsCreate() == null : this.getIsCreate().equals(other.getIsCreate()))
            && (this.getIsLocked() == null ? other.getIsLocked() == null : this.getIsLocked().equals(other.getIsLocked()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPetInfoDetailId() == null) ? 0 : getPetInfoDetailId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPetInfoId() == null) ? 0 : getPetInfoId().hashCode());
        result = prime * result + ((getPetGoldLevel() == null) ? 0 : getPetGoldLevel().hashCode());
        result = prime * result + ((getIsCreate() == null) ? 0 : getIsCreate().hashCode());
        result = prime * result + ((getIsLocked() == null) ? 0 : getIsLocked().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", petInfoDetailId=").append(petInfoDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", petInfoId=").append(petInfoId);
        sb.append(", petGoldLevel=").append(petGoldLevel);
        sb.append(", isCreate=").append(isCreate);
        sb.append(", isLocked=").append(isLocked);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}