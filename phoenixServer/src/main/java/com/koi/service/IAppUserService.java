package com.koi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.koi.po.User;
import com.koi.vo.JsonReturn;
import com.koi.vo.PaginationPage;

public interface IAppUserService {
	/**
	 * 通过手机号获取验证码
	 * @param phone
	 * @param request 
	 * @return
	 */
	public JsonReturn getValidationCode(String phone,HttpServletRequest request);
	/**
	 * 注册绑定手机号(新增用户信息)
	 * @param phone 手机号
	 * @param validationCode 验证码
	 * @param invitationCode 邀请码
	 * @param request 
	 * @return
	 */
	public JsonReturn insertUserInfo(String phone,String validationCode,String inviteCode,HttpServletRequest request);
	/**
	 * 上传用户头像
	 * @param userId
	 * @param avatar
	 * @return
	 */
	public JsonReturn uploadUserAvatar(int userId, MultipartFile avatar,HttpServletRequest request);
	/**
	 * 通过用户Id修改用户
	 * @param user
	 * @return
	 */
	public JsonReturn updateUserById(User user);
	/**
	 * 通过用户ID查询用户相关信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findUserInfoById(Integer userId);
	/**
	 * 通过用户ID查询我的团队信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findTeamByUserId(Integer userId);
	/**
	 * 通过用户ID查询算力记录
	 * @param userId
	 * @param page
	 * @return
	 */
	public JsonReturn findPowerRecord (Integer userId,PaginationPage page);
	/**
	 * 通过用户ID查询dhc记录
	 * @param userId
	 * @param page
	 * @return
	 */
	public JsonReturn findDhcRecord (Integer userId,PaginationPage page);
	/**
	 * 查询师傅信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findFromUser(Integer userId);
	/**
	 * 查询团队信息与算力信息
	 * @param userId
	 * @return
	 */
	public JsonReturn findTeamNumAndPowerNum(Integer userId);
}
