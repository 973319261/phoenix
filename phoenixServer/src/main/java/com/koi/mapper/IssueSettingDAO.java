package com.koi.mapper;

import com.koi.po.IssueSetting;
import org.springframework.stereotype.Repository;

/**
 * IssueSettingDAO继承基类
 */
@Repository
public interface IssueSettingDAO extends MyBatisBaseDao<IssueSetting, Integer> {
}