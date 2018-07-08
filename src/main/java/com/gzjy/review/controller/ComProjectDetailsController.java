package com.gzjy.review.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.review.modle.ComProjectDetails;
import com.gzjy.review.service.ComProjectDetailsService;


/**
 * @Description: 企业评审项目明细信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/project/detail" })
public class ComProjectDetailsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComProjectDetailsController.class);

	@Autowired
	private ComProjectDetailsService comProjectDetailsService;

	
	/**
	 *  根据企业类型查询企业评审项目明细信息
	 * @param comType 企业类型 
	 * @return
	 */
	@RequestMapping(value = "/select/comType", method = RequestMethod.GET)
	public Response selectByComType(String comType){

		List<ComProjectDetails> comProjectDetails = comProjectDetailsService.selectByComType(comType);
		return Response.success(comProjectDetails);

	}

	
}
