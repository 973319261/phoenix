package com.koi.mapper;

import com.koi.po.IncomeStage;
import org.springframework.stereotype.Repository;

/**
 * IncomeStageDAO继承基类
 */
@Repository
public interface IncomeStageDAO extends MyBatisBaseDao<IncomeStage, Integer> {
	/**
	 * 通过用户ID查询收益阶段
	 * @param userId
	 * @return
	 */
	public IncomeStage findIncomeStageById(Integer userId);
}