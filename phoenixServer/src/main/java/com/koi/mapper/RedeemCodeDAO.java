package com.koi.mapper;

import com.koi.po.RedeemCode;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * RedeemCodeDAO继承基类
 */
@Repository
public interface RedeemCodeDAO extends MyBatisBaseDao<RedeemCode, Integer> {
	/**
	 * 批量新增兑换码
	 * @param redeemCodeList
	 * @return
	 */
	public int insertByBatch(List<RedeemCode> redeemCodeList);
	/**
	 * 查询兑换码
	 * @param issueId
	 * @param userId
	 * @return
	 */
	public List<String> findRedeemCode(@Param("issueId")Integer issueId,@Param("userId")Integer userId);
}