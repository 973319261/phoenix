package com.koi.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.service.IAppWalletService;
import com.koi.vo.PaginationPage;

/**
 * App零钱操作
 * @author koi
 *
 */
@Controller
@RequestMapping("/app/wallet")
public class AppWalletController {
	@Autowired
	IAppWalletService appWalletService;
	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 获取零钱记录
	 * @param phone
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMoneyRecord", produces = "application/json;charset=UTF-8")
	public Object getMoneyRecord(Integer userId,PaginationPage page) {
		result = appWalletService.findMoneyRecord(userId,page);
		return gson.toJson(result);
	}
	/**
	 * 获取钱包设置明细信息
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWithdrawSetting", produces = "application/json;charset=UTF-8")
	public Object getWithdrawSetting(Integer userId) {
		result = appWalletService.findWithdrawSetting(userId);
		return gson.toJson(result);
	}
	/**
	 * 提交（添加）零钱记录
	 * @param userId 用户Id
	 * @param moenySoureId 零钱来源Id
	 * @param moneyStateId 零钱状态Id
	 * @param money 零钱金额
	 * @param moneyType 零钱类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addMoneyRecord", produces = "application/json;charset=UTF-8")
	public Object addMoneyRecord(Integer userId,Integer moneySourceId,Integer moneyStateId,BigDecimal money,Integer moneyType) {
		result = appWalletService.addMoneyRecord(userId, moneySourceId, moneyStateId, money, moneyType);
		return gson.toJson(result);
	}
}
