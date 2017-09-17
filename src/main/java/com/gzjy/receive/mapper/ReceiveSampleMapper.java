package com.gzjy.receive.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.receive.model.ReceiveSample;
@Mapper
public interface ReceiveSampleMapper {
    int deleteByPrimaryKey(String receiveSampleId);

    int insert(ReceiveSample record);

    int insertSelective(ReceiveSample record);

    ReceiveSample selectByPrimaryKey(String receiveSampleId);

    int updateByPrimaryKeySelective(ReceiveSample record);

    int updateByPrimaryKey(ReceiveSample record);
    List<ReceiveSample>selectTest(@Param("filters") Map<String, Object> filter);
    
    List<ReceiveSample> selectAll(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
    
    
}