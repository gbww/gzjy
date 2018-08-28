package com.gzjy.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.review.modle.ComScale;
@Mapper
public interface ComScaleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComScale record);

    int insertSelective(ComScale record);

    ComScale selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComScale record);

    int updateByPrimaryKey(ComScale record);

	List<ComScale> selectALL();
}