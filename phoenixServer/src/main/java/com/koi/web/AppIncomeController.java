package com.koi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.service.IAppIncomeService;
import com.koi.vo.PaginationPage;

/**
 * App端收益页面服务
 * @author koi
 *
 */
@Controller
@RequestMapping("/app/income")
public class AppIncomeController {
	@Autowired
	IAppIncomeService appIncomeService;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	// 返回参数
	private Object result;
	/**
	 * 查询收益信息
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIncomeByUserId", produces = "application/json;charset=UTF-8")
	public Object getIncomeByUserId(Integer userId) {
		result=appIncomeService.findIncomeByUserId(userId);
		return gson.toJson(result);
	}
	/**
	 * 通过年日获取收益记录
	 * @param userId
	 * @param date
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIncomeByDate", produces = "application/json;charset=UTF-8")
	public Object getIncomeByDate(Integer userId,String date,PaginationPage page) {
		result=appIncomeService.findIncomeByDate(userId,date,page);
		return gson.toJson(result);
	}
	/**
	 * 更改阶段
	 * @param userId
	 * @param incomeStageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIncomeStage", produces = "application/json;charset=UTF-8")
	public Object updateIncomeStage(Integer userId,Integer incomeStageId) {
		result=appIncomeService.updateIncomeStage(userId,incomeStageId);
		return gson.toJson(result);
	}
}
