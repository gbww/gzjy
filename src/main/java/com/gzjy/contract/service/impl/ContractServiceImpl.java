package com.gzjy.contract.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private ContractMapper contractMapper;
	
	@Autowired
	private ProcessEngine processEngine;
	
	
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
	
	
	/**
	 * 部署合同审批流程实例
	 * 部署完成之后将流程ID反填到合同表中
	 * @param approveUsers 合同审批的多个审批人
	 * @param updateContractUser 修改合同的人
	 * @return
	 */
	public void deploymentProcess(String contractId, ArrayList<String> approveUsers, String updateContractUser) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        repositoryService.createDeployment().addClasspathResource("processes/ContractProcess.bpmn").deploy();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approve_users", approveUsers);
        variables.put("resultCount", 0);
        variables.put("taskComplete", new TaskComplete());
        String processId = runtimeService.startProcessInstanceByKey("TestProcess",variables).getId();
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setActivity1(processId);
        contractMapper.updateByPrimaryKey(contract);
	}

}
