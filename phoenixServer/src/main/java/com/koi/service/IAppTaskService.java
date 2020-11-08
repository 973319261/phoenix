package com.koi.service;

import javax.servlet.http.HttpServletRequest;

import com.koi.po.TaskDetail;
import com.koi.vo.JsonReturn;
import com.koi.vo.SignVo;

public interface IAppTaskService {
	/**
	 * 通过用户id查询当前任务列表
	 * @param userId
	 * @return
	 */
	public JsonReturn findTaskByUserId(Integer userId);
	/**
	 * 修改当前用户任务完成状态
	 * @param taskDetail 任务明细
	 * @param rewardNum 奖励数
	 * @param rewardType 奖励类型
	 * @param sourceTypeId 来源Id
	 * @return
	 */
	public JsonReturn updateTaskDetailHasDo(TaskDetail taskDetail,Integer rewardNum,Integer rewardType,Integer sourceTypeId);
	/**
	 * 修改任务进度
	 * @param userId 用户ID
	 * @param taskId 任务ID
	 * @param sourceTypeId 来源ID
	 * @return
	 */
	public JsonReturn updateTaskDetailProgress(Integer userId, Integer taskId,Integer sourceTypeId);
	/**
	 * 通过用户ID查询签到信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findSignByUserId(Integer userId);
	/**
	 * 修改签到列表
	 * @param signVo
	 * @return
	 */
	public JsonReturn updateSignDetail(SignVo signVo);
}
