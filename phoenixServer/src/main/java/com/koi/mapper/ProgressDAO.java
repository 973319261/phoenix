package com.koi.mapper;

import com.koi.po.IncomeStage;
import com.koi.po.Progress;
import org.springframework.stereotype.Repository;

/**
 * ProgressDAO继承基类
 */
@Repository
public interface ProgressDAO extends MyBatisBaseDao<Progress, Integer> {
	/**
	 * 通过用户ID查询分红进度
	 * @param userId
	 * @return
	 */
	public Progress findProgressById(Integer userId);
}