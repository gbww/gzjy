package com.gzjy.contract.service.impl;
import java.io.Serializable;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gzjy.receive.service.ReceiveSampleService;


public class TaskComplete implements Serializable{
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);
	
	public boolean isComplete(DelegateExecution execution) {
		// TODO Auto-generated method stub
		int resultCount = (int)execution.getVariable("resultCount");
        int completeTask = (int)execution.getVariable("nrOfCompletedInstances");
        int taskCount = (int)execution.getVariable("nrOfInstances");
        logger.info("############### completeTask invoked ###############");
        logger.info("resultCount="+resultCount +" | completeTask="+completeTask+" | taskCount="+taskCount);
        //设置该变量防止该流程执行多次导致的流程方向出错问题
        execution.setVariable("result", 1);
        //有一個人反对
        if(completeTask > resultCount && taskCount!=completeTask) {
        	execution.setVariable("resultCount", 0);
        	execution.setVariable("result", 0);
        	logger.info("有一個人反对");
        	return true;
        }
        return false;
	}
}
