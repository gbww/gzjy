package com.gzjy.contract.service;

import com.gzjy.contract.model.Contract;

public interface ContractService {
	
	Contract getContractById(String id);
	
	int insert(Contract record);
	
	int deleteByPrimaryKey(String id);
}
