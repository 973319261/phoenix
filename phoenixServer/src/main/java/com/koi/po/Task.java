package com.koi.po;

import java.io.Serializable;

/**
 * task
 * @author 
 */
public class Task implements Serializable {
    private Integer taskId;

    private String taskName;

    private String rewardState;

    private String taskIcon;

    private Integer rewardNum;

    private Integer taskSort;

    private Integer taskType;

    private Integer rewardType;

    private Integer taskTotal;
    

    private static final long serialVersionUID = 1L;

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

    public Integer getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(Integer taskTotal) {
        this.taskTotal = taskTotal;
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
        Task other = (Task) that;
        return (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskName() == null ? other.getTaskName() == null : this.getTaskName().equals(other.getTaskName()))
            && (this.getRewardState() == null ? other.getRewardState() == null : this.getRewardState().equals(other.getRewardState()))
            && (this.getTaskIcon() == null ? other.getTaskIcon() == null : this.getTaskIcon().equals(other.getTaskIcon()))
            && (this.getRewardNum() == null ? other.getRewardNum() == null : this.getRewardNum().equals(other.getRewardNum()))
            && (this.getTaskSort() == null ? other.getTaskSort() == null : this.getTaskSort().equals(other.getTaskSort()))
            && (this.getTaskType() == null ? other.getTaskType() == null : this.getTaskType().equals(other.getTaskType()))
            && (this.getRewardType() == null ? other.getRewardType() == null : this.getRewardType().equals(other.getRewardType()))
            && (this.getTaskTotal() == null ? other.getTaskTotal() == null : this.getTaskTotal().equals(other.getTaskTotal()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getRewardState() == null) ? 0 : getRewardState().hashCode());
        result = prime * result + ((getTaskIcon() == null) ? 0 : getTaskIcon().hashCode());
        result = prime * result + ((getRewardNum() == null) ? 0 : getRewardNum().hashCode());
        result = prime * result + ((getTaskSort() == null) ? 0 : getTaskSort().hashCode());
        result = prime * result + ((getTaskType() == null) ? 0 : getTaskType().hashCode());
        result = prime * result + ((getRewardType() == null) ? 0 : getRewardType().hashCode());
        result = prime * result + ((getTaskTotal() == null) ? 0 : getTaskTotal().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", taskName=").append(taskName);
        sb.append(", rewardState=").append(rewardState);
        sb.append(", taskIcon=").append(taskIcon);
        sb.append(", rewardNum=").append(rewardNum);
        sb.append(", taskSort=").append(taskSort);
        sb.append(", taskType=").append(taskType);
        sb.append(", rewardType=").append(rewardType);
        sb.append(", taskTotal=").append(taskTotal);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}