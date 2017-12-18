package com.gzjy.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.contract.model.Sample;

@Mapper
public interface SampleMapper {
    int deleteByPrimaryKey(String id);     

    int insert(Sample record);

    Sample selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sample record);    
  
}