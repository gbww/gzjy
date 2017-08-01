package com.gzjy.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.contract.model.Contract;

@Mapper
public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKeyWithBLOBs(Contract record);

    int updateByPrimaryKey(Contract record);
}