package com.gzjy.contract.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.contract.model.Contract;

@Mapper
public interface ContractMapper {
    int deleteByPrimaryKey(String id);
    
    String checkContractProtocolId(String id);

    List<Contract> selectAll(@Param("detectProject")String detectProject);   

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);    
    
    void updateStatusByProcessId(@Param("status")Integer status, @Param("processId")String processId);
}