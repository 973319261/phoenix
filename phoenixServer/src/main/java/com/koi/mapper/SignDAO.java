package com.koi.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koi.po.Sign;

/**
 * SignDAO继承基类
 */
@Repository
public interface SignDAO extends MyBatisBaseDao<Sign, Integer> {
	/**
	 * 查询所有签到信息
	 * @return
	 */
	List<Sign> findAll();
	/**
	 * 更新所有签到列表
	 * @return
	 */
	public int updateAllSign(List<Sign> signList);
}