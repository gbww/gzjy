package com.gzjy.client.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.client.model.GzClient;

@Mapper
public interface GzClientMapper {
    int deleteByPrimaryKey(Integer clientNum);

    int insert(GzClient record);

    int insertSelective(GzClient record);

    GzClient selectByPrimaryKey(Integer clientNum);

    int updateByPrimaryKeySelective(GzClient record);
    
    int updateByPrimaryKey(GzClient record);
    
    List<GzClient> selectAll(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}