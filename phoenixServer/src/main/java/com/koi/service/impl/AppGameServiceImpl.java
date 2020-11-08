package com.koi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koi.mapper.PetInfoDetailDAO;
import com.koi.mapper.UserDAO;
import com.koi.po.PetInfoDetail;
import com.koi.po.User;
import com.koi.service.IAppGameService;
import com.koi.vo.JsonReturn;
import com.koi.vo.PetInfoVo;
@Service
public class AppGameServiceImpl implements IAppGameService{
	@Autowired UserDAO userDao;
	@Autowired PetInfoDetailDAO petInfoDetailDao;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public JsonReturn findGameInfo(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		User user=userDao.selectByPrimaryKey(userId);//个人信息
		List<PetInfoVo> petList = petInfoDetailDao.findPetInfoByUserId(userId);//宠物列表
		List<User> rankingList=userDao.findRankingInfo();//排行榜列表
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("user", gson.toJsonTree(user));
		jsonObject.add("petList", gson.toJsonTree(petList));
		jsonObject.add("rankingList", gson.toJsonTree(rankingList));
		jsonReturn.setCode(200);
		jsonReturn.setData(jsonObject);
		return jsonReturn;
	}
	
	@Override
	public JsonReturn updateGameInfo(User user) {
		JsonReturn jsonReturn=new JsonReturn();
		int total=userDao.updateByPrimaryKeySelective(user);
		if(total>0) {
			jsonReturn.setCode(200);
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updatePetLevel(PetInfoDetail petInfoDetail,String goldUsable) {
		JsonReturn jsonReturn=new JsonReturn();
		//解锁当前等级
		petInfoDetail.setIsLocked(1);
		petInfoDetailDao.updatePetInfo(petInfoDetail);
		//修改当前等级-4的创建状态
		petInfoDetail.setIsCreate(1);
		petInfoDetail.setPetInfoId(petInfoDetail.getPetInfoId()-4);
		petInfoDetailDao.updatePetInfo(petInfoDetail);
		if(goldUsable!=null) {
			User user=new User();
			user.setUserId(petInfoDetail.getUserId());
			user.setGoldUsable(goldUsable);
			userDao.updateByPrimaryKeySelective(user);
		}
		jsonReturn.setCode(200);
		return jsonReturn;
	}

	@Override
	public JsonReturn updatePetGoldLevel(PetInfoDetail petInfoDetail) {
		JsonReturn jsonReturn=new JsonReturn();
		int total = petInfoDetailDao.updatePetInfo(petInfoDetail);
		if(total>0) {
			jsonReturn.setCode(200);
		}
		return jsonReturn;
	}
}
