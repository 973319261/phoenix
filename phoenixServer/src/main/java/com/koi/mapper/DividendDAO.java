package com.koi.mapper;

import com.koi.po.Dividend;
import org.springframework.stereotype.Repository;

/**
 * DividendDAO继承基类
 */
@Repository
public interface DividendDAO extends MyBatisBaseDao<Dividend, Integer> {
	/**
	 * 通过用户ID查询是已存在分红
	 * @param userId
	 * @return
	 */
	public Dividend findDividenByUserId(Integer userId);
}