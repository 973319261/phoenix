package com.koi.service;

import com.koi.po.Bet;
import com.koi.vo.IssueVo;
import com.koi.vo.JsonReturn;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;

public interface IAppIssueService {
	/**
	 * 获取今天的期号
	 * @return
	 */
	public JsonReturn findIssueByDay();
	/**
	 * 通过期号Id查询投注记录
	 * @param issueId 期号ID
	 * @param page 分页
	 * @return
	 */
	public JsonReturn findBetByIssueId(Integer issueId,PaginationPage page);
	/**
	 * 获取抽奖条件
	 * @param userId
	 * @return
	 */
	public JsonReturn findDrawCondition(Integer userId);
	/**
	 * 下注
	 * inviteNum 当前邀请卷
	 * @return
	 */
	public JsonReturn insertBet(Bet bet,Integer inviteNum);
	/**
	 * 通过期号ID和用户ID查询兑换码
	 * @param issueId
	 * @param userId
	 * @return
	 */
	public JsonReturn findRedeemCode(Integer issueId,Integer userId);
	/**
	 * 获取用户参与记录
	 * @param userId
	 * @param page 分页
	 * @return
	 */
	public Pagination<IssueVo> findParticipateRecord(Integer userId,PaginationPage page);
}
