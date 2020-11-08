package com.koi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koi.mapper.BetDAO;
import com.koi.mapper.IssueDAO;
import com.koi.mapper.RedeemCodeDAO;
import com.koi.mapper.UserDAO;
import com.koi.mapper.VideoRecordDAO;
import com.koi.po.Bet;
import com.koi.po.Issue;
import com.koi.po.RedeemCode;
import com.koi.po.User;
import com.koi.po.VideoRecord;
import com.koi.service.IAppIssueService;
import com.koi.vo.BetVo;
import com.koi.vo.IssueVo;
import com.koi.vo.JsonReturn;
import com.koi.vo.Pagination;
import com.koi.vo.PaginationPage;

@Service
public class AppIssueServiceImpl implements IAppIssueService {
	@Autowired IssueDAO issueDao;
	@Autowired BetDAO betDao;
	@Autowired RedeemCodeDAO redeemCodeDao;
	@Autowired UserDAO userDao;
	@Autowired VideoRecordDAO videoRecordDao;
	private SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public JsonReturn findIssueByDay() {
		JsonReturn jsonReturn=new JsonReturn();
		Date date=new Date();
		List<IssueVo> list =issueDao.findIssueByDay(sf.format(date));
		if(list.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setData(list);
		}else {
			jsonReturn.setCode(400);
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn findBetByIssueId(Integer issueId, PaginationPage page) {
		JsonReturn jsonReturn=new JsonReturn();
		IssueVo issueVo=issueDao.findIssueById(issueId);//查询期号信息
		BetVo luck=null;//幸运儿信息
		// 获取投注记录总数
		int totalRows = betDao.findBetCountByIssueId(issueId);
		List<BetVo> list=betDao.findBetByIssueId(issueId, page.getStartIndex(), page.getPageSize());//获取投注记录
		if(issueVo.getIssueStateId()==2) {//已开奖
			luck = betDao.findLuck(issueId);//查询幸运儿
		}
		// 分页数据
		Pagination<BetVo> pagination = new Pagination<BetVo>();
		pagination.setCurrentPage(page.getCurrentPage());// 当前页
		pagination.setPageSize(page.getPageSize());// 每页大小
		pagination.setTotalRows(totalRows);// 总条数
		pagination.setData(list);// 本页数据
		pagination.setSuccess(true);// 成功
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("issueVo", gson.toJsonTree(issueVo));
		jsonObject.add("luck", gson.toJsonTree(luck==null?"":luck));
		jsonObject.add("pagination", gson.toJsonTree(pagination));
		jsonReturn.setCode(200);
		jsonReturn.setData(jsonObject);
		return jsonReturn;
	}
	@Override
	public JsonReturn findDrawCondition(Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		int inviteNum = userDao.findInviteNum(userId);//查询邀请卷数量
		VideoRecord videoRecord=videoRecordDao.findVideoRecordByUserId(userId);//查询视频观看记录
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("inviteNum", gson.toJsonTree(inviteNum));
		jsonObject.add("videoRecord", gson.toJsonTree(videoRecord));
		jsonReturn.setCode(200);
		jsonReturn.setData(jsonObject);
		return jsonReturn;
	}
	@Override
	public JsonReturn insertBet(Bet bet,Integer inviteNum) {
		JsonReturn jsonReturn=new JsonReturn();
		int issueId=bet.getIssueId();//期号ID
		IssueVo issueVo=issueDao.findIssueById(issueId);//查询期号信息
		if(issueVo.getIssueStateId()!=2) {//未开奖
			bet.setBetTime(new Date());
			bet.setIsWinning(0);//待开奖
		    int total = betDao.insert(bet);
		    if(total>0) {
				int yetBetNum = issueVo.getYetBetNum();//已参与数
		    	List<RedeemCode> list=new ArrayList<RedeemCode>();
		    	for (int i = 1; i <= bet.getBetNum(); i++) {
					RedeemCode redeemCode=new RedeemCode();
					redeemCode.setBetId(bet.getBetId());//下注ID
					redeemCode.setRedeemCode(yetBetNum + i);//设置兑换码
					list.add(redeemCode);
				}
		    	int redeemCodeTotal = redeemCodeDao.insertByBatch(list);//批量新增兑换码
		    	if(redeemCodeTotal>0) {
		    		Issue issue=new Issue();
		    		issue.setIssueId(bet.getIssueId());//期号ID
			    	issue.setYetBetNum(yetBetNum+redeemCodeTotal);//已参与数
			    	issueDao.updateByPrimaryKeySelective(issue);//修改参与数
			    	User user=new User();
			    	user.setUserId(bet.getUserId());
			    	user.setInviteTicketNum(inviteNum-redeemCodeTotal);//修改数量(当前邀请卷-下注数)
			    	userDao.updateByPrimaryKeySelective(user);//修改邀请卷数量
			    	jsonReturn.setCode(200);
		    	}else {
					jsonReturn.setCode(400);
					jsonReturn.setText("下注失败，请稍后再试");
				}
		    }else {
				jsonReturn.setCode(400);
				jsonReturn.setText("下注失败，请稍后再试");
			}
		}else {
    		jsonReturn.setCode(400);
    		jsonReturn.setText("本期已开奖，无法完成下注");
    	}
		return jsonReturn;
	}
	@Override
	public JsonReturn findRedeemCode(Integer issueId, Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<String> list = redeemCodeDao.findRedeemCode(issueId, userId);//查询兑换码
		if(list.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setData(list);
		}else {
			jsonReturn.setCode(400);
		}
		return jsonReturn;
	}
	@Override
	public Pagination<IssueVo> findParticipateRecord(Integer userId, PaginationPage page) {
		// 获取投注记录总数
		int totalRows = issueDao.findParticipateCount(userId);//查询参与条数
		List<IssueVo> list=issueDao.findParticipateRecord(userId, page.getStartIndex(), page.getPageSize());//获取投注记录
		// 分页数据
		Pagination<IssueVo> pagination = new Pagination<IssueVo>();
		pagination.setCurrentPage(page.getCurrentPage());// 当前页
		pagination.setPageSize(page.getPageSize());// 每页大小
		pagination.setTotalRows(totalRows);// 总条数
		pagination.setData(list);// 本页数据
		pagination.setSuccess(true);// 成功
		return pagination;
	}

}
