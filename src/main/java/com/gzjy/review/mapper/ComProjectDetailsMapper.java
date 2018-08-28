package com.gzjy.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.review.modle.ComProjectDetails;
@Mapper
public interface ComProjectDetailsMapper {
	int deleteByPrimaryKey(String id);

	int insert(ComProjectDetails record);

	int insertSelective(ComProjectDetails record);

	ComProjectDetails selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ComProjectDetails record);

	int updateByPrimaryKey(ComProjectDetails record);

	List<ComProjectDetails> selectByComType(String comType);
}