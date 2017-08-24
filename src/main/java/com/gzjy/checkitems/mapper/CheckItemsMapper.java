package com.gzjy.checkitems.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gzjy.checkitems.model.CheckItems;

@Mapper
public interface CheckItemsMapper {
	CheckItems selectByPrimaryKey(String id);
}
