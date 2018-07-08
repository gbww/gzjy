package com.gzjy.review.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.review.modle.ComScale;
import com.gzjy.review.service.ComScaleService;


/**
 * @Description: 企业规模信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/scale" })
public class ComScaleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComScaleController.class);

	@Autowired
	private ComScaleService comScaleService;



	/**
	 * 查询所有企业规模信息
	 * @param pageCount
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public Response selectALL(){

		List<ComScale> comVarietyFormats = comScaleService.selectALL();
		return Response.success(comVarietyFormats);


	}


}
