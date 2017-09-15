package com.gzjy.contract.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.ContractStatus;

public class TaskCompleteListener implements TaskListener {

	
	@Autowired
	private ContractMapper contractMapper;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	public void notify(DelegateTask delegateTask) {
		
		int resultCount = (int)delegateTask.getExecution().getVariable("resultCount");
        int completeTask = (int)delegateTask.getExecution().getVariable("nrOfCompletedInstances");
        int taskCount = (int)delegateTask.getExecution().getVariable("nrOfInstances");
        System.out.println("Task Complete");
        System.out.println("resultCount="+resultCount + "| completeTask="+completeTask);
        String processId = delegateTask.getExecution().getProcessInstanceId();
        if(completeTask > resultCount) {        	
        	contractMapper.updateStatusByProcessId(ContractStatus.UPDATING.getValue(), processId);
        }
        if(taskCount == completeTask) {
        	contractMapper.updateStatusByProcessId(ContractStatus.OVER.getValue(), processId);
        }
	}
}
