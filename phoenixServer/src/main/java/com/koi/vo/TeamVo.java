package com.koi.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.koi.po.User;

public class TeamVo implements Serializable{
	private Integer userId;
	private Integer fromId;
	private String nickName;
	private Integer level;
	private Integer isEffect;
	private Date createTime;
	private List<TeamVo> children;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getFromId() {
		return fromId;
	}
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<TeamVo> getChildren() {
		return children;
	}
	public void setChildren(List<TeamVo> children) {
		this.children = children;
	}
	
	
}
