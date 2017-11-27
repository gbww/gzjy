package com.gzjy.log.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		record.setOperation(LogConstant.SYSTEM_LOGIN.getValue());
		record.setTarget("hfwe");
		record.setCreateTime(new Date());
		try {
			logService.insertLog(record);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public Response getLogList() {		
		LogModel record = new LogModel();
		record.setId(ShortUUID.getInstance().generateShortID());
		record.setOperateUserId("12123123");
		record.setOperateUser("zhangsan");
		record.setOperation(LogConstant.SYSTEM_LOGIN.getValue());
		record.setTarget("hfwe");
		record.setCreateTime(new Date());
		try {
			List <LogModel> result = logService.selectAll(record);
			return Response.success(result);
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/log/page", method = RequestMethod.GET)
	public Response getLogPageList() {
		try {
			PageInfo <LogModel> result = logService.getPageList(1, 10, "", "", "", "");
			return Response.success(result);
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/log", method = RequestMethod.DELETE)
	public Response deleteLog() {
		try {
			logService.deleteByCreateTime(new Date());
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
}
