package com.gzjy.review.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.review.mapper.ComProjectReviewMapper;
import com.gzjy.review.modle.ComProjectReview;
import com.gzjy.review.service.ComProjectReviewService;

/**
 * @Description: 企业评审信息服务层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:44
 */
@Service
public class ComProjectReviewServiceImpl implements ComProjectReviewService {

	@Autowired
	private ComProjectReviewMapper comProjectReviewMapper;




	@Override
	public List<ComProjectReview> selectByReviewReportId(String reviewReportId) {

		return comProjectReviewMapper.selectByReviewReportId(reviewReportId);
	}




	@Override
	public int batchupdate(List<ComProjectReview> comProjectReview) {

		return comProjectReviewMapper.batchupdate(comProjectReview);
	}
}
