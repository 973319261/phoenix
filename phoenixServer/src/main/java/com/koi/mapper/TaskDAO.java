package com.koi.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koi.po.Task;

/**
 * TaskDAO继承基类
 */
@Repository
public interface TaskDAO extends MyBatisBaseDao<Task, Integer> {
	/**
	 * 查询所有任务列表
	 * @return
	 */
	  List<Task> findAll();
	
}