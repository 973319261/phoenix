package com.koi.po;

import java.io.Serializable;

/**
 * task_detail
 * @author 
 */
public class TaskDetail implements Serializable {
    private Integer taskDetailId;

    private Integer userId;

    private Integer taskId;

    private Integer hasDo;

    private Integer progressNum;

    private static final long serialVersionUID = 1L;

    public Integer getTaskDetailId() {
        return taskDetailId;
    }

    public void setTaskDetailId(Integer taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getHasDo() {
        return hasDo;
    }

    public void setHasDo(Integer hasDo) {
        this.hasDo = hasDo;
    }

    public Integer getProgressNum() {
        return progressNum;
    }

    public void setProgressNum(Integer progressNum) {
        this.progressNum = progressNum;
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
        TaskDetail other = (TaskDetail) that;
        return (this.getTaskDetailId() == null ? other.getTaskDetailId() == null : this.getTaskDetailId().equals(other.getTaskDetailId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getHasDo() == null ? other.getHasDo() == null : this.getHasDo().equals(other.getHasDo()))
            && (this.getProgressNum() == null ? other.getProgressNum() == null : this.getProgressNum().equals(other.getProgressNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaskDetailId() == null) ? 0 : getTaskDetailId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getHasDo() == null) ? 0 : getHasDo().hashCode());
        result = prime * result + ((getProgressNum() == null) ? 0 : getProgressNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskDetailId=").append(taskDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", taskId=").append(taskId);
        sb.append(", hasDo=").append(hasDo);
        sb.append(", progressNum=").append(progressNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}