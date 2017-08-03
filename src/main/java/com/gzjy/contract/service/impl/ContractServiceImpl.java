package com.gzjy.contract.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private ContractMapper contractMapper;
	
	@Override
	public Contract getContractById(String id) {
		// TODO Auto-generated method stub
		return contractMapper.selectByPrimaryKey(id);
	}

}
