package com.koi.mapper;

import com.koi.po.SignDetail;
import com.koi.po.WithdrawSettingDetail;
import com.koi.vo.WithdrawSettingVo;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * WithdrawSettingDetailDAO继承基类
 */
@Repository
public interface WithdrawSettingDetailDAO extends MyBatisBaseDao<WithdrawSettingDetail, Integer> {
	/**
	 * 批量新增钱包设置明细
	 * @param signDetails
	 * @return
	 */
	public int insertByBatch(List<WithdrawSettingDetail> withdrawSettingDetails);
	/**
	 * 查询钱包设置明细记录
	 * @param userId
	 * @return
	 */
	public List<WithdrawSettingVo> findWithdrawSettingByUserId(Integer userId);
}