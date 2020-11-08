package com.koi.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koi.mapper.IncomeRecordDAO;
import com.koi.mapper.IncomeStageDAO;
import com.koi.mapper.ProgressDAO;
import com.koi.mapper.UserDAO;
import com.koi.po.IncomeRecord;
import com.koi.po.IncomeStage;
import com.koi.po.Progress;
import com.koi.po.User;
import com.koi.service.IAppIncomeService;
import com.koi.util.DateUtils;
import com.koi.vo.JsonReturn;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;

@Service
public class AppIncomeServiceImpl implements IAppIncomeService{
	@Autowired IncomeStageDAO incomeStageDAO ;
	@Autowired ProgressDAO progressDao;
	@Autowired IncomeRecordDAO incomeRecordDao	;
	@Autowired UserDAO userDao;
	private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	

	@Override
	public JsonReturn findIncomeByUserId(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(userId!=null) {
			IncomeStage incomeStage = incomeStageDAO.findIncomeStageById(userId);//查询收益阶段
			Progress progress=progressDao.findProgressById(userId);//查询分红进度
			IncomeRecord allIncome = incomeRecordDao.findInComeRecordByDay(userId,null);//查询总收益
			IncomeRecord todayIncome = incomeRecordDao.findInComeRecordByDay(userId, DateUtils.getToday());//查询今天收益
			IncomeRecord yesterdayIncome = incomeRecordDao.findInComeRecordByDay(userId, DateUtils.getYestoday());//查询昨天收益
			//将两个需要返回的对象放入一个JSON对象中
			JsonObject jsonObject=new JsonObject();
			jsonObject.add("incomeStage", gson.toJsonTree(incomeStage==null?"":incomeStage));
			jsonObject.add("progress", gson.toJsonTree(progress==null?"":progress));
			jsonObject.add("allIncome",gson.toJsonTree(allIncome==null?"":allIncome));
			jsonObject.add("todayIncome", gson.toJsonTree(todayIncome==null?"":todayIncome));
			jsonObject.add("yesterdayIncome", gson.toJsonTree(yesterdayIncome==null?"":yesterdayIncome));
			jsonReturn.setCode(200);
			jsonReturn.setData(jsonObject);
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数错误");
		}
		return jsonReturn;
	}
	
	@Override
	public Pagination<IncomeRecord> findIncomeByDate(Integer userId, String date, PaginationPage page) {
		// TODO Auto-generated method stub
		// 获取收益记录总数
		int totalRows = incomeRecordDao.findIncomeRecordCountByDate(userId, date);
		List<IncomeRecord> list=incomeRecordDao.findIncomeRecordByDate(userId, date, page.getStartIndex(), page.getPageSize());//获取收益记录
		// 分页数据
		Pagination<IncomeRecord> pagination = new Pagination<IncomeRecord>();
		pagination.setCurrentPage(page.getCurrentPage());// 当前页
		pagination.setPageSize(page.getPageSize());// 每页大小
		pagination.setTotalRows(totalRows);// 总条数
		pagination.setData(list);// 本页数据
		pagination.setSuccess(true);// 成功
		return pagination;
	}

	@Override
	public JsonReturn updateIncomeStage(Integer userId, Integer incomeStageId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(userId!=null && incomeStageId !=null) {
			User user=new User();
			user.setUserId(userId);
			user.setIncomeStageId(incomeStageId);
			int total = userDao.updateByPrimaryKeySelective(user);
			if(total>0) {
				IncomeStage incomeStage = incomeStageDAO.findIncomeStageById(userId);//查询更新后的所在阶段
				jsonReturn.setCode(200);
				jsonReturn.setData(incomeStage);
			}else {
				jsonReturn.setCode(400);
				jsonReturn.setText("阶段更新失败");
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}


}
