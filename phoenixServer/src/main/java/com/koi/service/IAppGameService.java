package com.koi.service;

import com.koi.po.PetInfoDetail;
import com.koi.po.User;
import com.koi.vo.JsonReturn;

public interface IAppGameService {
	/**
	 * 获取游戏相关信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findGameInfo(Integer userId);
	/**
	 * 修改游戏信息
	 * @param user
	 * @return
	 */
	public JsonReturn updateGameInfo(User user);
	/**
	 * 修改宠物列表信息
	 * @param petInfoDetail
	 * @return
	 */
	public JsonReturn updatePetLevel(PetInfoDetail petInfoDetail,String goldUsable);
	/**
	 * 修改宠物列表金币等级
	 * @param petInfoDetail
	 * @return
	 */
	public JsonReturn updatePetGoldLevel(PetInfoDetail petInfoDetail);
}
