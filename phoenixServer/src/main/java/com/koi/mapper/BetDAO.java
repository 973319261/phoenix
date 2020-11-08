package com.koi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.Bet;
import com.koi.vo.BetVo;

/**
 * BetDAO继承基类
 */
@Repository
public interface BetDAO extends MyBatisBaseDao<Bet, Integer> {
	/**
	 * 查询投注总数
	 * @param issueId
	 * @return
	 */
	public int findBetCountByIssueId(Integer issueId);
	/**
	 * 查询投注明细信息
	 * @param issueId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<BetVo> findBetByIssueId(@Param("issueId")Integer issueId,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	/**
	 * 查询幸运儿
	 * @param issueId
	 * @return
	 */
	public BetVo findLuck(Integer issueId);
	/**
	 * 通过期号Id和幸运号码查询中奖用户Id
	 * @param issueId
	 * @param redeemCode
	 * @return
	 */
	public Integer findUserId(@Param("issueId")Integer issueId ,@Param("redeemCode")Integer redeemCode);
	/**
	 * 修改参与用户的是否中奖状态（0待开奖，1未中奖，2已中奖）
	 * @param issueId
	 * @param userId
	 * @return
	 */
	public int updateWinning(@Param("issueId")Integer issueId ,@Param("userId")Integer userId);
	
}