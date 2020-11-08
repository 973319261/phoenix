package com.koi.vo;

import java.io.Serializable;

public class PetInfoVo implements Serializable{
	private Integer petInfoDetailId;

    private Integer userId;

    private Integer petInfoId;
    
    private String petName;

    private Integer petGoldLevel;

    private Integer isCreate;

    private Integer isLocked;
    
    private Integer petLevel;

    private Integer petImg;
    
    private String petGold;

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
	
	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
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
    
    
}
