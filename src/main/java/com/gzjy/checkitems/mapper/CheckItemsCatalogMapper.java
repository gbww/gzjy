package com.gzjy.checkitems.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gzjy.checkitems.model.CheckItemsCatalog;

@Mapper
public interface CheckItemsCatalogMapper {
	CheckItemsCatalog selectByPrimaryKey(String id);
}
