package com.gzjy.review.service;

import com.github.pagehelper.PageInfo;
import com.gzjy.review.modle.ComReviewReport;

/**
 * @Description: 企业评审报告服务层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:47
 */
public interface ComReviewReportService {

	String insertComReviewReport(ComReviewReport comReviewReport);

	int update(ComReviewReport comReviewReport);

	PageInfo<ComReviewReport> selectByCompanyId(Integer pageCount, Integer pageNum, String companyId);

	ComReviewReport selectByPrimaryKey(String reviewReportId);

	int perfectReport(String reviewReportId);
}
