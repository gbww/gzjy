package com.gzjy.checkitems.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.checkitems.model.CheckItemsCatalogMapping;

@Mapper
public interface CheckItemsCatalogMappingMapper {
	CheckItemsCatalogMapping selectByPrimaryKey(String id);
}
