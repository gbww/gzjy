package com.gzjy.contract.service;

import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.task.Task;

import com.github.pagehelper.PageInfo;
import com.gzjy.checkitems.model.CheckItem;
import com.gzjy.contract.model.Contract;

public interface ContractService {
	
	Contract selectByPrimaryKey(String id);
	
	PageInfo<Contract> getPageList(Integer pageNum, Integer pageSize, String name);
	
	int insert(Contract record);
	
	int deleteByPrimaryKey(String id);
	
	int updateByPrimaryKey(Contract record);
	
	void deploymentProcess(String contractId,ArrayList<String> approveUsers, String updateContractUser);
	
	List<Task> getTaskByUserId(String taskName, String userId);
	
	void completeApproveTask(String taskId, String contractId, String approve, String context);
	
	void completeUpdateTask(String taskId);
	
	void updateStatusByProcessId(Integer status, String processId);
}
