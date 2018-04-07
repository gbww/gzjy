package com.gzjy.receive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzjy.receive.mapper.ReceiveSampleMapper;
import com.gzjy.receive.mapper.ReportExtendMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleTask;
import com.gzjy.receive.model.ReportExtend;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;

@Service
public class ReportService {
	private static Logger logger = LoggerFactory.getLogger(ReportService.class);
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	UserService userService;
	@Autowired
	ReceiveSampleMapper receiveSampleMapper;
	@Autowired
	ReportExtendMapper reportExtendMapper;

	/**
	 * 编辑报告
	 * @param receiveSampleId
	 */
	@Transactional
	public void editReceiveSample(ReceiveSample receiveSample, ReportExtend reportExtend) {
		receiveSampleMapper.updateByPrimaryKeySelective(receiveSample);
		reportExtendMapper.updateByPrimaryKeySelective(reportExtend);
	}

	
	/**
	 * 启动报告流程引擎
	 * @param receiveSampleId
	 */
	@Transactional
	public void deploymentProcess(String receiveSampleId) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		User user = userService.getCurrentUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		// 设定流程标记tag=1
		variables.put("tag", 1);
		// 启动流程引擎时首先要指定编辑人，默认是当前用户
		variables.put("editPersion", user.getId());
		String processId = runtimeService.startProcessInstanceByKey("ReportProcess", variables).getId();
		// 流程启动成功之后将返回的流程ID回填到合同receive_sample表中
		ReceiveSample receiveSample = new ReceiveSample();
		receiveSample.setReceiveSampleId(receiveSampleId);		
		receiveSample.setReportProcessId(processId);
		receiveSampleMapper.updateByPrimaryKeySelective(receiveSample);
	}

	/**
	 * 根据用户ID获取当前用户任务
	 * @param isHandle(0表示查詢未完成任務，1表示查詢已完成任務)
	 * @return ArrayList<ContractTask>
	 */	
	public ArrayList<ReceiveSampleTask> getContractTaskByUserId(String isHandle) {
		String userId = userService.getCurrentUser().getId();
		ArrayList<ReceiveSampleTask> taskList = new ArrayList<ReceiveSampleTask>();
		if ("0".equals(isHandle)) {
			logger.info("查询未完成任务");
			TaskService taskService = processEngine.getTaskService();
			List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
			for (Task task : tasks) {
				logger.info("ID:" + task.getId() + ",任务名称:" + task.getName() + ",执行人:" + task.getAssignee() + ",开始时间:"
							+ task.getCreateTime());
				ReceiveSampleTask contractTask = new ReceiveSampleTask();
				contractTask.setId(task.getId());
				contractTask.setName(task.getName());
				contractTask.setAssignee(task.getAssignee());
				contractTask.setCreateTime(task.getCreateTime());
				contractTask.setProcessInstanceId(task.getProcessInstanceId());
				contractTask.setExecutionId(task.getExecutionId());
				taskList.add(contractTask);
			}
		} else {
			HistoryService historyService = processEngine.getHistoryService();
			List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().finished()
					.taskAssignee(userId).list();
			logger.info("历史任务:" + tasks.size() + " userId:" + userId);
			for (HistoricTaskInstance task : tasks) {
				logger.info("ID:" + task.getId() + ",任务名称:" + task.getName() + ",执行人:" + task.getAssignee() + ",开始时间:"
						+ task.getCreateTime());
				ReceiveSampleTask contractTask = new ReceiveSampleTask();
				contractTask.setId(task.getId());
				contractTask.setName(task.getName());
				contractTask.setAssignee(task.getAssignee());
				contractTask.setCreateTime(task.getCreateTime());
				contractTask.setProcessInstanceId(task.getProcessInstanceId());
				contractTask.setExecutionId(task.getExecutionId());
				taskList.add(contractTask);
			}
		}
		return taskList;
	}

	
	/**
	 * 执行报告中的编辑任务
	 * @param taskId  任务编号
	 */
	@Transactional
	public void doEditTask(String taskId, String receiveSampleId, String examinePersonId, String comment) {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).list().get(0);
		//执行任务过程中动态指定审核人
		runtimeService.setVariable(task.getExecutionId(), "examinePersonId", examinePersonId);
		//执行任务过程中需要设置标记位方便流程引擎区分
		runtimeService.setVariable(task.getExecutionId(), "tag", 2);
		ReceiveSample receiveSample = new ReceiveSample();
		receiveSample.setReceiveSampleId(receiveSampleId);
		//设置合同状态为待审核
		receiveSample.setReportStatus(1);
		receiveSampleMapper.updateByPrimaryKeySelective(receiveSample);
//		ReceiveSample receiveSample = receiveSampleMapper.selectByPrimaryKey(receiveSampleId);
//		taskService.addComment(taskId,null, comment);
		taskService.complete(taskId);	
		
	}
	
	/**
	 * 执行报告中的审核任务
	 * @param taskId  任务编号
	 */
	@Transactional
	public void doExamineTask(String taskId, String receiveSampleId,String approvePersonId, boolean pass, String comment) {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).list().get(0);
		//执行任务过程中可能同意可能拒绝,故需要设置标记位方便流程引擎区分
		if (pass) {
			runtimeService.setVariable(task.getExecutionId(), "tag", 3);
			//执行任务过程中动态指定批准人
			runtimeService.setVariable(task.getExecutionId(), "approvePersonId", approvePersonId);
		}else {
			runtimeService.setVariable(task.getExecutionId(), "tag", 1);
		}
		//设置合同状态为待批准
		ReceiveSample receiveSample = new ReceiveSample();
		receiveSample.setReceiveSampleId(receiveSampleId);				
		if(pass) {
			receiveSample.setReportStatus(2);
		}else {
			receiveSample.setReportStatus(0);
		}		
		receiveSampleMapper.updateByPrimaryKeySelective(receiveSample);
//		taskService.addComment(taskId, null, comment);
		taskService.complete(taskId);		
	}
	
	
	/**
	 * 执行报告中的批准任务
	 * @param taskId  任务编号
	 */
	@Transactional
	public void doApproveTask(String taskId, String receiveSampleId, boolean pass,String comment) {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).list().get(0);
		//执行任务过程中可能同意可能拒绝,故需要设置标记位方便流程引擎区分
		if (pass) {
			runtimeService.setVariable(task.getExecutionId(), "tag", 4);			
		}else {
			runtimeService.setVariable(task.getExecutionId(), "tag", 2);
		}
		ReceiveSample receiveSample = new ReceiveSample();
		receiveSample.setReceiveSampleId(receiveSampleId);	
		if(pass) {
			receiveSample.setReportStatus(3);
		}else {
			receiveSample.setReportStatus(1);
		}	
		receiveSampleMapper.updateByPrimaryKeySelective(receiveSample);
//		taskService.addComment(taskId, null, comment);
		taskService.complete(taskId);
	}
	
	
	/**
	 * 执行报告中的批准任务
	 * @param taskId  任务编号
	 *//*
	@Transactional
	public void doPrintTask(String taskId, String receiveSampleId,boolean pass) {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).list().get(0);
		//执行任务过程中可能同意可能拒绝,故需要设置标记位方便流程引擎区分
		if (pass) {
			runtimeService.setVariable(task.getExecutionId(), "tag", 5);			
		}else {
			runtimeService.setVariable(task.getExecutionId(), "tag", 3);
		}
		taskService.complete(taskId);
	}*/
}
