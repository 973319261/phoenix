package com.koi.service;

import java.util.List;

import com.koi.po.Issue;

/**
 * 每天定时更新数据
 * @author ZLY
 *
 */
public interface ITimerTaskSerivce {
	/**
	 * 更新所有任务明细（每天重置）
	 * @return
	 */
	public int updateAllTaskDetail();
	/**
	 * 每周更新签到列表信息（每周重置）
	 * @return
	 */
	public int updateAllSignDetail();
	/**
	 * 添加期号
	 * @return
	 */
	public List<Issue> insertIssue();
	/**
	 * 期号开奖
	 * @param issueId
	 * @return
	 */
	public int lotteryIssue(Integer issueId);
}
