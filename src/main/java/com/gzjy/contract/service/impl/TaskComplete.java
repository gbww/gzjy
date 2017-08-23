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
        //设置该变量防止该流程执行多次导致的流程方向出错问题
        execution.setVariable("result", 1);
        //有一個人反对
        if(completeTask > resultCount) {
        	execution.setVariable("resultCount", 0);
        	execution.setVariable("result", 0);
        	System.out.println("有一個人反对");
        	return true;
        }
        return false;
	}
}
