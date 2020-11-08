package com.koi.po;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @author 
 */
public class User implements Serializable {
    private Integer userId;

    private Integer progressId;

    private Integer incomeStageId;

    private Integer videoRecordId;

    private Integer userInfoId;

    private Integer fromId;

    private String openId;

    private String appOpenId;

    private String nickName;

    private String avatarUrl;

    private Integer gender;

    private String inviteCode;

    private String tel;

    private Integer level;

    private Integer levelImg;

    private Integer inviteTicketNum;

    private String petList;

    private String levelIncome;

    private String goldAll;

    private String goldUsable;

    private String profitSpeed;

    private Date goldGetTime;

    private Integer isGoldGet;

    private Integer isEffect;

    private Date loginTime;

    private Date createTime;

    private Date updateTime;

    private Integer isStaus;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public Integer getIncomeStageId() {
        return incomeStageId;
    }

    public void setIncomeStageId(Integer incomeStageId) {
        this.incomeStageId = incomeStageId;
    }

    public Integer getVideoRecordId() {
        return videoRecordId;
    }

    public void setVideoRecordId(Integer videoRecordId) {
        this.videoRecordId = videoRecordId;
    }

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppOpenId() {
        return appOpenId;
    }

    public void setAppOpenId(String appOpenId) {
        this.appOpenId = appOpenId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevelImg() {
        return levelImg;
    }

    public void setLevelImg(Integer levelImg) {
        this.levelImg = levelImg;
    }

    public Integer getInviteTicketNum() {
        return inviteTicketNum;
    }

    public void setInviteTicketNum(Integer inviteTicketNum) {
        this.inviteTicketNum = inviteTicketNum;
    }

    public String getPetList() {
        return petList;
    }

    public void setPetList(String petList) {
        this.petList = petList;
    }

    public String getLevelIncome() {
        return levelIncome;
    }

    public void setLevelIncome(String levelIncome) {
        this.levelIncome = levelIncome;
    }

    public String getGoldAll() {
        return goldAll;
    }

    public void setGoldAll(String goldAll) {
        this.goldAll = goldAll;
    }

    public String getGoldUsable() {
        return goldUsable;
    }

    public void setGoldUsable(String goldUsable) {
        this.goldUsable = goldUsable;
    }

    public String getProfitSpeed() {
        return profitSpeed;
    }

    public void setProfitSpeed(String profitSpeed) {
        this.profitSpeed = profitSpeed;
    }

    public Date getGoldGetTime() {
        return goldGetTime;
    }

    public void setGoldGetTime(Date goldGetTime) {
        this.goldGetTime = goldGetTime;
    }

    public Integer getIsGoldGet() {
        return isGoldGet;
    }

    public void setIsGoldGet(Integer isGoldGet) {
        this.isGoldGet = isGoldGet;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect) {
        this.isEffect = isEffect;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsStaus() {
        return isStaus;
    }

    public void setIsStaus(Integer isStaus) {
        this.isStaus = isStaus;
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
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getProgressId() == null ? other.getProgressId() == null : this.getProgressId().equals(other.getProgressId()))
            && (this.getIncomeStageId() == null ? other.getIncomeStageId() == null : this.getIncomeStageId().equals(other.getIncomeStageId()))
            && (this.getVideoRecordId() == null ? other.getVideoRecordId() == null : this.getVideoRecordId().equals(other.getVideoRecordId()))
            && (this.getUserInfoId() == null ? other.getUserInfoId() == null : this.getUserInfoId().equals(other.getUserInfoId()))
            && (this.getFromId() == null ? other.getFromId() == null : this.getFromId().equals(other.getFromId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getAppOpenId() == null ? other.getAppOpenId() == null : this.getAppOpenId().equals(other.getAppOpenId()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getAvatarUrl() == null ? other.getAvatarUrl() == null : this.getAvatarUrl().equals(other.getAvatarUrl()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getInviteCode() == null ? other.getInviteCode() == null : this.getInviteCode().equals(other.getInviteCode()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getLevelImg() == null ? other.getLevelImg() == null : this.getLevelImg().equals(other.getLevelImg()))
            && (this.getInviteTicketNum() == null ? other.getInviteTicketNum() == null : this.getInviteTicketNum().equals(other.getInviteTicketNum()))
            && (this.getPetList() == null ? other.getPetList() == null : this.getPetList().equals(other.getPetList()))
            && (this.getLevelIncome() == null ? other.getLevelIncome() == null : this.getLevelIncome().equals(other.getLevelIncome()))
            && (this.getGoldAll() == null ? other.getGoldAll() == null : this.getGoldAll().equals(other.getGoldAll()))
            && (this.getGoldUsable() == null ? other.getGoldUsable() == null : this.getGoldUsable().equals(other.getGoldUsable()))
            && (this.getProfitSpeed() == null ? other.getProfitSpeed() == null : this.getProfitSpeed().equals(other.getProfitSpeed()))
            && (this.getGoldGetTime() == null ? other.getGoldGetTime() == null : this.getGoldGetTime().equals(other.getGoldGetTime()))
            && (this.getIsGoldGet() == null ? other.getIsGoldGet() == null : this.getIsGoldGet().equals(other.getIsGoldGet()))
            && (this.getIsEffect() == null ? other.getIsEffect() == null : this.getIsEffect().equals(other.getIsEffect()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsStaus() == null ? other.getIsStaus() == null : this.getIsStaus().equals(other.getIsStaus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProgressId() == null) ? 0 : getProgressId().hashCode());
        result = prime * result + ((getIncomeStageId() == null) ? 0 : getIncomeStageId().hashCode());
        result = prime * result + ((getVideoRecordId() == null) ? 0 : getVideoRecordId().hashCode());
        result = prime * result + ((getUserInfoId() == null) ? 0 : getUserInfoId().hashCode());
        result = prime * result + ((getFromId() == null) ? 0 : getFromId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getAppOpenId() == null) ? 0 : getAppOpenId().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getAvatarUrl() == null) ? 0 : getAvatarUrl().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getInviteCode() == null) ? 0 : getInviteCode().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getLevelImg() == null) ? 0 : getLevelImg().hashCode());
        result = prime * result + ((getInviteTicketNum() == null) ? 0 : getInviteTicketNum().hashCode());
        result = prime * result + ((getPetList() == null) ? 0 : getPetList().hashCode());
        result = prime * result + ((getLevelIncome() == null) ? 0 : getLevelIncome().hashCode());
        result = prime * result + ((getGoldAll() == null) ? 0 : getGoldAll().hashCode());
        result = prime * result + ((getGoldUsable() == null) ? 0 : getGoldUsable().hashCode());
        result = prime * result + ((getProfitSpeed() == null) ? 0 : getProfitSpeed().hashCode());
        result = prime * result + ((getGoldGetTime() == null) ? 0 : getGoldGetTime().hashCode());
        result = prime * result + ((getIsGoldGet() == null) ? 0 : getIsGoldGet().hashCode());
        result = prime * result + ((getIsEffect() == null) ? 0 : getIsEffect().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsStaus() == null) ? 0 : getIsStaus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", progressId=").append(progressId);
        sb.append(", incomeStageId=").append(incomeStageId);
        sb.append(", videoRecordId=").append(videoRecordId);
        sb.append(", userInfoId=").append(userInfoId);
        sb.append(", fromId=").append(fromId);
        sb.append(", openId=").append(openId);
        sb.append(", appOpenId=").append(appOpenId);
        sb.append(", nickName=").append(nickName);
        sb.append(", avatarUrl=").append(avatarUrl);
        sb.append(", gender=").append(gender);
        sb.append(", inviteCode=").append(inviteCode);
        sb.append(", tel=").append(tel);
        sb.append(", level=").append(level);
        sb.append(", levelImg=").append(levelImg);
        sb.append(", inviteTicketNum=").append(inviteTicketNum);
        sb.append(", petList=").append(petList);
        sb.append(", levelIncome=").append(levelIncome);
        sb.append(", goldAll=").append(goldAll);
        sb.append(", goldUsable=").append(goldUsable);
        sb.append(", profitSpeed=").append(profitSpeed);
        sb.append(", goldGetTime=").append(goldGetTime);
        sb.append(", isGoldGet=").append(isGoldGet);
        sb.append(", isEffect=").append(isEffect);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isStaus=").append(isStaus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}