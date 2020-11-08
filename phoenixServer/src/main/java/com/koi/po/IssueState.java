package com.koi.po;

import java.io.Serializable;

/**
 * issue_state
 * @author 
 */
public class IssueState implements Serializable {
    private Integer issueStateId;

    private String issueStateName;

    private static final long serialVersionUID = 1L;

    public Integer getIssueStateId() {
        return issueStateId;
    }

    public void setIssueStateId(Integer issueStateId) {
        this.issueStateId = issueStateId;
    }

    public String getIssueStateName() {
        return issueStateName;
    }

    public void setIssueStateName(String issueStateName) {
        this.issueStateName = issueStateName;
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
        IssueState other = (IssueState) that;
        return (this.getIssueStateId() == null ? other.getIssueStateId() == null : this.getIssueStateId().equals(other.getIssueStateId()))
            && (this.getIssueStateName() == null ? other.getIssueStateName() == null : this.getIssueStateName().equals(other.getIssueStateName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIssueStateId() == null) ? 0 : getIssueStateId().hashCode());
        result = prime * result + ((getIssueStateName() == null) ? 0 : getIssueStateName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", issueStateId=").append(issueStateId);
        sb.append(", issueStateName=").append(issueStateName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}