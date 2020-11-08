package com.koi.mapper;

import com.koi.po.IncomeDetail;
import org.springframework.stereotype.Repository;

/**
 * IncomeDetailDAO继承基类
 */
@Repository
public interface IncomeDetailDAO extends MyBatisBaseDao<IncomeDetail, Integer> {
}