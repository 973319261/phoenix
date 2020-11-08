package com.koi.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.DhcRecord;
import com.koi.vo.DhcRecordVo;

/**
 * DhcRecordDAO继承基类
 */
@Repository
public interface DhcRecordDAO extends MyBatisBaseDao<DhcRecord, Integer> {
	/**
	 * 通过用户ID查询该用户Dhc总数
	 * @param userId
	 * @return
	 */
	public BigDecimal findDhcNum(Integer userId);
	/**
	 * 查询dhc记录总数
	 * @param userId
	 * @return
	 */
	public int findDhcRecordCount(Integer userId);
	/**
	 * 查询dhc记录
	 * @param userId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DhcRecordVo> findDhcRecord(@Param("userId")Integer userId,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
}