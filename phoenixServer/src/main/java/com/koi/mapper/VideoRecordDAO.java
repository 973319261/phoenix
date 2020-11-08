package com.koi.mapper;

import com.koi.po.VideoRecord;
import org.springframework.stereotype.Repository;

/**
 * VideoRecordDAO继承基类
 */
@Repository
public interface VideoRecordDAO extends MyBatisBaseDao<VideoRecord, Integer> {
	/**
	 * 通过用户ID查询观看视频记录
	 * @param userId
	 * @return
	 */
	public VideoRecord findVideoRecordByUserId(Integer userId);
}