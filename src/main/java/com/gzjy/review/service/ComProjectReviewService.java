package com.gzjy.review.service;

import java.util.List;

import com.gzjy.review.modle.ComProjectReview;

/**
 * @Description: 企业评审信息服务层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:44
 */
public interface ComProjectReviewService {



	List<ComProjectReview> selectByReviewReportId(String reviewReportId);

	int batchupdate(List<ComProjectReview> comProjectReview);
}
