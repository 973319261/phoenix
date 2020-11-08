package com.koi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.PowerRecord;
import com.koi.vo.MoneyRecordVo;
import com.koi.vo.PowerRecordVo;

/**
 * PowerRecordDAO继承基类
 */
@Repository
public interface PowerRecordDAO extends MyBatisBaseDao<PowerRecord, Integer> {
	/**
	 * 通过用户ID查询该用户算力总数
	 * @param userId
	 * @return
	 */
	public Integer findPowerNum(Integer userId);
	/**
	 * 查询零钱记录总数
	 * @param userId
	 * @return
	 */
	public int findPowerRecordCount(Integer userId);
	/**
	 * 查询零钱记录
	 * @param userId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<PowerRecordVo> findPowerRecord(@Param("userId")Integer userId,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
}