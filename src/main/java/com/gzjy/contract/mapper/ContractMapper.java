package com.gzjy.contract.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.contract.model.Contract;

@Mapper
public interface ContractMapper {
    int deleteByPrimaryKey(String id);
    
    String checkContractProtocolId(String id);

    List<Contract> selectAll(@Param("detectProject")String detectProject, @Param("type")String type,@Param("status")List<Integer> status);   

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);
    
    String getMaxIdByType(@Param("type") String type);
    
    int updateByPrimaryKeySelective(Contract record);    
    
    void updateStatusByProcessId(@Param("status")Integer status, @Param("processId")String processId);
    
    String getAppendixById(@Param("id")String id);
}