package com.koi.mapper;

import com.koi.po.MoneyState;
import org.springframework.stereotype.Repository;

/**
 * MoneyStateDAO继承基类
 */
@Repository
public interface MoneyStateDAO extends MyBatisBaseDao<MoneyState, Integer> {
}