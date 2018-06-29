package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComProjectDetails;

public interface ComProjectDetailsMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComProjectDetails record);

    int insertSelective(ComProjectDetails record);

    ComProjectDetails selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComProjectDetails record);

    int updateByPrimaryKey(ComProjectDetails record);
}