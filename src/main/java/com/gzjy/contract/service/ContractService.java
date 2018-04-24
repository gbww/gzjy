package com.gzjy.contract.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.model.ContractSample;
import com.gzjy.contract.model.ContractTask;

public interface ContractService {
	
	Contract selectByPrimaryKey(String id);
	
	PageInfo<Contract> getPageList(Integer pageNum, Integer pageSize, String name,String type, List<Integer> status);

	int insert(Contract record);
	
	int deleteByPrimaryKey(String id);
	
	String checkContractProtocolId(String ProtocolId);
	
	ContractSample getContractDetail(String contractId);
	
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
	
	public OutputStream getAppendix(OutputStream out, String contractId, String filename);
	
	public void deleteAppendix(String contractId, String filename);
	
	public String getAppendixById(String id);	
	
	public ArrayList<ContractTask> getAllContractTaskByUserName(String processId, String isHandle);
	
}
