package com.gzjy.unit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.unit.model.GzUnit;
@Mapper
public interface GzUnitMapper {
    int deleteByPrimaryKey(String unitName);

    int insert(GzUnit record);

    int insertSelective(GzUnit record);

    GzUnit selectByPrimaryKey(String unitName);

    int updateByPrimaryKeySelective(GzUnit record);

    int updateByPrimaryKey(GzUnit record);
    List<GzUnit> selectAll(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}