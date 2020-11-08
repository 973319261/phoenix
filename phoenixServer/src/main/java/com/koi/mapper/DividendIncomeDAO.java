package com.koi.mapper;

import com.koi.po.DividendIncome;
import org.springframework.stereotype.Repository;

/**
 * DividendIncomeDAO继承基类
 */
@Repository
public interface DividendIncomeDAO extends MyBatisBaseDao<DividendIncome, Integer> {
}