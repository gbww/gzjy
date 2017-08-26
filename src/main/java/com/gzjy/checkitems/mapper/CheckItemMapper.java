package com.gzjy.checkitems.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.checkitems.model.CheckItem;

@Mapper
public interface CheckItemMapper {
	CheckItem selectByPrimaryKey(String id);
}
