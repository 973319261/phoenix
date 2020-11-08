package com.koi.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koi.mapper.DividendDAO;
import com.koi.po.Dividend;
import com.koi.service.IAppDividendSerivce;
import com.koi.vo.JsonReturn;

@Service
public class AppDividendSerivceImpl implements IAppDividendSerivce {
	@Autowired DividendDAO dividendDao;
	
	@Override
	public JsonReturn insertDividend(Integer userId, Integer sourceTypeId) {
		JsonReturn jsonReturn=new JsonReturn();
		Dividend dividend;
		if(userId!=null && sourceTypeId !=null) {
			dividend = dividendDao.findDividenByUserId(userId);
			if(dividend==null) {
				dividend=new Dividend();
				dividend.setUserId(userId);//用户
				dividend.setSourceTypeId(sourceTypeId);//来源
				dividend.setDividendNum(1);//数量
				dividend.setCreateTime(new Date());//创建时间
				dividend.setAuditState(0);//未审核
				int total=dividendDao.insertSelective(dividend);
				if(total>0) {
					jsonReturn.setCode(200);
					jsonReturn.setText("恭喜您，合成分红");
					jsonReturn.setData(dividend);
				}else {
					jsonReturn.setCode(400);
					jsonReturn.setText("合成失败，请联系客服");
				}
			}else {
				jsonReturn.setCode(400);
				if(dividend.getAuditState()==0) {
					jsonReturn.setText("你已经合成分红，后台正在审核中...");
				}else {
					jsonReturn.setText("你已经存在分红，无法再合成");
				}
			}
		}else {
			jsonReturn.setCode(400);
			jsonReturn.setText("参数错误");
		}
		return jsonReturn;
	}
	
	
}
