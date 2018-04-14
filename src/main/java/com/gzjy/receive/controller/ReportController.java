package com.gzjy.receive.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.receive.mapper.ReportExtendMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.model.ReceiveSampleTask;
import com.gzjy.receive.model.ReportExtend;
import com.gzjy.receive.service.ReceiveSampleService;
import com.gzjy.receive.service.ReportService;
import com.gzjy.user.UserService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	@Autowired
	private DataSource dataSource; 
	@Autowired
	private EpicNFSService epicNFSService;

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Response editReport(@RequestBody(required = true) ReceiveSample receiveSample) throws Exception {
		try {
			reportService.editReceiveSample(receiveSample);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 根据receiveSampleId获取报告详情
	 * @param receiveSampleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{receiveSampleId}", method = RequestMethod.GET)
	public Response reportDetail(@PathVariable(required = true) String receiveSampleId) throws Exception {
		try {
			ReceiveSample receiveSample=reportService.getReceiveSample(receiveSampleId);
			ReportExtend reportExtend=reportExtendMapper.selectByReportId(receiveSample.getReportId());
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("receiveSample", receiveSample);
			result.put("reportExtend", reportExtend);
			return Response.success(result);
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
	 * @param isHandle(0表示查看未完成任务，1 表示查看已完成任务)
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
	 * @param taskId            任务编号
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
	 * @param taskId           任务编号
	 * @param receiveSampleId  收样单编号
	 * @param approvePersonId  批准人编号
	 * @param pass             是否通过
	 * @param comment          评论意见
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
	 * @param taskId            任务编号
	 * @param receiveSampleId   收样单编号
	 * @param pass              是否通过
	 * @param comment           评论意见
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
	 * 查看待编辑报告列表
	 */
	@RequestMapping(value = "/list/edit", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response getEditListReports(
			@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
//			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		PageInfo<ReceiveSample> result = reportService.getReportByCondition(
				id,reportId,entrustedUnit,inspectedUnit,sampleName,executeStandard,productionUnit,
				sampleType,checkType,0,order,status,pageNum,pageSize,startTime,endTime);
		return Response.success(result);
	}
	
	
	/**
	 * 查看待审核报告列表
	 */
	@RequestMapping(value = "/list/examine", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response getExamineListReports(
			@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
//			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		PageInfo<ReceiveSample> result = reportService.getReportByCondition(
				id,reportId,entrustedUnit,inspectedUnit,sampleName,executeStandard,productionUnit,
				sampleType,checkType,1,order,status,pageNum,pageSize,startTime,endTime);
		return Response.success(result);
	}
	
	/**
	 * 查看待批准报告列表
	 */
	@RequestMapping(value = "/list/approve", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response getApproveListReports(
			@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
//			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		PageInfo<ReceiveSample> result = reportService.getReportByCondition(
				id,reportId,entrustedUnit,inspectedUnit,sampleName,executeStandard,productionUnit,
				sampleType,checkType,2,order,status,pageNum,pageSize,startTime,endTime);
		return Response.success(result);
	}
	
	/**
	 * 查看待打印报告列表
	 */
	@RequestMapping(value = "/list/print", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response getPrintListReports(
			@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
//			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		PageInfo<ReceiveSample> result = reportService.getReportByCondition(
				id,reportId,entrustedUnit,inspectedUnit,sampleName,executeStandard,productionUnit,
				sampleType,checkType,3,order,status,pageNum,pageSize,startTime,endTime);
		return Response.success(result);
	}
	
	
	/**
	 * 查看已完成报告列表
	 */
	@RequestMapping(value = "/list/finish", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response getFinishListReports(
			@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
//			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		PageInfo<ReceiveSample> result = reportService.getReportByCondition(
				id,reportId,entrustedUnit,inspectedUnit,sampleName,executeStandard,productionUnit,
				sampleType,checkType,4,order,status,pageNum,pageSize,startTime,endTime);
		return Response.success(result);
	}
	
	/**
	 * 查看所有报告列表
	 */
	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response getAllListReports(
			@RequestParam(name = "receiveSampleId", required = false) String id,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
//			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		PageInfo<ReceiveSample> result = reportService.getReportByCondition(
				id,reportId,entrustedUnit,inspectedUnit,sampleName,executeStandard,productionUnit,
				sampleType,checkType,null,order,status,pageNum,pageSize,startTime,endTime);
		return Response.success(result);
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
	 * 修改报告中的检验项
	 * @return
	 */
	@RequestMapping(value = "/item/{receiveSampleItemId}", method = RequestMethod.PUT)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response detailReport(@PathVariable(name = "receiveSampleItemId", required = true) String receiveSampleItemId) {
		ReceiveSampleItem record = new ReceiveSampleItem();
		record.setId(receiveSampleItemId);
		try {
			reportService.updateReceiveSampleItem(record);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 修改报告中的签发日期
	 * @return
	 */
	@RequestMapping(value = "/{receiveSampleItemId}/property/qianfa", method = RequestMethod.PUT)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response updateQianfa(
			@PathVariable(name = "receiveSampleItemId", required = true) String receiveSampleItemId,
			@RequestParam(required = true)String qianfaDate) {
		ReceiveSample record = new ReceiveSample();
		record.setReceiveSampleId(receiveSampleItemId);
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			record.setQianfaDate(myFmt2.parse(qianfaDate));
			reportService.updateReceiveSample(record);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 指定报告对应魔板
	 * @return
	 */
	@RequestMapping(value = "/template/bind", method = RequestMethod.POST)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response receiveBindTemplate(@RequestBody ReportExtend reportExtend) {
		reportExtend.setId(ShortUUID.getInstance().generateShortID());		
		try {
			reportService.insertReportExtend(reportExtend);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	
	/**
	 * 修改报告对应魔板
	 * @return
	 */
	@RequestMapping(value = "/template/bind/{id}", method = RequestMethod.PUT)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response receiveBindTemplateEdit(
			@PathVariable(name = "id", required = true) String id,
			@RequestParam(name = "reportId", required = true) String reportId,
			@RequestParam(name = "templateId", required = true) String templateId,
			@RequestParam(name = "templateName", required = true) String templateName,
			@RequestParam(name = "templateDesc", required = false) String templateDesc) {
		 
		ReportExtend record = new ReportExtend();
		record.setId(id);
		record.setReportId(reportId);
		record.setTemplateId(templateId);
		record.setTemplateName(templateName);
		record.setTemplateDesc(templateDesc);
		try {
			reportService.updateReportExtend(record);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 删除报告对应魔板
	 * @return
	 */
	@RequestMapping(value = "/template/bind/{id}", method = RequestMethod.DELETE)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response receiveBindTemplateEdit(@PathVariable(name = "id", required = true) String id){
		try {
			reportService.deleteReportExtend(id);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 报告的导出pdf
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
	public Response exportReport(@RequestParam(name = "receiveSampleId", required = true) String receiveSampleId,
			HttpServletResponse response){
		try {
			ReceiveSample result = receiveSampleService.getReceiveSample(receiveSampleId);
			if(result==null) {
				return Response.fail("未查询到对应的报告信息");
			}
			ReportExtend reportExtend = reportExtendMapper.selectByReportId(receiveSampleId);
			String templateDir = "/var/lib/docs/gzjy/template/"+reportExtend.getTemplateName();			
			//设定报表所需要的外部参数内容
		    Map<String, Object> rptParameters = new HashMap<String, Object>();		    
		    rptParameters.put("receiveSampleId", receiveSampleId);
		    //传入报表源文件绝对路径，外部参数对象，DB连接，得到JasperPring对象
		    JasperPrint jasperPrint = JasperFillManager.fillReport(templateDir, rptParameters, dataSource.getConnection());
		    response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(receiveSampleId+".pdf", "UTF-8"));
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			if(out!=null) {
				out.close();
			}
		    logger.info("Export success!!");
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}finally {
			
		}
	}
	
	
}
