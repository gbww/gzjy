package com.gzjy.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.user.model.UserSign;
@Mapper
public interface UserSignMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserSign record);

    int insertSelective(UserSign record);

    UserSign selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserSign record);

    int updateByPrimaryKey(UserSign record);
}