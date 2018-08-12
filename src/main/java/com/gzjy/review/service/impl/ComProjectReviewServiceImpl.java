package com.gzjy.review.service.impl;

<<<<<<< Updated upstream
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream



	@Override
	public List<ComProjectReview> selectByReviewReportId(String reviewReportId) {

		return comProjectReviewMapper.selectByReviewReportId(reviewReportId);
	}




	@Override
	public int batchupdate(List<ComProjectReview> comProjectReview) {

		return comProjectReviewMapper.batchupdate(comProjectReview);
=======
	@Override
	public PageInfo<ComProjectReview> selectALL(Integer pageNum, Integer pageCount) {
		List<ComProjectReview> list = new ArrayList<ComProjectReview>();
		PageInfo<ComProjectReview> pages = new PageInfo<ComProjectReview>(list);
		pages = PageHelper.startPage(pageNum, pageCount).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				comProjectReviewMapper.selectAll();
			}
		});
		return pages;
	}

	@Override
	public ComProjectReview selectByPrimaryKey(String id) {
		return comProjectReviewMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertreviewers(ComProjectReview comProjectReview) {

		return comProjectReviewMapper.insertSelective(comProjectReview);
	}

	@Override
	public int updatereviewer(ComProjectReview comProjectReview) {

		return comProjectReviewMapper.updateByPrimaryKeySelective(comProjectReview);
	}

	@Override
	public int deletereviewer(String id) {
		// TODO Auto-generated method stub
		return 0;
>>>>>>> Stashed changes
	}
}
