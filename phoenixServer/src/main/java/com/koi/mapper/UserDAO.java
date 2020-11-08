package com.koi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koi.po.User;
import com.koi.vo.JsonReturn;
import com.koi.vo.TeamVo;

/**
 * UserDAO继承基类
 */
@Repository
public interface UserDAO extends MyBatisBaseDao<User, Integer> {
	/**
	 * 通过手机号查询该手机号是否已被绑定用户
	 * @param phone
	 * @return
	 */
	public int findUserByPhone(String phone);
	/**
	 * 通过邀请码查询用户
	 * @param inviteCode
	 * @return
	 */
	public User findUserByInviteCode(String inviteCode);
	/**
	 * 查询徒弟集合
	 * @param userId
	 * @return
	 */
	public List<TeamVo> findChildsByUserId(@Param("userId")Integer userId);
	/**
	 * 更改收益阶段
	 * @param userId
	 * @param incomeStageId
	 * @return
	 */
	public JsonReturn updateIncomeStage(@Param("userId")Integer userId,@Param("incomeStageId") Integer incomeStageId);
	/**
	 * 查询师傅信息
	 * @param userId
	 * @return
	 */
	public User findFromUser(Integer userId);
	/**
	 * 通过用户ID和来源Id查询团队数量与数量算力数量
	 * @param userId
	 * @param sourceTypeId
	 * @return
	 */
	public Map<String, Integer> findTeamNumAndPowerNum(@Param("userId")Integer userId,@Param("sourceTypeId")Integer sourceTypeId);
	/**
	 * 获取邀请卷数
	 * @param userId
	 * @return
	 */
	public int findInviteNum(Integer userId);
	/**
	 * 查询排行榜信息
	 * @return
	 */
	public List<User> findRankingInfo();
}