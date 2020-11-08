package com.koi.service;

import com.koi.po.Dividend;
import com.koi.vo.JsonReturn;

public interface IAppDividendSerivce {

	/**
	 * 新增分红
	 * @param userId
	 * @param sourceTypeId
	 * @return
	 */
	public JsonReturn insertDividend(Integer userId,Integer sourceTypeId);
}
