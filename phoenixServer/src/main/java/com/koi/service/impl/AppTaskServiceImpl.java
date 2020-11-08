package com.koi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koi.mapper.PowerRecordDAO;
import com.koi.mapper.SignDetailDAO;
import com.koi.mapper.TaskDetailDAO;
import com.koi.po.PowerRecord;
import com.koi.po.TaskDetail;
import com.koi.service.IAppTaskService;
import com.koi.vo.JsonReturn;
import com.koi.vo.SignVo;
import com.koi.vo.TaskVo;

@Service
public class AppTaskServiceImpl implements IAppTaskService{
	@Autowired TaskDetailDAO taskDetailDao;
	@Autowired PowerRecordDAO powerRecordDao;
	@Autowired SignDetailDAO signDetailDao;
	private Date nowDate=new Date();//当前时间
	private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Override
	public JsonReturn findTaskByUserId(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(userId!=null) {
			List<TaskVo> list=taskDetailDao.findTaskDetail(userId,0);//查询该用户任务明细
			Integer powerNum = powerRecordDao.findPowerNum(userId);//查询power数量
			if (list.size()>0) {
				JsonObject jsonObject=new JsonObject();
				jsonObject.add("powerNum", gson.toJsonTree(powerNum==null?0:powerNum));
				jsonObject.add("taskVoList", gson.toJsonTree(list));
				jsonReturn.setCode(200);
				jsonReturn.setData(jsonObject);
			}else {
				jsonReturn.setCode(400);
				jsonReturn.setText("任务列表为空");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn updateTaskDetailHasDo(TaskDetail taskDetail,Integer rewardNum,Integer rewardType,Integer sourceTypeId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(taskDetail!=null) {
			int total = taskDetailDao.updateTaskDetailHasDo(taskDetail);
			if(total>0) {
				if(rewardType==0) {//奖励算力
					PowerRecord powerRecord=new PowerRecord();
					powerRecord.setUserId(taskDetail.getUserId());//用户ID
					powerRecord.setProfitNum(rewardNum);//奖励数
					powerRecord.setSourceTypeId(sourceTypeId);//签到获取
					powerRecord.setCreateTime(new Date());//创建时间
					powerRecordDao.insert(powerRecord);//新增算力记录
				}else {//奖励dhc
					
				}
				jsonReturn.setCode(200);
			}else {
				jsonReturn.setCode(400);
				jsonReturn.setText("操作失败");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn findSignByUserId(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(userId!=null) {
			List<SignVo> list=signDetailDao.findSignByUserId(userId);
			if (list.size()>0) {
				jsonReturn.setCode(200);
				jsonReturn.setData(list);
			}else {
				jsonReturn.setCode(400);
				jsonReturn.setText("任务列表为空");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn updateSignDetail(SignVo signVo) {
		JsonReturn jsonReturn=new JsonReturn();
		if(signVo!=null) {
			int total = signDetailDao.updateSignDetail(signVo);
			if(total>0) {
				PowerRecord powerRecord=new PowerRecord();
				powerRecord.setUserId(signVo.getUserId());//用户ID
				powerRecord.setProfitNum(signVo.getRewardNum());//奖励数
				powerRecord.setSourceTypeId(3);//签到获取
				if(sf.format(nowDate).equals(sf.format(signVo.getSignDay()))) {//今天
					powerRecord.setCreateTime(nowDate);//创建时间
					TaskDetail taskDetail=new TaskDetail();
					taskDetail.setUserId(signVo.getUserId());//用户ID
					taskDetail.setTaskId(1);//签到任务
					taskDetail.setHasDo(1);//完成
					taskDetail.setProgressNum(1);//进度数
					taskDetailDao.updateTaskDetailHasDo(taskDetail);
				}else {//补签
					powerRecord.setCreateTime(signVo.getSignDay());//创建时间
				}
				powerRecordDao.insert(powerRecord);//新增算力记录
				jsonReturn.setCode(200);
				jsonReturn.setData(signVo);
			}else {
				jsonReturn.setCode(400);
				jsonReturn.setText("操作失败");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn updateTaskDetailProgress(Integer userId, Integer taskId,Integer sourceTypeId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(userId!=null && taskId !=null) {
			List<TaskVo> list=taskDetailDao.findTaskDetail(userId,taskId);//获取一条任务明细信息
			if(list.size()>0) {
				TaskVo taskVo=list.get(0);
				int taskTotal=taskVo.getTaskTotal();//任务总数
				int progressNum = taskVo.getProgressNum();//任务进度
				TaskDetail taskDetail=new TaskDetail();
				taskDetail.setTaskDetailId(taskVo.getTaskDetailId());//主键Id
				progressNum=progressNum+1;//加1
				if(progressNum<=taskTotal) {//未完成任务(任务进度小于或者等于任务总数)
					if(taskTotal==progressNum) {//完成任务
						taskDetail.setHasDo(1);
					}else {
						taskDetail.setHasDo(0);
					}
					taskDetail.setProgressNum(progressNum);//设置任务进度
					int total = taskDetailDao.updateByPrimaryKeySelective(taskDetail);//修改任务明细
					if(total>0 && taskDetail.getHasDo()==1) {//完成任务
						if(taskVo.getRewardType()==0) {//奖励算力
							PowerRecord powerRecord=new PowerRecord();
							powerRecord.setUserId(userId);//用户ID						
							if(taskVo.getTaskId()==2) {//邀请好友任务
								powerRecord.setProfitNum(taskVo.getRewardNum()/taskTotal);//奖励数=奖励总额/任务总数
							}else {
								powerRecord.setProfitNum(taskVo.getRewardNum());
							}
							powerRecord.setSourceTypeId(sourceTypeId);//签到获取
							powerRecord.setCreateTime(new Date());//创建时间
							powerRecordDao.insert(powerRecord);//新增算力记录
						}
					
					}
				}
				jsonReturn.setCode(200);
				jsonReturn.setData(taskVo);
			}
		}
		
		return jsonReturn;
	}

}
