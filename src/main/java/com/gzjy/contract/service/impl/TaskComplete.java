package com.gzjy.contract.service.impl;
import java.io.Serializable;
import org.activiti.engine.delegate.DelegateExecution;
public class TaskComplete implements Serializable{
	
	public boolean isComplete(DelegateExecution execution) {
		// TODO Auto-generated method stub
		int resultCount = (int)execution.getVariable("resultCount");
        int completeTask = (int)execution.getVariable("nrOfCompletedInstances");
        int taskCount = (int)execution.getVariable("nrOfInstances");
        System.out.println("############### completeTask invoked ###############");
        System.out.println("resultCount="+resultCount +" | completeTask="+completeTask+" | taskCount="+taskCount);
//      全部通過
        if (taskCount == resultCount) {
        	execution.setVariable("result", 1);
        	System.out.println("全部通過");
        	return true;
        }
//        有一個人反对
        if(completeTask > resultCount) {
        	execution.setVariable("resultCount", 0);
        	execution.setVariable("result", 0);
        	System.out.println("有一個人反对");
        	return true;
        }  
        return false;
	}
}
