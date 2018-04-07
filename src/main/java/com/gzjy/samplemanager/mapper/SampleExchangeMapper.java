package com.gzjy.samplemanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.samplemanager.model.SampleExchange;
@Mapper
public interface SampleExchangeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SampleExchange record);

    int insertSelective(SampleExchange record);

    SampleExchange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SampleExchange record);

    int updateByPrimaryKeyWithBLOBs(SampleExchange record);

    int updateByPrimaryKey(SampleExchange record);
    
    List<SampleExchange> select(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}