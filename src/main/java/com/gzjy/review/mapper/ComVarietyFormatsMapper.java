package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComVarietyFormats;

public interface ComVarietyFormatsMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComVarietyFormats record);

    int insertSelective(ComVarietyFormats record);

    ComVarietyFormats selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComVarietyFormats record);

    int updateByPrimaryKey(ComVarietyFormats record);
}