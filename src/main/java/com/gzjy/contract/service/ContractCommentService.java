package com.gzjy.contract.service;

import java.util.List;

import com.gzjy.contract.model.ContractComment;

public interface ContractCommentService {
	ContractComment selectByPrimaryKey(String id);
	List<ContractComment> selectLatestComment(String contract_id);
	int insertSelective(ContractComment record);
	int insert(ContractComment record);
}
