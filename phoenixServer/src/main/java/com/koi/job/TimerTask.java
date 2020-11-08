package com.koi.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.koi.po.Issue;
import com.koi.service.ITimerTaskSerivce;

/**
 * 每天定时任务
 * @author koi  
 *
 */
@Component
public class TimerTask {
	@Autowired ITimerTaskSerivce timerTaskSerivce;
	/**
	 * 每一天的凌晨执行更新任务明细列表
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateAllTaskDetail() {
		int total = timerTaskSerivce.updateAllTaskDetail();
		if(total>0) {
			System.out.println("每天重置任务信息完毕");
		}else {
			System.out.println("每周重置任务信息失败");
		}
	}
	/**
	 * 每周星期一凌晨更新签到列表
	 */
	@Scheduled(cron = "0 0 0 ? * MON")
	public void updateAllSignDetail() {
		int total = timerTaskSerivce.updateAllSignDetail();
		if(total>0) {
			System.out.println("每周更新签到信息完毕");
		}else {
			System.out.println("每周更新签到信息失败");
		}
	
	}
	/**
	 * 每天早上8点更新期号信息
	 */
	@Scheduled(cron = "0 0 8 * * ?")
	public void insertIssue() {
		List<Issue> issueList = timerTaskSerivce.insertIssue();
		if(issueList!=null) {
			System.out.println("每天更新期号信息完毕");
			DelayQueueManager delayQueueManager=DelayQueueManager.getInstance();
			DelayIssue delayIssue;
			for (Issue issue:issueList) {
				// 将期号加入延迟队列
				delayIssue =DelayQueueManager.issueToDelayIssue(issue);
				delayQueueManager.add(delayIssue);
			}
			System.out.println("期号加入延迟队列完毕");
		}else {
			System.out.println("每天更新期号信息失败");
		}
	}
	
}
