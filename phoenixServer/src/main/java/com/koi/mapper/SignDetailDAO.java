package com.koi.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koi.po.SignDetail;
import com.koi.vo.SignVo;

/**
 * SignDetailDAO继承基类
 */
@Repository
public interface SignDetailDAO extends MyBatisBaseDao<SignDetail, Integer> {
	/**
	 * 更新所有签到明细信息（重置签到列表）
	 * @return
	 */
	public int updateAllSignDetail();
	/**
	 * 批量新增签到信息明细
	 * @param signDetails
	 * @return
	 */
	public int insertByBatch(List<SignDetail> signDetails);
	/**
	 * 查询当前用户签到信息
	 * @param userId
	 * @return
	 */
	public List<SignVo> findSignByUserId(Integer userId);
	/**
	 * 修改签到状态（单个）
	 * @param signVo
	 * @return
	 */
	public int updateSignDetail(SignVo signVo);
}