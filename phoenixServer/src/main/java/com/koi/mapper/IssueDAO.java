package com.koi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.Issue;
import com.koi.vo.IssueVo;

/**
 * IssueDAO继承基类
 */
@Repository
public interface IssueDAO extends MyBatisBaseDao<Issue, Integer> {
	/**
	 * 批量新增期号
	 * @param list
	 * @return
	 */
	public int insertByBatch(List<Issue> list);
	/**
	 * 通过id查询期号信息
	 * @param issueId
	 * @return
	 */
	public IssueVo findIssueById(Integer issueId);
	/**
	 * 查询今天的期号
	 * @param day 今天日期
	 * @return
	 */
	public List<IssueVo> findIssueByDay(String day);
	/**
	 * 通过开奖状态id查询期号列表
	 * @param issueStateId
	 * @return
	 */
	public List<Issue> findIssueByStateId(Integer issueStateId);
	/**
	 * 获取参与总条数
	 * @param userId
	 * @return
	 */
	public int findParticipateCount(Integer userId);
	/**
	 * 获取用户参与记录
	 * @param userId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<IssueVo> findParticipateRecord(@Param("userId")Integer userId,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);	
}