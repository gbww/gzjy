package com.gzjy.checkitems.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.gzjy.checkitems.model.CheckItem;

@Mapper
public interface CheckItemMapper {
	CheckItem selectByPrimaryKey(String id);
	int insert(CheckItem checkItem);
	int importData(List<CheckItem> data);
	int updateByPrimaryKeySelective(CheckItem checkItem);
	int deleteByPrimaryKey(String id);
	int validateCheckItem(CheckItem checkItem);
	List<CheckItem> selectAll(@Param("name")String name, @Param("method")String method);
	List<String> selectDistinctIds();
	int deleteByIds(@Param("idList")String idList);
}
