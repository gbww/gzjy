package com.gzjy.checkitems.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.checkitems.model.CheckItemsCatalogMapping;

@Mapper
public interface CheckItemsCatalogMappingMapper {
	CheckItemsCatalogMapping selectByPrimaryKey(String id);
	int insert(CheckItemsCatalogMapping record);
	int updateByPrimaryKeySelective(CheckItemsCatalogMapping record);
	int deleteByPrimaryKey(String id);
	List<HashMap<String, Object>> selectCheckItemsById(@Param("catalogId")String catalogId);
	int importData(List<CheckItemsCatalogMapping> data);
}
