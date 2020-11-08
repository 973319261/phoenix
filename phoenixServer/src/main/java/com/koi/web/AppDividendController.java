package com.koi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.service.IAppDividendSerivce;
import com.koi.service.IAppIncomeService;

/**
 * app端分红操作
 * @author ZLY
 *
 */
@Controller
@RequestMapping("/app/dividend")
public class AppDividendController {
	@Autowired
	IAppDividendSerivce appDividendSerivce;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	// 返回参数
	private Object result;
	
	/**
	 * 新增分红
	 * @param userId
	 * @param sourceTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertDividend", produces = "application/json;charset=UTF-8")
	public Object insertDividend(Integer userId,Integer sourceTypeId) {
		result=appDividendSerivce.insertDividend(userId, sourceTypeId);
		return gson.toJson(result);
	}
}
