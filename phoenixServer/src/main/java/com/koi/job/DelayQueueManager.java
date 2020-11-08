package com.koi.job;
/**
 * 延时队列管理器
 * @author ZLY
 *
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

import com.koi.po.Issue;

public class DelayQueueManager {
	//单列模式
	private static DelayQueueManager delayQueueManager=new DelayQueueManager();
	
	//构造私有管理器
	private DelayQueueManager() {}
	
	//对外返回实例
	public static DelayQueueManager getInstance() {
		return delayQueueManager;
	}
	
	//时间格式
	final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//延迟队列
	DelayQueue<DelayIssue> delayQueue=new DelayQueue<DelayIssue>();
	
	/**
	 * 将未开奖的期号信息加入队列
	 * @param delayIssue
	 * @return
	 */
	public boolean add(DelayIssue delayIssue) {
		System.err.println("期号："+delayIssue.getIssueName()+" 在" + sf.format(new Date())+" 加入    队列");
		return delayQueue.add(delayIssue);
	}
	
	/**
	 * 将已开奖的期号移除队列
	 * @param delayIssue
	 * @return
	 */
	public boolean remove(DelayIssue delayIssue) {
		boolean flag=false;
		for(DelayIssue inDelayIssue:delayQueue) {
			//判断期号id是否 是否相等，相等就移除
			if(inDelayIssue.getIssueId()==delayIssue.getIssueId()) {
				flag=delayQueue.remove(inDelayIssue);//移除
			}
		}
		System.err.println("订单："+delayIssue.getIssueName()+" 在" + sf.format(new Date())+" 移出   队列,结果： "+flag);
		return flag;
	}
	
	/**
	 * 获取队列中出队列的方法
	 * @return
	 * @throws InterruptedException
	 */
	public DelayIssue getTake() throws InterruptedException{
		return delayQueue.take();
	}
	
	/**
	 * 将期号数据转成队列期号数据
	 * @param issue
	 * @return
	 */
	public static DelayIssue issueToDelayIssue(Issue issue) {
		DelayIssue delayIssue=new DelayIssue();
		delayIssue.setIssueId(issue.getIssueId());//期号ID
		delayIssue.setIssueName(issue.getIssueName().trim());//期号名称
		delayIssue.setAbortTime(issue.getAbortTime());//截止时间
		return delayIssue;
	}
}
