package com.gzjy.samplemanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.samplemanager.model.SampleManager;
@Mapper
public interface SampleManagerMapper {
    int deleteByPrimaryKey(String sampleId);

    int insert(SampleManager record);

    int insertSelective(SampleManager record);

    SampleManager selectByPrimaryKey(String sampleId);

    int updateByPrimaryKeySelective(SampleManager record);

    int updateByPrimaryKey(SampleManager record);
    
    List<SampleManager> select(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}