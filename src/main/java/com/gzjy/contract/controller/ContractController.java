package com.gzjy.contract.controller;

import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gzjy.common.Response;
import com.gzjy.common.util.UUID;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.model.ContractComment;
import com.gzjy.contract.model.ContractProcess;
import com.gzjy.contract.model.ContractTask;
import com.gzjy.contract.service.ContractCommentService;
import com.gzjy.contract.service.ContractService;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	ContractCommentService contractCommentService;
	
	/** 
	 * 录入合同信息到数据库
	 * @param Contract实体对象
	 * @return 
	 */
	@RequestMapping(value = "/contract", method = RequestMethod.POST)
	public Response createContract(@RequestBody Contract contract) {
		try {
			contract.setId(UUID.random());
			contractService.insert(contract);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/** 
	 * 通过ID获取合同信息
	 * @param id
	 * @return JSON对象(包含查询到的Contract实体信息)
	 */
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
	public Response getContractById(@PathVariable String id) {
		try {
			Contract contract = contractService.selectByPrimaryKey(id);
			return Response.success(contract);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 通过ID删除合同信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.DELETE)
	public Response delContractById(@PathVariable String id) {
		try {
			contractService.deleteByPrimaryKey(id);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 更新合同信息
	 * @param contract
	 * @return
	 */
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.PUT)
	public Response updateContract(@PathVariable String id, @RequestBody Contract contract) {
		try {
			contract.setId(id);
			contractService.updateByPrimaryKey(contract);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 启动合同流程
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contract/process", method = RequestMethod.POST)
	public Response startContractProcess(@RequestBody ContractProcess contractProcess) {
		ArrayList<String> approveUsers = contractProcess.getApproveUsers();
		String updateContractUser = contractProcess.getUpdateContractUser();
		String contractId = contractProcess.getContractId();		
		try {
			
			contractService.deploymentProcess(contractId, approveUsers, updateContractUser);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 根据用户ID获取当前用户任务
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/contract/process/task", method = RequestMethod.GET)
	public Response getContractTaskByUserId(@RequestParam(required = true) String userId,
			@RequestParam(required = true) String taskName) {
		ArrayList<ContractTask> taskList = new ArrayList<ContractTask>();
		try {
			List<Task> tasks= contractService.getTaskByUserId(taskName, userId);
			for (Task task :tasks) {
				System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());
				ContractTask contractTask = new ContractTask();
				contractTask.setId(task.getId());
				contractTask.setName(task.getName());
				contractTask.setAssignee(task.getAssignee());
				contractTask.setCreateTime(task.getCreateTime());
				taskList.add(contractTask);
			}
			return Response.success(taskList);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 执行合同流程中任务
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contract/process/task/{taskId}", method = RequestMethod.GET)
	public Response approveContractTask(@PathVariable String taskId, 
			@RequestParam String approve, @RequestParam String context,
			@RequestParam(required = true) String action,
			@RequestParam String contractId) {
		try {
			if (action.equals("approve")){
				if(approve.equals("")||context.equals(""))
					return Response.fail("Param approve or context must not null");
				contractService.completeApproveTask(taskId, contractId, approve, context);
			}else if (action.equals("update")){
				contractService.completeUpdateTask(taskId);
			}			
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}	
	
	
	@RequestMapping(value = "/contract/process/comment", method = RequestMethod.GET)
	public Response getContractComment(@RequestParam(required = true) String contract_id) {
		try {
			List<ContractComment> result = contractCommentService.selectLatestComment(contract_id);
			return Response.success(result);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}		
	}
}