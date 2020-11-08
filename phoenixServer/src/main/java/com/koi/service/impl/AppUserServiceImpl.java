package com.koi.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koi.mapper.DhcRecordDAO;
import com.koi.mapper.MoneyRecordDAO;
import com.koi.mapper.PetInfoDAO;
import com.koi.mapper.PetInfoDetailDAO;
import com.koi.mapper.PowerRecordDAO;
import com.koi.mapper.ProgressDAO;
import com.koi.mapper.SignDAO;
import com.koi.mapper.SignDetailDAO;
import com.koi.mapper.TaskDAO;
import com.koi.mapper.TaskDetailDAO;
import com.koi.mapper.UserDAO;
import com.koi.mapper.VideoRecordDAO;
import com.koi.mapper.WithdrawSettingDAO;
import com.koi.mapper.WithdrawSettingDetailDAO;
import com.koi.po.PetInfo;
import com.koi.po.PetInfoDetail;
import com.koi.po.PowerRecord;
import com.koi.po.Progress;
import com.koi.po.Sign;
import com.koi.po.SignDetail;
import com.koi.po.Task;
import com.koi.po.TaskDetail;
import com.koi.po.User;
import com.koi.po.VideoRecord;
import com.koi.po.WithdrawSetting;
import com.koi.po.WithdrawSettingDetail;
import com.koi.service.IAppUserService;
import com.koi.util.AppSeting;
import com.koi.util.InviteCodeUtil;
import com.koi.util.Tools;
import com.koi.vo.DhcRecordVo;
import com.koi.vo.JsonReturn;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;
import com.koi.vo.PowerRecordVo;
import com.koi.vo.TeamVo;

