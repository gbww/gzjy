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
	public Contract selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return contractMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(Contract record) {
		// TODO Auto-generated method stub
		return contractMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return contractMapper.deleteByPrimaryKey(id);
	}

}
