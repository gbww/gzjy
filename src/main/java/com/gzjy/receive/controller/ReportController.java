package com.gzjy.receive.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.receive.mapper.ReportExtendMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.model.ReceiveSampleTask;
import com.gzjy.receive.model.ReportExtend;
import com.gzjy.receive.service.ReceiveSampleService;
import com.gzjy.receive.service.ReportService;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;

@RestController
@RequestMapping(value = "v1/ahgz/report")

public class ReportController {
	private static Logger logger = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private ReportService reportService;
	@Autowired
	UserService userService;
	@Autowired
	ReceiveSampleService receiveSampleService;
	@Autowired
	ReportExtendMapper reportExtendMapper;

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Response editReport(@RequestBody(required = true) ReceiveSample receiveSample,
			@RequestBody(required = true) ReportExtend reportExtend) throws Exception {
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
	 * 
	 * @param receiveSampleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public Response startProcess(@RequestParam(required = true) String receiveSampleId) throws Exception {
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
	 * 
	 * @param isHandle(0表示查看未完成任务，1
	 *            表示查看已完成任务)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public Response getTaskByUser(@RequestParam(required = true, defaultValue = "0") String isHandle) throws Exception {
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
	 * @param taskId   任务编号
	 * @param receiveSampleId   收样单编号
	 * @param examinePersonId   审核人编号
	 * @param comment           评论意见
	 * @return
	 */
	@RequestMapping(value = "/process/task/edit/{taskId}", method = RequestMethod.GET)
	public Response completeEditTask(@PathVariable String taskId, @RequestParam(required = true) String receiveSampleId,
			@RequestParam(required = true) String examinePersonId, @RequestParam(required = false) String comment) {
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
	 * 
	 * @param taskId
	 *            任务编号
	 * @param receiveSampleId
	 *            收样单编号
	 * @param approvePersonId
	 *            批准人编号
	 * @param pass
	 *            是否通过
	 * @param comment
	 *            评论意见
	 * @return
	 */
	@RequestMapping(value = "/process/task/examine/{taskId}", method = RequestMethod.GET)
	public Response completeExamineTask(@PathVariable String taskId,
			@RequestParam(required = true) String receiveSampleId,
			@RequestParam(required = true) String approvePersonId, @RequestParam(required = true) boolean pass,
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
	 * 
	 * @param taskId
	 *            任务编号
	 * @param receiveSampleId
	 *            收样单编号
	 * @param pass
	 *            是否通过
	 * @param comment
	 *            评论意见
	 * @return
	 */
	@RequestMapping(value = "/process/task/approve/{taskId}", method = RequestMethod.GET)
	public Response completeApproveTask(@PathVariable String taskId,
			@RequestParam(required = true) String receiveSampleId, @RequestParam(required = true) boolean pass,
			@RequestParam(required = false) String comment) {
		try {
			reportService.doApproveTask(taskId, receiveSampleId, pass, comment);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 查看报告列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response listReports(@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		Map<String, Object> filter = new HashMap<String, Object>();
		if (StringUtils.isBlank(startTime)) {
			startTime = null;
		}
		if (StringUtils.isBlank(endTime)) {
			endTime = null;
		}
		if (!StringUtils.isBlank(reportId)) {
			filter.put("report_id", reportId);
		}
		if (!StringUtils.isBlank(inspectedUnit)) {
			filter.put("inspected_unit", inspectedUnit);
		}
		if (!StringUtils.isBlank(sampleName)) {
			filter.put("sample_name", sampleName);
		}
		if (!StringUtils.isBlank(executeStandard)) {
			filter.put("execute_standard", executeStandard);
		}
		if (!StringUtils.isBlank(productionUnit)) {
			filter.put("production_unit", productionUnit);
		}
		if (!StringUtils.isBlank(id)) {
			filter.put("receive_sample_id", id);
		}
		if (!StringUtils.isBlank(entrustedUnit)) {
			filter.put("entrusted_unit", entrustedUnit);
		}
		if (!StringUtils.isBlank(sampleType)) {
			filter.put("sample_type", sampleType);
		}
		if (!StringUtils.isBlank(checkType)) {
			filter.put("check_type", checkType);
		}
		if (reportStatus != null && reportStatus != 5) { 
			filter.put("report_status", reportStatus);
			User u = userService.getCurrentUser();
			boolean superUser = u.getRole().isSuperAdmin();
			if (!superUser) {
				String name = u.getName();
				if (reportStatus == 0) {
					filter.put("draw_user", name);
				}
				if (reportStatus == 1) {
					filter.put("examine_user", name);
				}
				if (reportStatus == 2) {
					filter.put("approval_user", name);
				}
			}
		}
		if (StringUtils.isBlank(order)) {
			order = "created_at desc";
		}
		if (status != 5) {
			filter.put("status", status);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		try {
			start = startTime == null ? null : sdf.parse(startTime);
			end = endTime == null ? null : sdf.parse(endTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException("输入的时间格式不合法！");
		}
		return Response.success(receiveSampleService.select(pageNum, pageSize, order, filter, start, end));
	}

	/**
	 * 查看报告中的检验项
	 * @return
	 */
	@RequestMapping(value = "/list/{receiveSampleId}", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response detailReportItem(@PathVariable(name = "receiveSampleId", required = true) String receiveSampleId) {
		try {
			List<ReceiveSampleItem> getReceiveSampleItemList = reportService.getReceiveSampleItemList(receiveSampleId);
			return Response.success(getReceiveSampleItemList);
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 查看报告中的检验项
	 * @return
	 */
	@RequestMapping(value = "/item/{receiveSampleItemId}", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response detailReport(@PathVariable(name = "receiveSampleItemId", required = true) String receiveSampleItemId) {
		ReceiveSampleItem record = new ReceiveSampleItem();
		record.setId(receiveSampleItemId);
		try {
			reportService.updateReceiveSample(record);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
}
