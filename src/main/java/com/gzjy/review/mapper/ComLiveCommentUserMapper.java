package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComLiveCommentUser;

public interface ComLiveCommentUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComLiveCommentUser record);

    int insertSelective(ComLiveCommentUser record);

    ComLiveCommentUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComLiveCommentUser record);

    int updateByPrimaryKey(ComLiveCommentUser record);
}