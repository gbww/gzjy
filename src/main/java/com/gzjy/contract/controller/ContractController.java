package com.gzjy.contract.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.model.ContractComment;
import com.gzjy.contract.model.ContractProcess;
import com.gzjy.contract.model.ContractSample;
import com.gzjy.contract.model.ContractStatus;
import com.gzjy.contract.model.ContractTask;
import com.gzjy.contract.model.Sample;
import com.gzjy.contract.service.ContractCommentService;
import com.gzjy.contract.service.ContractService;
import com.gzjy.contract.service.SampleService;
import com.gzjy.log.constant.LogConstant;
import com.gzjy.log.service.LogService;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class ContractController {

	@Autowired
	ContractService contractService;

	@Autowired
	ContractCommentService contractCommentService;

	@Autowired
	LogService logService;

	@Autowired
	UserService userService;

	@Autowired
	SampleService sampleService;

	private static Logger logger = LoggerFactory.getLogger(ContractService.class);

	/**
	 * 录入合同信息到数据库
	 * 
	 * @param Contract实体对象
	 * @return
	 */
	@Privileges(name = "CONTRACT-ADD", scope = { 1 })
	@RequestMapping(value = "/contract", method = RequestMethod.POST)
	@Transactional
	public synchronized Response createContract(@RequestParam("files") MultipartFile[] files,
			@RequestParam(required = true) String contractSample, @RequestParam(required = true) String isFood) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		ContractSample contractSampleObject = null;
		try {
			contractSampleObject = objectMapper.readValue(contractSample, ContractSample.class);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return Response.fail("数据转换异常:" + e.getMessage());
		}
		if (contractSampleObject.getContract().getType() == null) {
			return Response.fail("合同类型为空");
		}
		Contract contract = contractSampleObject.getContract();
		contract.setStatus(ContractStatus.READY.getValue());
		Date now = new Date();
		contract.setCreatedAt(now);
		// 根据合同类型生成规则有序的合同编号
		String contractId = contractService.generateContractId(contractSampleObject.getContract().getType(), isFood);
		contract.setId(contractId);
		String appendix = "";
		String path = "var\\lib\\docs\\gzjy\\attachment\\";
		for (MultipartFile file : files) {
			appendix = appendix + path + contractId + "\\" + file.getOriginalFilename() + ";";
		}
		contract.setAppendix(appendix);
		try {
			contractService.insert(contract);
			for (Sample sample : contractSampleObject.getSampleList()) {
				String id = ShortUUID.getInstance().generateShortID();
				sample.setId(id);
				sample.setContractId(contractId);
				sampleService.insert(sample);
			}
			contractService.uploadFile(files, contractId);
			logService.insertLog(LogConstant.CONTRACT_INPUT.getCode(), contractId, null);
			return Response.success(contractId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 下载合同附件
	 * 
	 * @param Contract实体对象
	 * @return
	 */
	@RequestMapping(value = "/contract/{id}/appendix", method = RequestMethod.GET)
	public Response getAppendix(@PathVariable(required = true) String id,
			@RequestParam(required = true) String filename, HttpServletResponse response) {
		try {
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			OutputStream out = response.getOutputStream();
			if (out == null) {
				return Response.fail("附件不存在");
			}
			contractService.getAppendix(out, id, filename);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 删除合同附件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/contract/{id}/appendix", method = RequestMethod.DELETE)
	public Response deleteAppendix(@PathVariable(required = true) String id,
			@RequestParam(required = true) String filename) {
		try {
			contractService.deleteAppendix(id, filename);
			String appendix = contractService.getAppendixById(id);
			if (appendix != null) {
				String[] datas = appendix.split(";");
				String temp = "";
				for (String data : datas) {
					if (!data.contains(filename)) {
						temp += data + ";";
					}
				}
				Contract contract = new Contract();
				contract.setId(id);
				contract.setAppendix(temp);
				contractService.updateByPrimaryKey(contract);
				// 同时更新数据库字段信息
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.fail(e.getMessage());
		}
		return null;
	}

	/**
	 * 上传合同附件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/contract/{id}/appendix", method = RequestMethod.POST)
	public Response addAppendix(@PathVariable(required = true) String id, @RequestParam("file") MultipartFile file) {
		try {
			MultipartFile[] fileList = new MultipartFile[1];
			fileList[0] = file;
			contractService.uploadFile(fileList, id);
			String path = "var\\lib\\docs\\gzjy\\attachment\\";
			String appendix = contractService.getAppendixById(id);
			Contract contract = new Contract();
			contract.setId(id);
			contract.setAppendix(appendix + path + file.getOriginalFilename() + ";");
			contractService.updateByPrimaryKey(contract);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.fail(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取合同列表信息
	 * 
	 * @param Contract实体对象
	 * @return
	 */
	@Privileges(name = "CONTRACT-SELECT", scope = { 1 })
	@RequestMapping(value = "/contract", method = RequestMethod.GET)
	public Response getContractList(@RequestParam(required = false) String detectProject,
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String type) {
		try {
			PageInfo<Contract> result = contractService.getPageList(pageNum, pageSize, detectProject, type);
			return Response.success(result);
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 通过ID获取合同信息
	 * 
	 * @param id
	 * @return JSON对象(包含查询到的Contract实体信息)
	 */
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
	public Response getContractById(@PathVariable String id) {
		try {
			ContractSample contractSample = contractService.getContractDetail(id);
			return Response.success(contractSample);
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 检测合同协议ID是否存在
	 * 
	 * @param id
	 * @return true/false
	 */
	@RequestMapping(value = "/contract/check/{protocolId}", method = RequestMethod.GET)
	public Response checkContractById(@PathVariable String protocolId) {
		try {
			String result = contractService.checkContractProtocolId(protocolId);
			return Response.success(result != null);
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 通过ID删除合同信息
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	@Privileges(name = "CONTRACT-DELETE", scope = { 1 })
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.DELETE)
	public Response delContractById(@PathVariable String id) {
		try {
			contractService.deleteByPrimaryKey(id);
			sampleService.deleteByContractId(id);
			logService.insertLog(LogConstant.CONTRACT_DELETE.getCode(), id, null);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 更新合同信息
	 * 
	 * @param contract
	 * @return
	 */
	@Privileges(name = "CONTRACT-UPDATE", scope = { 1 })
	@RequestMapping(value = "/contract/update", method = RequestMethod.POST)
	@Transactional
	public Response updateContract(@RequestParam(required = true) String id,
			@RequestParam(required = true) String contractSample,
			@RequestParam(required = false) String deleteSampleIdList, 
			@RequestParam("files") MultipartFile[] files,
			@RequestParam(required = false) String deleteFileNameList) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		ContractSample contractSampleObject = null;
		try {
			contractSampleObject = objectMapper.readValue(contractSample, ContractSample.class);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return Response.fail("数据转换异常:" + e.getMessage());
		}
		Contract contract = contractSampleObject.getContract();
		try {
			Contract temp = contractService.selectByPrimaryKey(id);
			if (temp.getStatus() == ContractStatus.UPDATING.getValue()) {
				Task task = contractService.getUpdateTaskByProcessId(temp.getProcessId());
				User user = userService.getCurrentUser();
				if (user.getId().equals(task.getAssignee())) {
					contractService.completeUpdateTask(task.getId());
					contract.setStatus(ContractStatus.APPROVING.getValue());
				}
			}
			String appendix = temp.getAppendix();
			// 修改合同中新添加附件
			if (files.length != 0) {
				contractService.uploadFile(files, id);
				for (MultipartFile file : files) {
					appendix += "var\\lib\\docs\\gzjy\\attachment\\" + id + "\\" + file.getOriginalFilename()+";";
				}
				contract.setAppendix(appendix);
			}
			String tempFileArray[] = new String[50];
			//如果附件需要删除，在删除附件的时候也需要对数据库appendix进行更新
			if(deleteFileNameList!=null) {
				String delFileArray[] = deleteFileNameList.split(";");
				if (deleteFileNameList != null && (!deleteFileNameList.equals(""))) {
					String appendixArray[] = appendix.split(";");
					int j = 0;
					for (String a : appendixArray) {
						int find = 0;
						for (String d : delFileArray) {
							if (a.contains(d)) {
								find = 1;
								break;
							}
						}
						if (find == 0) {
							tempFileArray[j] = a;
							j += 1;
						}
					}
				}
				for (String d : delFileArray) {
					contractService.deleteAppendix(id, d);
				}
				String appendixTemp = "";
				for (String s : tempFileArray) {
					if (s != null) {
						appendixTemp += s + ";";
					}
				}
				contract.setAppendix(appendixTemp);
			}			
			// 修改数据库表数据
			contract.setId(id);			
			contract.setUpdatedAt(new Date());
			contractService.updateByPrimaryKey(contract);
			List<Sample> sampleList = contractSampleObject.getSampleList();
			// 删除合同修改中去掉的样品
			if (deleteSampleIdList != null && (!deleteSampleIdList.equals(""))) {
				List<String> sampleIdList = Arrays.asList(deleteSampleIdList.split(";"));
				sampleService.deleteByIds(sampleIdList);
			}
			//添加或修改合同中的样品
			if (sampleList != null && sampleList.size() != 0) {
				for (Sample sample : sampleList) {
					if (sample.getId() != null) {
						sampleService.updateSampleById(sample);
					} else {
						sample.setId(ShortUUID.getInstance().generateShortID());
						sample.setContractId(id);
						sampleService.insert(sample);
					}
				}
			}			
			logService.insertLog(LogConstant.CONTRACT_UPDATE.getCode(), id, null);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 启动合同流程
	 * 
	 * @param id
	 * @return
	 */
	@Privileges(name = "CONTRACT-STARTPROCESS", scope = { 1 })
	@RequestMapping(value = "/contract/process", method = RequestMethod.POST)
	public Response startContractProcess(@RequestBody ContractProcess contractProcess) {
		ArrayList<String> approveUsers = contractProcess.getApproveUsers();
		String updateContractUser = contractProcess.getUpdateContractUser();
		String contractId = contractProcess.getContractId();
		try {
			contractService.deploymentProcess(contractId, approveUsers, updateContractUser);
			logService.insertLog(LogConstant.CONTRACT_REVIEW.getCode(), contractId, null);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 根据用户ID获取当前用户任务
	 * 
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/contract/process/task", method = RequestMethod.GET)
	public Response getContractTaskByUserId(@RequestParam(required = false, defaultValue = "1") String isHandle) {
		ArrayList<ContractTask> taskList = new ArrayList<ContractTask>();
		try {
			if ("0".equals(isHandle)) {
				logger.info("查询未完成任务");
				List<Task> tasks = contractService.getTaskByUserId();
				for (Task task : tasks) {
					logger.info("ID:" + task.getId() + ",姓名:" + task.getName() + ",接收人:" + task.getAssignee() + ",开始时间:"
							+ task.getCreateTime());
					ContractTask contractTask = new ContractTask();
					contractTask.setId(task.getId());
					contractTask.setName(task.getName());
					contractTask.setAssignee(task.getAssignee());
					contractTask.setCreateTime(task.getCreateTime());
					contractTask.setProcessInstanceId(task.getProcessInstanceId());
					contractTask.setExecutionId(task.getExecutionId());
					taskList.add(contractTask);
				}
				return Response.success(taskList);
			} else {
				logger.info("查询已完成任务");
				List<HistoricTaskInstance> tasks = contractService.getHistoryTaskByUserId();
				for (HistoricTaskInstance task : tasks) {
					logger.info("ID:" + task.getId() + ",姓名:" + task.getName() + ",接收人:" + task.getAssignee() + ",开始时间:"
							+ task.getCreateTime());
					ContractTask contractTask = new ContractTask();
					contractTask.setId(task.getId());
					contractTask.setName(task.getName());
					contractTask.setAssignee(task.getAssignee());
					contractTask.setCreateTime(task.getCreateTime());
					contractTask.setProcessInstanceId(task.getProcessInstanceId());
					contractTask.setExecutionId(task.getExecutionId());
					taskList.add(contractTask);
				}
				return Response.success(taskList);
			}

		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	/**
	 * 执行合同流程中任务
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contract/process/task/{taskId}", method = RequestMethod.GET)
	public Response approveContractTask(@PathVariable String taskId, @RequestParam(required = false) String approve,
			@RequestParam(required = false) String context, @RequestParam(required = true) String action,
			@RequestParam(required = false) String contractId) {
		String extra = null;
		try {
			// 如果是审批操作
			if (action.equals("approve")) {
				extra = approve.equals("true") ? "批准该流程" : "驳回该流程";
				// 必须带上行为true/false
				if (approve == null || !(approve.equals("true") || approve.equals("false"))) {
					return Response.fail("Param approve must be true or false");
				}
				if (contractId == null || contractId.equals("")) {
					return Response.fail("Param contractId must not null");
				}
				if (approve.equals("false") && context == null)
					return Response.fail("Param  context must not null");
				contractService.completeApproveTask(taskId, contractId, approve, context);
			} else if (action.equals("update")) {
				extra = "修改该合同流程";
				contractService.completeUpdateTask(taskId);
				Contract record = new Contract();
				record.setId(contractId);
				record.setStatus(ContractStatus.APPROVING.getValue());
				contractService.updateByPrimaryKey(record);
			}
			logService.insertLog(LogConstant.CONTRACT_APPROVE.getCode(), contractId, extra);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/contract/process/comment", method = RequestMethod.GET)
	public Response getContractComment(@RequestParam(required = true) String contract_id) {
		try {
			List<ContractComment> result = contractCommentService.selectLatestComment(contract_id);
			return Response.success(result);
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/contract/process/comment", method = RequestMethod.POST)
	public Response insertSelective(@RequestBody ContractComment contractComment) {
		try {
			contractCommentService.insert(contractComment);
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}	
}