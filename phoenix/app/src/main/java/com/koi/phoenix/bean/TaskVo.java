package com.koi.phoenix.bean;

import java.io.Serializable;

public class TaskVo implements Serializable{
	private Integer taskId;

    private String taskName;

    private String rewardState;

    private String taskIcon;

    private Integer rewardNum;
    
    private Integer taskSort;
    
    private Integer taskType;

    private Integer rewardType;
    
    private Integer hasDo;
    
    private Integer taskTotal;
    
    private Integer progressNum;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRewardState() {
		return rewardState;
	}

	public void setRewardState(String rewardState) {
		this.rewardState = rewardState;
	}

	public String getTaskIcon() {
		return taskIcon;
	}

	public void setTaskIcon(String taskIcon) {
		this.taskIcon = taskIcon;
	}

	public Integer getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}

	public Integer getTaskSort() {
		return taskSort;
	}

	public void setTaskSort(Integer taskSort) {
		this.taskSort = taskSort;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }
	public Integer getHasDo() {
		return hasDo;
	}

	public void setHasDo(Integer hasDo) {
		this.hasDo = hasDo;
	}

	public Integer getTaskTotal() {
		return taskTotal;
	}

	public void setTaskTotal(Integer taskTotal) {
		this.taskTotal = taskTotal;
	}

	public Integer getProgressNum() {
		return progressNum;
	}

	public void setProgressNum(Integer progressNum) {
		this.progressNum = progressNum;
	}
    
    
}
