package com.gzjy.checkitems.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.checkitems.model.CheckItem;

@Mapper
public interface CheckItemMapper {
	CheckItem selectByPrimaryKey(String id);
	int insert(CheckItem checkItem);
	int updateByPrimaryKeySelective(CheckItem checkItem);
	int deleteByPrimaryKey(String id);
}
