package com.gzjy.contract.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.contract.model.Contract;

@Mapper
public interface ContractMapper {
    int deleteByPrimaryKey(String id);
    
    String checkContractId(String id);

    List<Contract> selectAll(@Param("sampleName")String sampleName);
    
    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKeyWithBLOBs(Contract record);

    int updateByPrimaryKey(Contract record);
    
    void updateStatusByProcessId(@Param("status")Integer status, @Param("processId")String processId);
}