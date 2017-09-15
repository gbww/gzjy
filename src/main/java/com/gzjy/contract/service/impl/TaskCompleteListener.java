package com.gzjy.contract.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class TaskCompleteListener implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("Task Listener");

	}

}
