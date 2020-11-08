package com.koi.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.po.Bet;
import com.koi.service.IAppIssueService;
import com.koi.util.AppSeting;
import com.koi.util.Tools;
import com.koi.vo.PaginationPage;

/**
 * App抽奖操作
 * @author koi
 *
 */
@Controller
@RequestMapping("/app/issue")
public class AppIssueController {
	@Autowired IAppIssueService issueService;// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 获取奖品图片
	 */
	@RequestMapping(value = "/getPrizePicture")
	public ResponseEntity<byte[]> getPrizePicture(String pictureName) throws IOException {
		if (Tools.isNotNull(pictureName)) {
			// 文件
			File file = new File(AppSeting.UPLOAD_PRIZE_URL, pictureName);
			if (file.exists()) {// 判断文件是否存在
				// 设置Header
				HttpHeaders headers = new HttpHeaders();
				// 设置为图片类型
				headers.setContentType(MediaType.IMAGE_JPEG);
				headers.setContentDispositionFormData("attachment", pictureName);

				// 返回文件相关数据
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			}
		}
		return null;
	}

	/**
	 * 获取今天期号信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIssueByDay", produces = "application/json;charset=UTF-8")
	public Object getIssueByDay() {
		result=issueService.findIssueByDay();
		return gson.toJson(result);
	}
	/**
	 * 通过期号Id获取投注记录
	 * @param issueId 期号ID
	 * @param page 分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBetByIssueId", produces = "application/json;charset=UTF-8")
	public Object getBetByIssueId(Integer issueId,PaginationPage page) {
		result=issueService.findBetByIssueId(issueId,page);
		return gson.toJson(result);
	}
	/**
	 * 获取抽奖条件
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDrawCondition", produces = "application/json;charset=UTF-8")
	public Object getDrawCondition(Integer userId) {
		result=issueService.findDrawCondition(userId);
		return gson.toJson(result);
	}
	/**
	 * 下注
	 * @param issueId 期号ID
	 * @param userId 用户ID
	 * @param betNum 下注数量
	 * @param inviteNum 当前邀请卷
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBet", produces = "application/json;charset=UTF-8")
	public Object addBet(Bet bet,Integer inviteNum) {
		result=issueService.insertBet(bet,inviteNum);
		return gson.toJson(result);
	}
	/**
	 * 通过期号和用户ID查询兑换码
	 * @param issueId
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRedeemCode", produces = "application/json;charset=UTF-8")
	public Object getRedeemCode(Integer issueId,Integer userId) {
		result=issueService.findRedeemCode(issueId,userId);
		return gson.toJson(result);
	}
	/**
	 * 获取参与记录
	 * @param userId
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getParticipateRecord", produces = "application/json;charset=UTF-8")
	public Object getParticipateRecord(Integer userId,PaginationPage page) {
		result=issueService.findParticipateRecord(userId,page);
		return gson.toJson(result);
	}
}
