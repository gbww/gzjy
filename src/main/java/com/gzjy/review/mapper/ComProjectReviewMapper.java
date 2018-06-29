package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComProjectReview;

public interface ComProjectReviewMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComProjectReview record);

    int insertSelective(ComProjectReview record);

    ComProjectReview selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComProjectReview record);

    int updateByPrimaryKey(ComProjectReview record);
}