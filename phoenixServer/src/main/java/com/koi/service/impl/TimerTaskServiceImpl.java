package com.koi.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koi.job.DelayIssue;
import com.koi.job.DelayQueueManager;
import com.koi.mapper.BetDAO;
import com.koi.mapper.IssueDAO;
import com.koi.mapper.IssueSettingDAO;
import com.koi.mapper.MoneyRecordDAO;
import com.koi.mapper.RedeemCodeDAO;
import com.koi.mapper.SignDAO;
import com.koi.mapper.SignDetailDAO;
import com.koi.mapper.TaskDetailDAO;
import com.koi.po.Issue;
import com.koi.po.IssueSetting;
import com.koi.po.MoneyRecord;
import com.koi.po.Sign;
import com.koi.service.ITimerTaskSerivce;

@Service
public class TimerTaskServiceImpl implements ITimerTaskSerivce {
	@Autowired TaskDetailDAO taskDetailDAO;
	@Autowired SignDAO signDao;
	@Autowired SignDetailDAO signDetailDao;
	@Autowired IssueDAO issueDao;
	@Autowired IssueSettingDAO issueSettingDao;
	@Autowired BetDAO betDao;
	@Autowired RedeemCodeDAO redeemCodeDao;
	@Autowired MoneyRecordDAO moneyRecordDao;
	@Override
	public int updateAllTaskDetail() {
		int total = taskDetailDAO.updateAllTaskDetail();
		return total;
	}

	@Override
	public int updateAllSignDetail() {
		Calendar cal;//获取当前日期（星期一）
		List<Sign> list=signDao.findAll();
		for(int i=0;i<list.size();i++) {
		 cal = Calendar.getInstance();
       	 cal.add(Calendar.DATE,i);//循环获取一周时间
       	 list.get(i).setSignDay(cal.getTime());//设置日期待签到列表
		}
		int signTotal=signDao.updateAllSign(list);
		if(signTotal>0) {
			int signDetailTotal = signDetailDao.updateAllSignDetail();//更新签到明细列表
		}
		return signTotal;
	}

	@Override
	public List<Issue> insertIssue() {
		IssueSetting issueSetting = issueSettingDao.selectByPrimaryKey(1);//查询期号设置表
		if(issueSetting!=null) {
			List<Issue> issuesList=new ArrayList<>();
			Calendar cal;//获取当前日期
			SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
			String date=sf.format(new Date());
			for (int i = 1; i <=issueSetting.getIssueNum(); i++) {//循环一天期数
				Issue issue=new Issue();
				issue.setIssueStateId(1);//可参与
				issue.setPrizeName(issueSetting.getPrizeName().trim());//奖品名称
				issue.setPrizeMoney(issueSetting.getPrizeMoney());//奖励金额
				issue.setPrizeImg(issueSetting.getPrizeImg().trim());//奖品图片
				issue.setYetBetNum(issueSetting.getInitBetNum());//已投注数
				issue.setAllBetNum(issueSetting.getMaxBetNum());//总投注数
				String issueName=date+"-"+ i;
				issue.setIssueName(issueName);
				cal = Calendar.getInstance();
		        cal.set(Calendar.HOUR_OF_DAY, issueSetting.getStartHour()+issueSetting.getIntervalHour()*i);//循环设置小时(隔getIntervalHour()个小时开一次)
		        cal.set(Calendar.MINUTE, issueSetting.getStartMinute());//循环设置分
		        cal.set(Calendar.SECOND, issueSetting.getStartSecond());//循环设置秒
				issue.setAbortTime(cal.getTime());//截止时间
				issuesList.add(issue);//添加到集合
			}
			int total = issueDao.insertByBatch(issuesList);
			return issuesList;
		}else {
			return null;
		}
	}

	@Override
	public int lotteryIssue(Integer issueId) {
		Issue issue=issueDao.selectByPrimaryKey(issueId);
		Random random=new Random();
		int max=999999999;
		int min=100000000;
		int blockNumber=random.nextInt(max) % (max-min) + min;//获取[min,max]随机数（区块号）
		int luckNumber = 0;
		if(issue.getYetBetNum()!=0) {
			luckNumber=(int) (blockNumber % issue.getYetBetNum()) + 1;//（幸运号码=开奖时间戳%（取余）抽奖总人数）+1
			issue.setBlockNumber(Long.valueOf(blockNumber));//设置区块号
			issue.setLuckNumber(luckNumber);//设置幸运号码
		}
		issue.setIssueStateId(2);//已开奖
		issue.setLotteryTime(new Date(blockNumber));//设置开奖时间
		int total=issueDao.updateByPrimaryKeySelective(issue);//修改
		if(total>0) {
			Integer userId = betDao.findUserId(issueId, luckNumber);//通过幸运号码和期号ID查询中奖用户ID
			if(userId != null) {
				int i = betDao.updateWinning(issueId, userId);//更改是否中奖状态
				MoneyRecord moneyRecord=new MoneyRecord();//零钱明细
				moneyRecord.setUserId(userId);//用户ID
				moneyRecord.setMoneySoureId(2);//抽奖获取
				moneyRecord.setMoneyStateId(3);//入账成功
				moneyRecord.setMoney(issue.getPrizeMoney());//奖励金额
				moneyRecord.setCreateTime(new Date());//创建时间
				moneyRecord.setMoneyType(0);//入账
				moneyRecordDao.insertSelective(moneyRecord);//添加零钱明细
			}
			//从延迟列队中移除期号
			DelayIssue delayIssue=DelayQueueManager.issueToDelayIssue(issue);
			DelayQueueManager.getInstance().remove(delayIssue);
		}
		return total;
	}
}
