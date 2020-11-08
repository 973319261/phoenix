package com.koi.service;

import com.koi.po.IncomeRecord;
import com.koi.vo.JsonReturn;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;

public interface IAppIncomeService {
	/**
	 * 通过用户ID查询收益
	 * @param userId
	 * @return
	 */
	public JsonReturn findIncomeByUserId(Integer userId);
	
	/**
	 * 查询收益记录
	 * @param userId
	 * @param date
	 * @param page
	 * @return
	 */
	public Pagination<IncomeRecord> findIncomeByDate(Integer userId,String date,PaginationPage page);
	/**
	 * 更改阶段
	 * @param userId
	 * @param incomeStageId
	 * @return
	 */
	public JsonReturn updateIncomeStage(Integer userId,Integer incomeStageId);
}
