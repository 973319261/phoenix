package com.koi.mapper;

import com.koi.po.IssueState;
import org.springframework.stereotype.Repository;

/**
 * IssueStateDAO继承基类
 */
@Repository
public interface IssueStateDAO extends MyBatisBaseDao<IssueState, Integer> {
}