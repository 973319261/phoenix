package com.koi.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koi.mapper.MoneyRecordDAO;
import com.koi.mapper.WithdrawSettingDetailDAO;
import com.koi.po.MoneyRecord;
import com.koi.service.IAppWalletService;
import com.koi.vo.JsonReturn;
import com.koi.vo.MoneyRecordVo;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;
import com.koi.vo.WithdrawSettingVo;
@Service
public class AppWalletServiceImpl implements IAppWalletService {
	@Autowired MoneyRecordDAO moneyRecordDao;
	@Autowired WithdrawSettingDetailDAO withdrawSettingDetailDAO;
	private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public Pagination<MoneyRecordVo> findMoneyRecord(Integer userId,PaginationPage page) {
		int totalRows = moneyRecordDao.findMoneyRecordCount(userId);
		List<MoneyRecordVo> list=moneyRecordDao.findMoneyRecord(userId,page.getStartIndex(), page.getPageSize());//获取零钱记录
		// 分页数据
		Pagination<MoneyRecordVo> pagination = new Pagination<MoneyRecordVo>();
		pagination.setCurrentPage(page.getCurrentPage());// 当前页
		pagination.setPageSize(page.getPageSize());// 每页大小
		pagination.setTotalRows(totalRows);// 总条数
		pagination.setData(list);// 本页数据
		pagination.setSuccess(true);// 成功
		return pagination;
	}
	@Override
	public JsonReturn findWithdrawSetting(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		if(userId!=null) {
			BigDecimal balance = moneyRecordDao.findBalance(userId);//总金额
			List<WithdrawSettingVo> list=withdrawSettingDetailDAO.findWithdrawSettingByUserId(userId);
			JsonObject jsonObject=new JsonObject();
			jsonObject.add("balance", gson.toJsonTree(balance==null?0.0:balance));
			jsonObject.add("withdrawSettingVoList", gson.toJsonTree(list));
			jsonReturn.setCode(200);
			jsonReturn.setData(jsonObject);
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn addMoneyRecord(Integer userId, Integer moneySourceId, Integer moneyStateId, BigDecimal money,
			Integer moneyType) {
		JsonReturn jsonReturn=new JsonReturn();
		MoneyRecord moneyRecord=new MoneyRecord();
		moneyRecord.setUserId(userId);
		moneyRecord.setMoneySoureId(moneySourceId);
		moneyRecord.setMoneyStateId(moneyStateId);
		moneyRecord.setMoney(money);
		moneyRecord.setCreateTime(new Date());
		moneyRecord.setMoneyType(moneyType);
		int total=moneyRecordDao.insert(moneyRecord);
		if (total>0) {
			jsonReturn.setCode(200);
		}else {
			jsonReturn.setCode(400);
		}
		return jsonReturn;
	}

}
