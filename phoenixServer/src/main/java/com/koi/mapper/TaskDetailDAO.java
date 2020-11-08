package com.koi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.TaskDetail;
import com.koi.vo.TaskVo;

/**
 * TaskDetailDAO继承基类
 */
@Repository
public interface TaskDetailDAO extends MyBatisBaseDao<TaskDetail, Integer> {
	/**
	 * 更新所有任务明细信息（重置任务）
	 * @return
	 */
	public int updateAllTaskDetail();
	/**
	 * 批量新增任务明细
	 * @param taskDetailList
	 * @return
	 */
	public int insertByBatch(List<TaskDetail> taskDetailList);
	/**
	 * 通过用户当前用户任务明细
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public List<TaskVo> findTaskDetail(@Param("userId")Integer userId,@Param("taskId")Integer taskId);
	/**
	 * 修改任务列表完成状态（单个）
	 * @param taskDetail
	 * @return
	 */
	public int updateTaskDetailHasDo(TaskDetail taskDetail); 
}