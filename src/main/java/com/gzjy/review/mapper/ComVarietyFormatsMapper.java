package com.gzjy.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.PageInfo;
import com.gzjy.review.modle.ComVarietyFormats;
@Mapper
public interface ComVarietyFormatsMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComVarietyFormats record);

    int insertSelective(ComVarietyFormats record);

    ComVarietyFormats selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComVarietyFormats record);

    int updateByPrimaryKey(ComVarietyFormats record);

    List<ComVarietyFormats> selectALL();
}