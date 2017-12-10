package com.gzjy.log.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.log.constant.LogConstant;
import com.gzjy.log.model.LogModel;
import com.gzjy.log.service.LogService;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class LogController {

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public Response createLog() {

		LogModel record = new LogModel();
		record.setId(ShortUUID.getInstance().generateShortID());
		record.setOperateUserId("12123123");
		record.setOperateUser("zhangsan");
		record.setOperation(LogConstant.SYSTEM_LOGIN.getCode());
		record.setTarget("hfwe");
		record.setCreateTime(new Date());
		try {
			logService.insertLog(record);
			return Response.success("success");
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public Response getLogList() {
		LogModel record = new LogModel();
		record.setId(ShortUUID.getInstance().generateShortID());
		record.setOperateUserId("12123123");
		record.setOperateUser("zhangsan");
		record.setOperation(LogConstant.SYSTEM_LOGIN.getCode());
		record.setTarget("hfwe");
		record.setCreateTime(new Date());
		try {
			List<LogModel> result = logService.selectAll(record);
			return Response.success(result);
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/log/page", method = RequestMethod.GET)
	public Response getLogPageList(@RequestParam(required = false) String operateUserId,
			@RequestParam(required = false) String operateUser, @RequestParam(required = false) String target,
			@RequestParam(required = false) String operation,
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		try {
			PageInfo<LogModel> result = logService.getPageList(pageNum, pageSize, operateUserId, operateUser, target,
					operation);
			return Response.success(result);
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/log", method = RequestMethod.DELETE)
	public Response deleteLog() {
		try {
			logService.deleteByCreateTime(new Date());
			return Response.success("success");
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/log/constant", method = RequestMethod.GET)
	public Response getLogConstant() {
		Map<String, String> result = new HashMap<String, String>();
		try {
			for (LogConstant node : LogConstant.values()) {
				result.put(node.getCode(), node.getName());
			}
			return Response.success(result);
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
}
