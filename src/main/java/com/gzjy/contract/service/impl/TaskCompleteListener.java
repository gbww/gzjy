package com.gzjy.contract.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.contract.model.ContractStatus;
import com.gzjy.contract.service.ContractService;
import com.gzjy.receive.service.ReceiveSampleService;

@Service("taskCompleteListener")
public class TaskCompleteListener implements TaskListener {
	
	@Autowired
	private ContractService contractService;
	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {		
		int resultCount = (int)delegateTask.getExecution().getVariable("resultCount");
        int completeTask = (int)delegateTask.getExecution().getVariable("nrOfCompletedInstances");
        int taskCount = (int)delegateTask.getExecution().getVariable("nrOfInstances");
        System.out.println("##############Task Complete Listener##############");
        System.out.println("resultCount="+resultCount + "| completeTask="+completeTask+ "| taskCount="+taskCount);
        String processId = delegateTask.getExecution().getProcessInstanceId();
        if(completeTask+1 > resultCount) {
        	System.out.println("updateStatusByProcessId status=" + ContractStatus.UPDATING.getValue());
        	contractService.updateStatusByProcessId(ContractStatus.UPDATING.getValue(), processId);
        }
        if((taskCount == completeTask+1) && (resultCount ==taskCount)) {
        	System.out.println("(taskCount == completeTask+1) && (resultCount ==taskCount)");
        	System.out.println("updateStatusByProcessId status=" + ContractStatus.OVER.getValue() + "|processId="+processId);
        	try {
        		contractService.updateStatusByProcessId(ContractStatus.OVER.getValue(), processId);
        		delegateTask.getExecution().setVariable("result", 1);
        	}catch (Exception e) {
        		System.out.println(e+"");
			}
        }else {
        	System.out.println("Set result = 0");
        	delegateTask.getExecution().setVariable("result", 0);
        }
	}
}
