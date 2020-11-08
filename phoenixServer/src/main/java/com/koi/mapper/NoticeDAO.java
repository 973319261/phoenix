package com.koi.mapper;

import com.koi.po.Notice;
import org.springframework.stereotype.Repository;

/**
 * NoticeDAO继承基类
 */
@Repository
public interface NoticeDAO extends MyBatisBaseDao<Notice, Integer> {
}