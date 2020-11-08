package com.koi.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koi.po.WithdrawSetting;

/**
 * WithdrawSettingDAO继承基类
 */
@Repository
public interface WithdrawSettingDAO extends MyBatisBaseDao<WithdrawSetting, Integer> {
	/**
	 * 查询所有钱包设置
	 * @return
	 */
	List<WithdrawSetting> findAll();
}