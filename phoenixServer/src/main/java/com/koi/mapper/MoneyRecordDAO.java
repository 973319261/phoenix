package com.koi.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.MoneyRecord;
import com.koi.vo.MoneyRecordVo;

/**
 * MoneyRecordDAO继承基类
 */
@Repository
public interface MoneyRecordDAO extends MyBatisBaseDao<MoneyRecord, Integer> {
	/**
	 * 查询余额
	 * @param userId
	 * @return
	 */
	public BigDecimal findBalance(Integer userId);
	/**
	 * 查询零钱记录总数
	 * @param userId
	 * @return
	 */
	public int findMoneyRecordCount(Integer userId);
	/**
	 * 查询零钱记录
	 * @param userId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<MoneyRecordVo> findMoneyRecord(@Param("userId")Integer userId,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	
}