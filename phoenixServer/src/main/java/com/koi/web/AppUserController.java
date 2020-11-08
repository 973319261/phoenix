package com.koi.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.po.User;
import com.koi.service.IAppUserService;
import com.koi.util.AppSeting;
import com.koi.util.Tools;
import com.koi.vo.JsonReturn;
import com.koi.vo.PaginationPage;
/**
 * App端用户页面服务
 * @author koi
 *
 */
@Controller
@RequestMapping("/app/user")
public class AppUserController{
	@Autowired
	IAppUserService appUserService;
	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	// 获取验证码
	@ResponseBody
	@RequestMapping(value = "/getValidationCode", produces = "application/json;charset=UTF-8")
	public Object getValidationCode(String phone,HttpServletRequest request) {
		result = appUserService.getValidationCode(phone, request);
		return gson.toJson(result);
	}
	/**
	 * 注册绑定手机号
	 * @param phone
	 * @param register
	 * @return
	 * @throws CustomException 
	 */
	@ResponseBody
	@RequestMapping(value = "/register", produces = "application/json;charset=UTF-8")
	public Object register(String phone,String validationCode,String inviteCode,HttpServletRequest request) {
		result=appUserService.insertUserInfo(phone, validationCode, inviteCode, request);
		return gson.toJson(result);
	}
	/**
	 *  通过用户Id修改用户
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserById", produces = "application/json;charset=UTF-8")
	public Object updateUserById(User user) {
		result=appUserService.updateUserById(user);
		return gson.toJson(result);
	}
	/**
	 * 上传用户头像
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadUserAvatar", produces = "application/json;charset=UTF-8")
	public Object uploadUserAvatar(int userId, @RequestParam(value = "avatar") MultipartFile avatar,HttpServletRequest request) {
		result=appUserService.uploadUserAvatar(userId, avatar,request);
		return gson.toJson(result);
	}

	/**
	 * 获取用户头像
	 */
	@RequestMapping(value = "/getUserAvatar")
	public ResponseEntity<byte[]> getUserAvatar(String pictureName) throws IOException {
		if (Tools.isNotNull(pictureName)) {
			// 文件
			File file = new File(AppSeting.UPLOAD_AVATAR_URL, pictureName);
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
	 * 通过用户ID查询用户相关信息
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfoById", produces = "application/json;charset=UTF-8")
	public Object getUserInfoById(Integer userId) {
		result=appUserService.findUserInfoById(userId);
		return gson.toJson(result);
	}
	/**
	 * 通过用户ID查询我的团队信息
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeamByUserId", produces = "application/json;charset=UTF-8")
	public Object getTeamByUserId(Integer userId) {
		result=appUserService.findTeamByUserId(userId);
		return gson.toJson(result);
	}
	/**
	 * 通过用户Id查询算力记录
	 * @param userId
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPowerRecord", produces = "application/json;charset=UTF-8")
	public Object getPowerRecord(Integer userId,PaginationPage page) {
		result=appUserService.findPowerRecord(userId,page);
		return gson.toJson(result);
	}
	/**
	 * 通过用户Id查询dh记录
	 * @param userId
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDhcRecord", produces = "application/json;charset=UTF-8")
	public Object getDhcRecord(Integer userId,PaginationPage page) {
		result=appUserService.findDhcRecord(userId,page);
		return gson.toJson(result);
	}
	/**
	 * 查询师傅信息
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFromUser", produces = "application/json;charset=UTF-8")
	public Object getFromUser(Integer userId) {
		result=appUserService.findFromUser(userId);
		return gson.toJson(result);
	}
	/**
	 * 获取团队数量与算力数量（邀请好友页面）
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeamNumAndPowerNum", produces = "application/json;charset=UTF-8")
	public Object getTeamNumAndPowerNum(Integer userId) {
		result=appUserService.findTeamNumAndPowerNum(userId);
		return gson.toJson(result);
	}
}
