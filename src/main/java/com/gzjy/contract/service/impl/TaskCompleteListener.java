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
        logger.debug("##############Task Complete Listener##############");
        logger.debug("resultCount="+resultCount + "| completeTask="+completeTask+ "| taskCount="+taskCount);
        String processId = delegateTask.getExecution().getProcessInstanceId();
        if(completeTask+1 > resultCount) {
        	logger.debug("updateStatusByProcessId status=" + ContractStatus.UPDATING.getValue());
        	contractService.updateStatusByProcessId(ContractStatus.UPDATING.getValue(), processId);
        }
        if(taskCount == completeTask+1) {
        	logger.debug("updateStatusByProcessId status=" + ContractStatus.OVER.getValue() + "|processId="+processId);
        	try {
        		contractService.updateStatusByProcessId(ContractStatus.OVER.getValue(), processId);
        	}catch (Exception e) {
        		logger.debug(e+"");
			}
        }
	}
}
