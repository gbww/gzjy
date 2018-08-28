package com.gzjy.contract.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.contract.model.ContractComment;

@Mapper
public interface ContractCommentMapper {
	ContractComment selectByPrimaryKey(String id);
	int deleteByPrimaryKey(String id);
	int insertSelective(ContractComment record);
	int insert(ContractComment record);
	List<ContractComment> selectLatestComment(String contract_id);	
}
