package com.gzjy.review.controller;

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
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComReviewReport;
import com.gzjy.review.service.ComReviewReportService;


/**
 * @Description: 企业评审报告信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/review/report" })
public class ComReviewReportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComReviewReportController.class);

	@Autowired
	private ComReviewReportService comReviewReportService;


	/**
	 *  根据主键查询企业评审报告信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/selectById/{reviewReportId}", method = RequestMethod.GET)
	public Response selectByPrimaryKey(@PathVariable(name = "reviewReportId",required = true) String reviewReportId){
		ComReviewReport comReviewReport=comReviewReportService.selectByPrimaryKey(reviewReportId);
		return Response.success(comReviewReport);
	}



	/**
	 *  根据企业id查询企业评审报告信息
	 * @param CompanyId 企业id
	 * @return
	 */
	@RequestMapping(value = "/selectByCompanyId/{companyId}", method = RequestMethod.GET)
	public Response selectByCompanyId(
			@RequestParam(name = "pageCount", defaultValue = "10") Integer pageCount,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@PathVariable(name = "companyId",required = true) String companyId){

		PageInfo<ComReviewReport> comReviewReport = comReviewReportService.selectByCompanyId(pageCount,pageNum,companyId);
		return Response.success(comReviewReport);

	} 



	/**
	 * 通过companyId  企业ID 初始化企业评审报告信息
	 * 企业审核项目信息，并向前端返回创建的报告信息编号
	 * @param companyId 企业ID
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response add(@RequestParam(name="companyId",required = true) String companyId){
		try {

			String reviewReportId=comReviewReportService.insertComReviewReport(companyId);
			return Response.success(reviewReportId);
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 * 修改企业评审报告信息
	 * @param comReviewReport
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Response update(@RequestBody ComReviewReport comReviewReport){
		try {

			comReviewReportService.update(comReviewReport);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 *完善企业评审报告信息
	 * @param reviewReportId
	 * @return
	 */
	@RequestMapping(value = "/PerfectReport",method = RequestMethod.PUT)
	public Response perfectReport(@RequestParam(name="reviewReportId",required = true)String reviewReportId){
		try {

			comReviewReportService.perfectReport(reviewReportId);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	
}