@Service
public class AppUserServiceImpl implements IAppUserService{
	private final String SMS_VALID_PHONE = "smsValidPhone";//验证手机号
	private final String SMS_VALID_CODE = "smsValidCode";//验证码
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Autowired UserDAO userDao;
	@Autowired ProgressDAO progressDao;
	@Autowired VideoRecordDAO videoRecordDao;
	@Autowired TaskDAO taskDao;
	@Autowired TaskDetailDAO taskDetailDao;
	@Autowired PetInfoDAO petInfoDao;
	@Autowired PetInfoDetailDAO petInfoDetailDao;
	@Autowired PowerRecordDAO powerRecordDao;
	@Autowired DhcRecordDAO dhcRecordDao;
	@Autowired SignDAO signDao;
	@Autowired SignDetailDAO signDetailDao;
	@Autowired MoneyRecordDAO moneyRecordDao;
	@Autowired WithdrawSettingDAO withdrawSettingDao;
	@Autowired WithdrawSettingDetailDAO withdrawSettingDetailDao;
	@Override
	public JsonReturn getValidationCode(String phone,HttpServletRequest request) {
		JsonReturn jsonReturn=new JsonReturn();
		if(Tools.isNotNull(phone)) {
			int total = userDao.findUserByPhone(phone);//查询手机号是否已被绑定
			if(total>0) {//已绑定
				jsonReturn.setCode(400);
				jsonReturn.setText("该手机号已被其他用户绑定");
			}else {//获取验证码
				String verificationCode="123456";
				HttpSession session=request.getSession();
				session.setAttribute(SMS_VALID_PHONE, phone);
				session.setAttribute(SMS_VALID_CODE, verificationCode);
				jsonReturn.setCode(200);
				jsonReturn.setText("验证码发送成功，请注意查收");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("请输入正确的手机号");
		}
		return jsonReturn;
	}
	@Transactional (propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public JsonReturn insertUserInfo(String phone, String validationCode, String inviteCode,HttpServletRequest request) {
		JsonReturn jsonReturn=new JsonReturn();
		Date nowDate;//当前时间
		User user;//用户
		User fromUser;//师傅信息
		Progress progress;//分红进度条
		VideoRecord videoRecord;//视频记录
		PowerRecord powerRecord;//能量记录
		TaskDetail taskDetail;//任务明细
		PetInfoDetail petInfoDetail;//宠物明细
		SignDetail signDetail;//签到明细
		WithdrawSettingDetail withdrawSettingDetail;//钱包设置明细
		List<Task> taskList;//所有任务列表
		List<TaskDetail> taskDetailList;//任务明细列表
		List<PetInfo> petInfoList;//所有宠物信息列表
		List<PetInfoDetail> petInfoDetailList;//宠物信息明细列表
		List<Sign> signList;//所有签到列表
		List<SignDetail> signDetailList;//签到明细列表
		List<WithdrawSetting> withdrawSettingList;//所有钱包设置列表
		List<WithdrawSettingDetail> withdrawSettingDetailList;//钱包设置明细列表
		if(Tools.isNotNull(phone) && Tools.isNotNull(validationCode)) {
			fromUser = userDao.findUserByInviteCode(inviteCode);//查询师傅信息
			if(Tools.isNotNull(inviteCode) && fromUser==null) {//邀请码不为空并且查询不到信息
				jsonReturn.setCode(400);
				jsonReturn.setText("邀请码输入错误");
				return jsonReturn;
			}
			HttpSession session=request.getSession();
			String smsValidPhone=(String) session.getAttribute(SMS_VALID_PHONE);//session中的手机号
			String smsValidCode=(String) session.getAttribute(SMS_VALID_CODE);//session中的验证码
			if(phone.equals(smsValidPhone) && validationCode.equals(smsValidCode)) {
				nowDate=new Date();		
				BigDecimal decimal=new BigDecimal("0");
				//初始化数据
				//新增分红进度
				progress=new Progress();//分红进度
				progress.setTudi(decimal);//徒弟进度
				progress.setTushu(decimal);//徒孙进度
				progress.setOther(decimal);//其他进度
				progressDao.insert(progress);
				videoRecord=new VideoRecord();//视频记录
				videoRecord.setVideoRecordNum(5);//观看总次数
				videoRecord.setVideoRecordUseNum(15);//每天可观看次数
				videoRecordDao.insert(videoRecord);//新增视频记录
				user=new User();//用户表
				user.setProgressId(progress.getProgressId());//设置分红进度ID
				user.setIncomeStageId(1);//第一阶段
				user.setInviteTicketNum(5000);//设置邀请卷数量
				user.setVideoRecordId(videoRecord.getVideoRecordId());//设置视频记录ID
				user.setOpenId("");//微信授权ID
				user.setAppOpenId("");//设备ID
				user.setNickName("测试昵称");//微信昵称
				user.setAvatarUrl("");//头像路径
				user.setGender(0);//性别
				if(Tools.isNotNull(inviteCode) && fromUser!=null) {
					user.setFromId(fromUser.getUserId());//设置师傅ID
				}
				user.setTel(phone.trim());//手机号
				user.setLevel(1);//设置初始等级
				user.setLevelImg(1);//设置初始等级图片
				user.setPetList("1");//设置初始宠物列表
				user.setLevelIncome("");//设置等级红包 6,16,26,36
				user.setGoldAll("16000");//所有金币
				user.setGoldUsable("16000");//可用金币
				user.setProfitSpeed("25");//收益速度 25/s
				user.setGoldGetTime(nowDate);//金币领取时间
				user.setIsGoldGet(0);//宝箱未领取
				user.setIsEffect(0);//是否已激活
				user.setLoginTime(null);//登录时间
				user.setCreateTime(nowDate);//创建时间
				user.setUpdateTime(nowDate);
				user.setIsStaus(1);//是否有效	
				userDao.insert(user);//新增
				User userUpdate=new User();
				userUpdate.setUserId(user.getUserId());
				userUpdate.setInviteCode(InviteCodeUtil.toSerialCode(user.getUserId()));
				userDao.updateByPrimaryKeySelective(userUpdate);//为用户添加邀请码
				taskList = taskDao.findAll();//查询所有任务列表
				taskDetailList = new ArrayList<TaskDetail>();
				for (Task task : taskList) {
					taskDetail=new TaskDetail();
					taskDetail.setUserId(user.getUserId());//用户ID
					taskDetail.setTaskId(task.getTaskId());//任务ID
					taskDetail.setHasDo(0);//未完成
					taskDetail.setProgressNum(0);//进度数
					if(task.getTaskId()==5) {// 绑定微信
						taskDetail.setHasDo(1);//已完成
						taskDetail.setProgressNum(1);//进度数
					}
					if(task.getTaskId()==6 && Tools.isNotNull(inviteCode)) {//填写邀请码
						taskDetail.setHasDo(1);//已完成
						taskDetail.setProgressNum(1);//进度数
					}
					taskDetailList.add(taskDetail);
				}
				taskDetailDao.insertByBatch(taskDetailList);//批量新增任务明细
				petInfoList = petInfoDao.findAll();//查询所有宠物信息列表
				petInfoDetailList=new ArrayList<PetInfoDetail>();
				for(PetInfo petInfo : petInfoList) {
					petInfoDetail=new PetInfoDetail();
					petInfoDetail.setUserId(user.getUserId());//用户ID
					petInfoDetail.setPetInfoId(petInfo.getPetInfoId());//宠物信息ID
					petInfoDetail.setPetGoldLevel(1);//宠物金币等级
					petInfoDetail.setIsCreate(0);//不可创建
					if(petInfo.getPetInfoId()<=5) {//五级以下的宠物
						if(petInfo.getPetInfoId()==1) {//一级宠物
							petInfoDetail.setIsCreate(1);//可创建
						}
						petInfoDetail.setIsLocked(1);//解锁
					}else {
						petInfoDetail.setIsLocked(0);//未解锁
					}
					petInfoDetailList.add(petInfoDetail);
				}
				petInfoDetailDao.insertByBatch(petInfoDetailList);//批量新增宠物信息
				signList=signDao.findAll();
				signDetailList=new ArrayList<SignDetail>();
				for (Sign sign : signList) {
					signDetail=new SignDetail();
					signDetail.setSignId(sign.getSignId());//签到id
					signDetail.setUserId(user.getUserId());//用户id
					signDetail.setIsSign(0);//未签到
					signDetailList.add(signDetail);
				}
				signDetailDao.insertByBatch(signDetailList);//批量新增签到明细信息
				withdrawSettingList=withdrawSettingDao.findAll();
				withdrawSettingDetailList=new ArrayList<WithdrawSettingDetail>();
				for (WithdrawSetting withdrawSetting : withdrawSettingList) {
					withdrawSettingDetail=new WithdrawSettingDetail();
					withdrawSettingDetail.setUserId(user.getUserId());//用户ID
					withdrawSettingDetail.setWithdrawSettingId(withdrawSetting.getWithdrawSettingId());//钱包设置ID
					withdrawSettingDetail.setIsSwitch(1);//钱包设置金额开关
					withdrawSettingDetailList.add(withdrawSettingDetail);
				}
				withdrawSettingDetailDao.insertByBatch(withdrawSettingDetailList);//批量新增钱包设置明细信息
				if(Tools.isNotNull(inviteCode)) {//邀请码不为空
					powerRecord = new PowerRecord();//能量记录
					powerRecord.setSourceTypeId(13);//绑定微信获取
					powerRecord.setUserId(user.getUserId());//用户ID
					powerRecord.setProfitNum(6);//能量数量
					powerRecord.setCreateTime(nowDate);//创建时间
					powerRecordDao.insert(powerRecord);//新增能量记录
				}
				
				jsonReturn.setCode(200);
				jsonReturn.setText("绑定成功");
				jsonReturn.setData(userUpdate);
			}else {
				jsonReturn.setCode(400);
				jsonReturn.setText("验证码输入错误");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("请填写完整信息");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn uploadUserAvatar(int userId, MultipartFile avatar,HttpServletRequest request) {
		JsonReturn jsonReturn=new JsonReturn();
		// 判断文件是否为空
		if (!avatar.isEmpty() && avatar.getSize() > 0) {
			// 判断memberId
			if (userId > 0) {
				User user =userDao.selectByPrimaryKey(userId);//查询用户信息
				if (avatar != null) {
					// 获取文件名称
					String fileName = avatar.getOriginalFilename();
					// 获取文件扩展名称
					String strExt = fileName.substring(fileName.lastIndexOf('.'));

					String phoneName = userId + "_" + System.currentTimeMillis() + "_" + System.nanoTime() + strExt;

					// 保存图片
					try {
						String serverPath= request.getSession().getServletContext().getRealPath("/avatar");//本地服务器taomcat
						FileUtils.writeByteArrayToFile(new File(serverPath,phoneName),avatar.getBytes());
						FileUtils.writeByteArrayToFile(new File(AppSeting.UPLOAD_AVATAR_URL, phoneName),
								avatar.getBytes());
						// 删除以前的图片
						if (Tools.isNotNull(user.getAvatarUrl())) {
							File oldImage = new File(AppSeting.UPLOAD_AVATAR_URL, user.getAvatarUrl());
							File oldImageServer = new File(serverPath, user.getAvatarUrl());
							if (oldImage.exists()) {
								oldImage.delete();
							}
							if (oldImageServer.exists()) {
								oldImageServer.delete();
							}
						}
						// 将头像的文件名称保存到数据库
						user.setAvatarUrl(phoneName);
						int intR = userDao.updateByPrimaryKey(user);
						if (intR > 0) {
							jsonReturn.setCode(200);
							jsonReturn.setText("头像上传成功");
							jsonReturn.setData(user);
						} else {
							jsonReturn.setCode(300);
							jsonReturn.setText("头像上传失败，稍后再试");
						}

					} catch (IOException e) {
						e.printStackTrace();
						jsonReturn.setCode(300);
						jsonReturn.setText("头像上传失败，稍后再试");
					}
				} else {
					jsonReturn.setCode(500);
					jsonReturn.setText("参数异常");
				}

			} else {
				jsonReturn.setCode(500);
				jsonReturn.setText("参数异常");
			}

		} else {
			jsonReturn.setCode(500);
			jsonReturn.setText("上传的头像为空");
		}

		return jsonReturn;
	}
	@Override
	public JsonReturn updateUserById(User user) {
		JsonReturn jsonReturn=new JsonReturn();
		int total=userDao.updateByPrimaryKeySelective(user);
		if (total>0) {
			jsonReturn.setCode(200);
		}else {
			jsonReturn.setCode(400);
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn findUserInfoById(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		//将两个需要返回的对象放入一个JSON对象中
		if(userId!=null) {
			User user = userDao.selectByPrimaryKey(userId);//查询用户信息
			BigDecimal dhcNum= dhcRecordDao.findDhcNum(userId);//查询Dhc总数
			Integer powerNum= powerRecordDao.findPowerNum(userId);//查询算力总数
			BigDecimal balance= moneyRecordDao.findBalance(userId);//查询余额
			JsonObject jsonObject=new JsonObject();
			jsonObject.add("user", gson.toJsonTree(user));
			jsonObject.add("dhcNum", gson.toJsonTree(dhcNum==null?0.0:dhcNum));
			jsonObject.add("powerNum", gson.toJsonTree(powerNum==null?0:powerNum));
			jsonObject.add("balance", gson.toJsonTree(balance==null?0.00:balance));
			jsonReturn.setCode(200);
			jsonReturn.setData(jsonObject);
		}
		
		return jsonReturn;
	}
	@Override
	public JsonReturn findTeamByUserId(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<TeamVo> list=userDao.findChildsByUserId(userId);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}
	@Override
	public JsonReturn findPowerRecord(Integer userId, PaginationPage page) {
		JsonReturn jsonReturn=new JsonReturn();
		int totalRows = powerRecordDao.findPowerRecordCount(userId);
		List<PowerRecordVo> list=powerRecordDao.findPowerRecord(userId,page.getStartIndex(), page.getPageSize());//获取零钱记录
		Integer powerNum=powerRecordDao.findPowerNum(userId);//算力总数量
		// 分页数据
		Pagination<PowerRecordVo> pagination = new Pagination<PowerRecordVo>();
		pagination.setCurrentPage(page.getCurrentPage());// 当前页
		pagination.setPageSize(page.getPageSize());// 每页大小
		pagination.setTotalRows(totalRows);// 总条数
		pagination.setData(list);// 本页数据
		pagination.setSuccess(true);// 成功
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("powerNum", gson.toJsonTree(powerNum==null?0:powerNum));
		jsonObject.add("pagination", gson.toJsonTree(pagination));
		jsonReturn.setData(jsonObject);
		return jsonReturn;
	}
	@Override
	public JsonReturn findDhcRecord(Integer userId, PaginationPage page) {
		JsonReturn jsonReturn=new JsonReturn();
		int totalRows = dhcRecordDao.findDhcRecordCount(userId);
		List<DhcRecordVo> list=dhcRecordDao.findDhcRecord(userId,page.getStartIndex(), page.getPageSize());//获取零钱记录
		BigDecimal dhcNum=dhcRecordDao.findDhcNum(userId);//Dhc总数
		// 分页数据
		Pagination<DhcRecordVo> pagination = new Pagination<DhcRecordVo>();
		pagination.setCurrentPage(page.getCurrentPage());// 当前页
		pagination.setPageSize(page.getPageSize());// 每页大小
		pagination.setTotalRows(totalRows);// 总条数
		pagination.setData(list);// 本页数据
		pagination.setSuccess(true);// 成功
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("dhcNum", gson.toJsonTree(dhcNum==null?0.0:dhcNum));
		jsonObject.add("pagination", gson.toJsonTree(pagination));
		jsonReturn.setData(jsonObject);
		return jsonReturn;
	}
	@Override
	public JsonReturn findFromUser(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		User user=userDao.findFromUser(userId);
		if (user!=null) {
			jsonReturn.setCode(200);
			jsonReturn.setData(user);
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("暂无上级");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn findTeamNumAndPowerNum(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		Map<String, Integer> tudi=userDao.findTeamNumAndPowerNum(userId, 4);//查询徒弟数量与通过徒弟获取的算力
		Map<String, Integer> tusun=userDao.findTeamNumAndPowerNum(userId, 5);//查询徒弟数量与通过徒弟获取的算力
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("tudi", gson.toJsonTree(tudi));
		jsonObject.add("tusun", gson.toJsonTree(tusun));
		jsonReturn.setCode(200);
		jsonReturn.setData(jsonObject);
		// TODO Auto-generated method stub
		return jsonReturn;
	}
	
}
