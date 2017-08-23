package com.gzjy.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.contract.mapper.ContractCommentMapper;
import com.gzjy.contract.model.ContractComment;
import com.gzjy.contract.service.ContractCommentService;

@Service
public class ContractCommentImpl implements ContractCommentService {

	@Autowired
	private ContractCommentMapper contractCommentMapper;
	
	
	public ContractComment selectByPrimaryKey(String id) {
		return contractCommentMapper.selectByPrimaryKey(id);
	}

	public List<ContractComment> selectLatestComment(String contract_id){
		return contractCommentMapper.selectLatestComment(contract_id);
	}

	public int insertSelective(ContractComment record) {
		// TODO Auto-generated method stub
		return contractCommentMapper.insertSelective(record);
	}
	
	public int insert(ContractComment record) {
		// TODO Auto-generated method stub
		return contractCommentMapper.insert(record);
	}
}
