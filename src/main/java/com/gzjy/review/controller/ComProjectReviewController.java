package com.gzjy.review.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComProjectReview;
import com.gzjy.review.service.ComProjectReviewService;


/**
 * @Description: 企业评审信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/project/review" })
public class ComProjectReviewController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComProjectReviewController.class);

	@Autowired
	private ComProjectReviewService comProjectReviewService;

	/**
	 *  根据报告编码查询审核信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/select/{reviewReportId}", method = RequestMethod.GET)
	public Response selectByReviewReportId(@PathVariable(name = "reviewReportId",required = true) String reviewReportId){

		List <ComProjectReview> comProjectReview=comProjectReviewService.selectByReviewReportId(reviewReportId);

		return Response.success(comProjectReview);
	}

	/**
	 * 批量修改审核信息
	 * @param comProjectReview
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Response batchupdate(@RequestBody List<ComProjectReview> comProjectReview){

		try {
			comProjectReviewService.batchupdate(comProjectReview);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}

	}


}
