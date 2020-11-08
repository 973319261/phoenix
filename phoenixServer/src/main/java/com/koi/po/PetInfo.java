package com.koi.po;

import java.io.Serializable;

/**
 * pet_info
 * @author 
 */
public class PetInfo implements Serializable {
    private Integer petInfoId;

    private String petName;

    private Integer petLevel;

    private Integer petImg;

    private String petGold;

    private static final long serialVersionUID = 1L;

    public Integer getPetInfoId() {
        return petInfoId;
    }

    public void setPetInfoId(Integer petInfoId) {
        this.petInfoId = petInfoId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getPetLevel() {
        return petLevel;
    }

    public void setPetLevel(Integer petLevel) {
        this.petLevel = petLevel;
    }

    public Integer getPetImg() {
        return petImg;
    }

    public void setPetImg(Integer petImg) {
        this.petImg = petImg;
    }

    public String getPetGold() {
        return petGold;
    }

    public void setPetGold(String petGold) {
        this.petGold = petGold;
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
        PetInfo other = (PetInfo) that;
        return (this.getPetInfoId() == null ? other.getPetInfoId() == null : this.getPetInfoId().equals(other.getPetInfoId()))
            && (this.getPetName() == null ? other.getPetName() == null : this.getPetName().equals(other.getPetName()))
            && (this.getPetLevel() == null ? other.getPetLevel() == null : this.getPetLevel().equals(other.getPetLevel()))
            && (this.getPetImg() == null ? other.getPetImg() == null : this.getPetImg().equals(other.getPetImg()))
            && (this.getPetGold() == null ? other.getPetGold() == null : this.getPetGold().equals(other.getPetGold()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPetInfoId() == null) ? 0 : getPetInfoId().hashCode());
        result = prime * result + ((getPetName() == null) ? 0 : getPetName().hashCode());
        result = prime * result + ((getPetLevel() == null) ? 0 : getPetLevel().hashCode());
        result = prime * result + ((getPetImg() == null) ? 0 : getPetImg().hashCode());
        result = prime * result + ((getPetGold() == null) ? 0 : getPetGold().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", petInfoId=").append(petInfoId);
        sb.append(", petName=").append(petName);
        sb.append(", petLevel=").append(petLevel);
        sb.append(", petImg=").append(petImg);
        sb.append(", petGold=").append(petGold);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}