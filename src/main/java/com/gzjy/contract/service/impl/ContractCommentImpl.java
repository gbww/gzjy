package com.gzjy.contract.service.impl;

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
		// TODO Auto-generated method stub
		return contractCommentMapper.selectByPrimaryKey(id);
	}

}
