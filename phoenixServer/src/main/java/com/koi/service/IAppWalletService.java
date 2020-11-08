package com.koi.service;

import java.math.BigDecimal;

import com.koi.vo.JsonReturn;
import com.koi.vo.MoneyRecordVo;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;

public interface IAppWalletService {
	/**
	 * 查询零钱记录
	 * @param userId
	 * @param page 分页
	 * @return
	 */
	public Pagination<MoneyRecordVo> findMoneyRecord(Integer userId,PaginationPage page);
	/**
	 * 查询钱包设置信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findWithdrawSetting(Integer userId);
	/**
	 * 提交（添加）零钱记录
	 * @param userId 用户Id
	 * @param moenySoureId 零钱来源Id
	 * @param moneyStateId 零钱状态Id
	 * @param money 零钱金额
	 * @param moneyType 零钱类型
	 * @return
	 */
	public JsonReturn addMoneyRecord(Integer userId,Integer moneySourceId,Integer moneyStateId,BigDecimal moeny,Integer moneyType);
}
