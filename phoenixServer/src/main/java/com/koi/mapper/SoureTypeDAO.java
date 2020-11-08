package com.koi.mapper;

import com.koi.po.SoureType;
import org.springframework.stereotype.Repository;

/**
 * SoureTypeDAO继承基类
 */
@Repository
public interface SoureTypeDAO extends MyBatisBaseDao<SoureType, Integer> {
}