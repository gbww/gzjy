package com.gzjy.contract.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private ContractMapper contractMapper;
	
	@Autowired
	private ProcessEngine processEngine;
	
	
	public Contract selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return contractMapper.selectByPrimaryKey(id);
	}

	public int insert(Contract record) {
		// TODO Auto-generated method stub
		return contractMapper.insert(record);
	}

	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return contractMapper.deleteByPrimaryKey(id);
	}
	
	public int updateByPrimaryKey(Contract record) {
		return contractMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 部署合同审批流程实例
	 * 部署完成之后将流程ID反填到合同表中
	 * @param approveUsers 合同审批的多个审批人
	 * @param updateContractUser 修改合同的人
	 * @return
	 */
	public void deploymentProcess(String contractId, ArrayList<String> approveUsers, String updateContractUser) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        repositoryService.createDeployment().addClasspathResource("processes/ContractProcess.bpmn").deploy();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approve_users", approveUsers);
        variables.put("resultCount", 0);
        variables.put("taskComplete", new TaskComplete());
        String processId = runtimeService.startProcessInstanceByKey("TestProcess",variables).getId();
        //流程启动成功之后将返回的流程ID回填到合同contract表中
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setProcessId(processId);
        contractMapper.updateByPrimaryKeySelective(contract);
	}

	/**
	 * 根据用户ID获取当前用户在合同流程中的任务
	 */
	public List<Task> getTaskByUserId(String taskName,String userId) {
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).taskName(taskName).list();
		return tasks;
	}
	
	/**
	 * 执行合同流程中的多人审批任务任务
	 * @param task
	 * @param approve
	 */	
	public void completeApproveTask(String taskId, String approve) {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Task task = (Task) taskService.createTaskQuery().taskId(taskId);
//		如果是审批流程中的同意状态，则需要将result值加1，否则不需额外操作
		if(approve.equals("true")) {
			int result = (int)runtimeService.getVariable(task.getExecutionId(), "result");
			runtimeService.setVariable(task.getExecutionId(), "result", result + 1);
		}
		//taskService.setVariable(taskId, "请假日期", new Date());
        //taskService.setVariable(taskId, "请假原因", "回家探亲");
		taskService.complete(task.getId());
	}
	
	/**
	 * 执行合同流程中的修改合同任务
	 * @param task
	 * @param approve
	 */	
	public void completeUpdateTask(String taskId) {
		TaskService taskService = processEngine.getTaskService();
		taskService.complete(taskId);
	}
}
