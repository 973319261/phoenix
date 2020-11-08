package com.koi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.po.PetInfoDetail;
import com.koi.po.User;
import com.koi.service.IAppGameService;

/**
 * App游戏服务
 * @author ZLY
 *
 */
@Controller
@RequestMapping("/app/game")
public class AppGameController {
	@Autowired IAppGameService appGameService;
	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	// 获取游戏相关信息
	@ResponseBody
	@RequestMapping(value = "/getGameInfo", produces = "application/json;charset=UTF-8")
	public Object getGameInfo(Integer userId) {
		result = appGameService.findGameInfo(userId);
		return gson.toJson(result);
	}
	/**
	 * 修改游戏相关信息
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateGameInfo", produces = "application/json;charset=UTF-8")
	public Object updateGameInfo(User user) {
		result=appGameService.updateGameInfo(user);
		return gson.toJson(result);
	}
	/**
	 * 修改宠物列表等级（创建等级与等级解锁）
	 * @param petInfoDetail
	 * @param goldUsable 可用金币
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePetLevel", produces = "application/json;charset=UTF-8")
	public Object updatePetLevel(PetInfoDetail petInfoDetail,String goldUsable) {
		result=appGameService.updatePetLevel(petInfoDetail,goldUsable);
		return gson.toJson(result);
	}
	/**
	 * 修改宠物列表金币等级
	 * @param petInfoDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePetGoldLevel", produces = "application/json;charset=UTF-8")
	public Object updatePetGoldLevel(PetInfoDetail petInfoDetail) {
		result=appGameService.updatePetGoldLevel(petInfoDetail);
		return gson.toJson(result);
	}
}
