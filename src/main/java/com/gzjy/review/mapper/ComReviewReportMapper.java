package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComReviewReport;

public interface ComReviewReportMapper {
    int deleteByPrimaryKey(String reviewReportId);

    int insert(ComReviewReport record);

    int insertSelective(ComReviewReport record);

    ComReviewReport selectByPrimaryKey(String reviewReportId);

    int updateByPrimaryKeySelective(ComReviewReport record);

    int updateByPrimaryKey(ComReviewReport record);
}