package com.gzjy.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.review.modle.ComReviewReport;
@Mapper
public interface ComReviewReportMapper {
	int deleteByPrimaryKey(String reviewReportId);
	
	int deleteByCompanyId(String companyId);

	int insert(ComReviewReport record);

	int insertSelective(ComReviewReport record);

	ComReviewReport selectByPrimaryKey(String reviewReportId);

	int updateByPrimaryKeySelective(ComReviewReport record);

	int updateByPrimaryKey(ComReviewReport record);

	List<ComReviewReport> selectByCompanyId(String companyId);
}