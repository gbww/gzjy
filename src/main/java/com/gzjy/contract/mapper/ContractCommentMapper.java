package com.gzjy.contract.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gzjy.contract.model.ContractComment;

@Mapper
public interface ContractCommentMapper {
	ContractComment selectByPrimaryKey(String id);
	int deleteByPrimaryKey(String id);
	int insertSelective(ContractComment recoed);
}
