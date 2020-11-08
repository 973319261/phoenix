package com.koi.po;

import java.io.Serializable;

/**
 * video_record
 * @author 
 */
public class VideoRecord implements Serializable {
    private Integer videoRecordId;

    private Integer videoRecordNum;

    private Integer videoRecordUseNum;

    private static final long serialVersionUID = 1L;

    public Integer getVideoRecordId() {
        return videoRecordId;
    }

    public void setVideoRecordId(Integer videoRecordId) {
        this.videoRecordId = videoRecordId;
    }

    public Integer getVideoRecordNum() {
        return videoRecordNum;
    }

    public void setVideoRecordNum(Integer videoRecordNum) {
        this.videoRecordNum = videoRecordNum;
    }

    public Integer getVideoRecordUseNum() {
        return videoRecordUseNum;
    }

    public void setVideoRecordUseNum(Integer videoRecordUseNum) {
        this.videoRecordUseNum = videoRecordUseNum;
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
        VideoRecord other = (VideoRecord) that;
        return (this.getVideoRecordId() == null ? other.getVideoRecordId() == null : this.getVideoRecordId().equals(other.getVideoRecordId()))
            && (this.getVideoRecordNum() == null ? other.getVideoRecordNum() == null : this.getVideoRecordNum().equals(other.getVideoRecordNum()))
            && (this.getVideoRecordUseNum() == null ? other.getVideoRecordUseNum() == null : this.getVideoRecordUseNum().equals(other.getVideoRecordUseNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVideoRecordId() == null) ? 0 : getVideoRecordId().hashCode());
        result = prime * result + ((getVideoRecordNum() == null) ? 0 : getVideoRecordNum().hashCode());
        result = prime * result + ((getVideoRecordUseNum() == null) ? 0 : getVideoRecordUseNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", videoRecordId=").append(videoRecordId);
        sb.append(", videoRecordNum=").append(videoRecordNum);
        sb.append(", videoRecordUseNum=").append(videoRecordUseNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}