package com.gzjy.contract.service;

import java.util.ArrayList;

import com.gzjy.contract.model.Contract;

public interface ContractService {
	
	Contract selectByPrimaryKey(String id);
	
	int insert(Contract record);
	
	int deleteByPrimaryKey(String id);
	
	void deploymentProcess(String contractId,ArrayList<String> approveUsers, String updateContractUser);
}
