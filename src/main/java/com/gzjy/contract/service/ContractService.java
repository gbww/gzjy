package com.gzjy.contract.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.contract.model.Contract;

public interface ContractService {
	
	Contract selectByPrimaryKey(String id);
	
	PageInfo<Contract> getPageList(Integer pageNum, Integer pageSize, String name);
	
	int insert(Contract record);
	
	int deleteByPrimaryKey(String id);
	
	String checkContractProtocolId(String ProtocolId);
	
	int updateByPrimaryKey(Contract record);
	
	void deploymentProcess(String contractId,ArrayList<String> approveUsers, String updateContractUser);
	
	List<Task> getTaskByUserId();
	
	public List<HistoricTaskInstance> getHistoryTaskByUserId();
	
	void completeApproveTask(String taskId, String contractId, String approve, String context);
	
	void completeUpdateTask(String taskId);
	
	void updateStatusByProcessId(Integer status, String processId);
	
	Task getUpdateTaskByProcessId(String processId);
	
	public void uploadFile(MultipartFile[] files,String contractId);
	
	public String getMaxIdByType(String type);
	
	public String generateContractId(String contractType, String foodType);
}
