package com.gzjy.contract.service.impl;
import java.io.Serializable;
import org.activiti.engine.delegate.DelegateExecution;
public class TaskComplete implements Serializable{
	
	public boolean isComplete(DelegateExecution execution) {
		// TODO Auto-generated method stub
		System.out.println("***********************");
		int resultCount = (int)execution.getVariable("resultCount");
        int completeTask = (int)execution.getVariable("nrOfCompletedInstances");
        int taskCount = (int)execution.getVariable("nrOfInstances");
        System.out.println("############### completeTask invoked ###############");
//      全部通過
        if (taskCount == resultCount) {
        	execution.setVariable("result", 1);
        	return true;
        }
//        有一個人反对
        if(completeTask > resultCount) {
        	execution.setVariable("result", 0);
        	return true;
        }  
        return false;
	}
}
