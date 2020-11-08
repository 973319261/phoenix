package com.koi.web;


import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.koi.po.SignDetail;
import com.koi.po.TaskDetail;
import com.koi.service.IAppTaskService;
import com.koi.util.AppSeting;
import com.koi.util.Tools;
import com.koi.vo.JsonReturn;
import com.koi.vo.SignVo;

/**
 * App端任务页面服务
 * @author koi
 *
 */
@Controller
@RequestMapping("/app/task")
public class AppTaskController {
	@Autowired
	IAppTaskService appTaskService;
	public static final String Online_UserId ="OnlineUserId";//在线用户id
	public static final String Online_Minutes ="OnlineMinutes";//在线分钟

	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 获取当前用户任务列表
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTaskByUserId", produces = "application/json;charset=UTF-8")
	public Object getTaskByUserId(Integer userId) {
		result = appTaskService.findTaskByUserId(userId);
		return gson.toJson(result);
	}
	/**
	 * app 获取图标
	 *
	 * 
	 * @param pictureName 图片名称
	 * @return 图片二进制信息
	 * @throws IOException
	 */
	@RequestMapping(value = "/getIcon")
	public ResponseEntity<byte[]> getIcon(String pictureName) throws IOException {
		if (Tools.isNotNull(pictureName)) {
			// 文件
			File file = new File(AppSeting.UPLOAD_ICON_URL, pictureName);
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
	 * / 修改当前用户任务明细
	 * @param taskDetail 任务明细
	 * @param rewardNum 奖励数
	 * @param rewardType 奖励类型
	 * @param sourceTypeId来源Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTaskDetailHasDo", produces = "application/json;charset=UTF-8")
	public Object updateTaskDetailHasDo(TaskDetail taskDetail,Integer rewardNum,Integer rewardType,Integer sourceTypeId) {
		result = appTaskService.updateTaskDetailHasDo(taskDetail,rewardNum,rewardType,sourceTypeId);
		return gson.toJson(result);
	}
	/**
	 * 修改任务进度
	 * @param userId
	 * @param taskId
	 * @param sourceTypeId
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTaskDetailProgress", produces = "application/json;charset=UTF-8")
	public Object updateTaskDetailProgress(Integer userId,Integer taskId,Integer sourceTypeId) {
		result=appTaskService.updateTaskDetailProgress(userId,taskId,sourceTypeId);
		return gson.toJson(result);
	}
	/**
	 * 通过用户ID查询签到表
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSignByUserId", produces = "application/json;charset=UTF-8")
	public Object getSignByUserId(Integer userId) {
		result = appTaskService.findSignByUserId(userId);
		return gson.toJson(result);
	}
	/**
	 * 修改签到列表
	 * @param signVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSignDetail", produces = "application/json;charset=UTF-8")
	public Object updateSignDetail(SignVo signVo) {
		result = appTaskService.updateSignDetail(signVo);
		return gson.toJson(result);
	}
}
