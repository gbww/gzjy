package com.gzjy.receive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;

@RestController
@RequestMapping(value = "v1/ahgz/fr/report")

public class FrReportController {
	private static Logger logger = LoggerFactory.getLogger(FrReportController.class);

	/**
	 * 查詢報表列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//@Privileges(name = "REPORT-MUTI-STATUS-UPDATE", scope = { 1 })
	public Response reportList(
			@RequestParam(required = true) String reportId,
			@RequestParam(required = true) String processId,
			@RequestParam(required = true) String newDrawUser) {
		try {
			return Response.success("success");
		} catch (Exception e) {
			logger.error(e + "");
			return Response.fail(e.getMessage());
		}
	}

}
