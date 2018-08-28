package com.gzjy.contract.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.contract.model.Sample;

@Mapper
public interface SampleMapper {
    int deleteByPrimaryKey(String id);     

    int insert(Sample record);

    Sample selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sample record);   
    
    List<Sample> getListByContractId(@Param("contractId")String contractId);
    
    int deleteByContractId(@Param("contractId")String contractId);
    
    int deleteByIds(@Param("idList")List<String> idList);
  
}