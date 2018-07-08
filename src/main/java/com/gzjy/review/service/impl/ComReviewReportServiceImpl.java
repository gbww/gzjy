package com.gzjy.review.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.ShortUUID;
import com.gzjy.review.mapper.ComInforMapper;
import com.gzjy.review.mapper.ComProjectDetailsMapper;
import com.gzjy.review.mapper.ComProjectReviewMapper;
import com.gzjy.review.mapper.ComReviewReportMapper;
import com.gzjy.review.modle.ComInfor;
import com.gzjy.review.modle.ComProjectDetails;
import com.gzjy.review.modle.ComProjectReview;
import com.gzjy.review.modle.ComReviewReport;
import com.gzjy.review.service.ComReviewReportService;

/**
 * @Description: 企业评审报告
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:48
 */
@Service
public class ComReviewReportServiceImpl implements ComReviewReportService {


	@Autowired
	private ComReviewReportMapper comReviewReportMapper;

	@Autowired
	private ComInforMapper comInforMapper;
	@Autowired
	private ComProjectDetailsMapper comProjectDetailsMapper;
	@Autowired
	private ComProjectReviewMapper comProjectReviewMapper;

	@Override
	@Transactional
	public String insertComReviewReport(ComReviewReport comReviewReport) {
		//初始化报告表
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 comReviewReport.setCheckDate(new Date());
		comReviewReport.setReviewReportId(ShortUUID.getInstance().generateShortID());
		comReviewReportMapper.insertSelective(comReviewReport);
		//根据企业ID查询企业信息
		ComInfor comInfor=comInforMapper.selectByPrimaryKey(comReviewReport.getCompanyId());
		//根据企业类型查询审核明细
		List<ComProjectDetails>ComProjectDetails= comProjectDetailsMapper.selectByComType(comInfor.getComType());
		List<ComProjectReview> comProjectReview=new ArrayList<ComProjectReview>();
		for (ComProjectDetails comProjectDetails2 : ComProjectDetails) {
			ComProjectReview comProjectReview1 = new ComProjectReview();
			comProjectReview1.setId(ShortUUID.getInstance().generateShortID());
			comProjectReview1.setComType(comInfor.getComType());
			comProjectReview1.setCompanyId(comReviewReport.getCompanyId());
			comProjectReview1.setReviewReportId(comReviewReport.getReviewReportId());
			comProjectReview1.setProjectId(comProjectDetails2.getId());
			comProjectReview1.setProjectName(comProjectDetails2.getContent());
			comProjectReview.add(comProjectReview1);		
		}
		//初始化企业审核项目
		comProjectReviewMapper.batchInsertcomProjectReview(comProjectReview);

		return comReviewReport.getReviewReportId();
	}

	@Override
	public int update(ComReviewReport comReviewReport) {

		return comReviewReportMapper.updateByPrimaryKeySelective(comReviewReport);
	}

	@Override
	public PageInfo<ComReviewReport> selectByCompanyId(Integer pageCount, Integer pageNum, String companyId) {
		List<ComReviewReport> list = new ArrayList<ComReviewReport>();
		PageInfo<ComReviewReport> pages = new PageInfo<ComReviewReport>(list);
		pages = PageHelper.startPage(pageNum, pageCount).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				comReviewReportMapper.selectByCompanyId(companyId);
			}
		});
		return pages;
	}

	@Override
	public ComReviewReport selectByPrimaryKey(String reviewReportId) {

		return comReviewReportMapper.selectByPrimaryKey(reviewReportId);
	}

	@Override
	public int perfectReport(String reviewReportId) {

		//根据报告id查询报告信息
		ComReviewReport comReviewReport=comReviewReportMapper.selectByPrimaryKey(reviewReportId);

		//根据报告id查询审核信息
		List<ComProjectReview> comProjectReview=comProjectReviewMapper.selectByReviewReportId(reviewReportId);
		double score=0;
		//计算得分
		for (ComProjectReview comProjectReview2 : comProjectReview) {
			score=score+comProjectReview2.getScore();
		}
		comReviewReport.setScore(score);

		return comReviewReportMapper.updateByPrimaryKeySelective(comReviewReport);
	}
}
