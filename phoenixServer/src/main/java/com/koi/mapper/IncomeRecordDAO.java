package com.koi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.IncomeRecord;

/**
 * IncomeRecordDAO继承基类
 */
@Repository
public interface IncomeRecordDAO extends MyBatisBaseDao<IncomeRecord, Integer> {
	/**
	 * 通过用户和日期查询收益(年月日)
	 * @param userId
	 * @param day
	 * @return
	 */
	public IncomeRecord findInComeRecordByDay(@Param("userId")Integer userId,@Param("day")String day);
	/**
	 * 查询收益记录条数(年日)
	 * @param userId
	 * @param date
	 * @return
	 */
	public int findIncomeRecordCountByDate(@Param("userId")Integer userId,@Param("date")String date);
	/**
	 * 查询收益记录(年日)
	 * @param userId
	 * @param date
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<IncomeRecord> findIncomeRecordByDate(@Param("userId")Integer userId,@Param("date")String date,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
}