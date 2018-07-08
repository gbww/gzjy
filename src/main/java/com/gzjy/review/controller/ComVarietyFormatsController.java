package com.gzjy.review.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.review.modle.ComVarietyFormats;
import com.gzjy.review.service.ComVarietyFormatsService;


/**
 * @Description: 品种业态信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/variety/format" })
public class ComVarietyFormatsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComVarietyFormatsController.class);

	@Autowired
	private ComVarietyFormatsService comVarietyFormatsService;



	/**
	 * 查询所有品种业态信息
	 * @param pageCount
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/selectALL", method = RequestMethod.GET)
	public Response selectALL(){

		List<ComVarietyFormats> comVarietyFormats = comVarietyFormatsService.selectALL();
		return Response.success(comVarietyFormats);


	}

	
}
