package com.gzjy.receive.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleTask;
import com.gzjy.receive.model.ReportExtend;
import com.gzjy.receive.service.ReportService;

@RestController
@RequestMapping(value = "v1/ahgz/report")

public class ReportController {
	private static Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	@Autowired
	private ReportService reportService;
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Response editReport(@RequestBody(required = true) ReceiveSample receiveSample,
			@RequestBody(required = true)ReportExtend reportExtend) throws Exception{
		try {
			reportService.editReceiveSample(receiveSample, reportExtend);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 启动报告流程引擎接口
	 * @param receiveSampleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public Response startProcess(@RequestParam(required = true) String receiveSampleId) throws Exception{
		try {
			reportService.deploymentProcess(receiveSampleId);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	
	/**
	 * 查看当前用户任务列表
	 * @param isHandle(0表示查看未完成任务，1 表示查看已完成任务)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public Response getTaskByUser(@RequestParam(required = true, defaultValue="0") String isHandle) throws Exception{
		try {
			ArrayList<ReceiveSampleTask> result = reportService.getContractTaskByUserId(isHandle);
			return Response.success(result);
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	
	/**
	 * 执行合同流程中编辑任务
	 * @param taskId  任务编号
	 * @param receiveSampleId 收样单编号
	 * @param examinePersonId 审核人编号
	 * @param comment 评论意见
	 * @return
	 */
	@RequestMapping(value = "/process/task/edit/{taskId}", method = RequestMethod.GET)
	public Response completeEditTask(@PathVariable String taskId,
			@RequestParam(required = true) String receiveSampleId,
			@RequestParam(required = true) String examinePersonId,
			@RequestParam(required = false) String comment) {		
		try {
			reportService.doEditTask(taskId, receiveSampleId, examinePersonId, comment);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 执行合同流程中审核任务
	 * @param taskId  任务编号
	 * @param receiveSampleId 收样单编号
	 * @param approvePersonId 批准人编号
	 * @param pass 是否通过
	 * @param comment 评论意见
	 * @return
	 */
	@RequestMapping(value = "/process/task/examine/{taskId}", method = RequestMethod.GET)
	public Response completeExamineTask(@PathVariable String taskId,
			@RequestParam(required = true) String receiveSampleId,
			@RequestParam(required = true) String approvePersonId,
			@RequestParam(required = true) boolean pass,
			@RequestParam(required = false) String comment) {		
		try {
			reportService.doExamineTask(taskId, receiveSampleId, approvePersonId, pass, comment);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 执行合同流程中批准任务
	 * @param taskId  任务编号
	 * @param receiveSampleId 收样单编号
	 * @param pass 是否通过
	 * @param comment 评论意见
	 * @return
	 */
	@RequestMapping(value = "/process/task/approve/{taskId}", method = RequestMethod.GET)
	public Response completeApproveTask(@PathVariable String taskId,
			@RequestParam(required = true) String receiveSampleId,
			@RequestParam(required = true) boolean pass,
			@RequestParam(required = false) String comment) {		
		try {
			reportService.doApproveTask(taskId, receiveSampleId, pass, comment);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}	
	
}
