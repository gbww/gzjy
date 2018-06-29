package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComScale;

public interface ComScaleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComScale record);

    int insertSelective(ComScale record);

    ComScale selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComScale record);

    int updateByPrimaryKey(ComScale record);
}