package com.gzjy.contract.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.checkitems.model.CheckItem;
import com.gzjy.common.util.UUID;
import com.gzjy.contract.mapper.ContractCommentMapper;
import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.model.ContractComment;
import com.gzjy.contract.model.ContractStatus;
import com.gzjy.contract.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private ContractMapper contractMapper;
	
	@Autowired
	private ContractCommentMapper contractCommentMapper;
	
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
        variables.put("result", 1);
        variables.put("update_contract_user", updateContractUser);
        variables.put("taskComplete", new TaskComplete());
        String processId = runtimeService.startProcessInstanceByKey("ContractProcess",variables).getId();
        //流程启动成功之后将返回的流程ID回填到合同contract表中
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setProcessId(processId);
        //修改合同状态为审批中状态
        contract.setStatus(ContractStatus.APPROVING.getValue());
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
	public void completeApproveTask(String taskId, String contractId,String approve, String context) {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).list().get(0);
//		如果是审批流程中的同意状态，则需要将result值加1，否则不需额外操作
		if(approve.equals("true")) {
			int result = (int)runtimeService.getVariable(task.getExecutionId(), "resultCount");
			runtimeService.setVariable(task.getExecutionId(), "resultCount", result + 1);
		}
//		将评审意见插入到合同评审意见表中
		taskService.complete(task.getId());
		ContractComment contractComment = new ContractComment();
		contractComment.setId(UUID.random());
		contractComment.setTaskId(taskId);
		contractComment.setUserId("11111");
		contractComment.setUserName("zhangsna");
		contractComment.setContext(context);
		contractComment.setContractId(contractId);
		contractComment.setCreateTime(new Date());
		contractCommentMapper.insertSelective(contractComment);
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

	public PageInfo<Contract> getPageList(Integer pageNum, Integer pageSize, String sampleName) {
		List<Contract> list = new ArrayList<Contract>();
	    PageInfo<Contract> pages = new PageInfo<Contract>(list);
	    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
	        @Override
	        public void doSelect() {
	        	contractMapper.selectAll(sampleName);
	        }
	    });
	    return pages;
	}
}
