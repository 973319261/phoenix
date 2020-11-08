package com.koi.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koi.po.PetInfo;

/**
 * PetInfoDAO继承基类
 */
@Repository
public interface PetInfoDAO extends MyBatisBaseDao<PetInfo, Integer> {
	/**
	 * 查询所有宠物信息
	 * @return
	 */
	List<PetInfo> findAll();
}