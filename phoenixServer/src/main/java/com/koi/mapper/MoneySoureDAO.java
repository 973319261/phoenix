package com.koi.mapper;

import com.koi.po.MoneySoure;
import org.springframework.stereotype.Repository;

/**
 * MoneySoureDAO继承基类
 */
@Repository
public interface MoneySoureDAO extends MyBatisBaseDao<MoneySoure, Integer> {
}