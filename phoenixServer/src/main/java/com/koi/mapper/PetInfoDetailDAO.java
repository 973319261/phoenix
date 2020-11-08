package com.koi.mapper;

import com.koi.po.PetInfoDetail;
import com.koi.po.TaskDetail;
import com.koi.vo.PetInfoVo;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * PetInfoDetailDAO继承基类
 */
@Repository
public interface PetInfoDetailDAO extends MyBatisBaseDao<PetInfoDetail, Integer> {
	/**
	 * 批量新增宠物信息明细
	 * @param petInfoDetailList
	 * @return
	 */
	public int insertByBatch(List<PetInfoDetail> petInfoDetailList);
	/**
	 * 通过用户ID查询宠物列表信息
	 * @param userId
	 * @return
	 */
	public List<PetInfoVo> findPetInfoByUserId(Integer userId);
	/**
	 * 通过用户Id与宠物id修改宠物信息
	 * @param petInfoDetail
	 * @return
	 */
	public int updatePetInfo(PetInfoDetail petInfoDetail);
}