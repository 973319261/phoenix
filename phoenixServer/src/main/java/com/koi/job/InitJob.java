package com.koi.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.koi.mapper.IssueDAO;
import com.koi.po.Issue;
import com.koi.service.ITimerTaskSerivce;
/**
 * 延迟任务初始化
 * @author koi
 *
 */
@Component
public class InitJob implements InitializingBean{
	@Autowired
	ITimerTaskSerivce timerTaskSerivce;
	@Autowired
	IssueDAO issueDao;
	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("=============InitJob 启动================");
		final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Thread checkerThead =new Thread(new Runnable() {
			@Override
			public void run() {
				//获取延迟队列管理器
				DelayQueueManager delayQueueManager=DelayQueueManager.getInstance();
				while(true) {//一直循环判断是否有任务
					try {
						DelayIssue delayIssue=delayQueueManager.getTake();//查询是否有需要开奖的期号
						if(delayIssue!=null) {//有
							// 处理期号开奖操作
							int total = timerTaskSerivce.lotteryIssue(delayIssue.getIssueId());
							if (total > 0) {
								System.err.println("期号：" + delayIssue.getIssueName() + "已开奖，当前时间：" + sf.format(new Date()));
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		//启动子线程
		checkerThead.start();
		//项目启动时去查询数据库中未开奖的期号
		List<Issue> issueList = issueDao.findIssueByStateId(1);///查询未开奖列表
		//获取延迟队列管理器
		DelayQueueManager delayQueueManager = DelayQueueManager.getInstance();
		if(issueList!=null) {
			System.err.println("有 ["+issueList.size()+"] 条未开奖的期号加入到队列");
			if(issueList.size()>0) {
				//将未开奖的期号加入到队列中
				DelayIssue delayIssue;
				for (Issue issue : issueList) {
					delayIssue=DelayQueueManager.issueToDelayIssue(issue);
					delayQueueManager.add(delayIssue);
				}
			}
		}
	}

}
