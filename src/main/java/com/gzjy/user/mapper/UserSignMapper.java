package com.gzjy.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.user.model.UserSign;
@Mapper
public interface UserSignMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserSign record);

    int insertSelective(UserSign record);

    UserSign selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserSign record);

    int updateByPrimaryKey(UserSign record);
    
    List<HashMap<String, String>> getSignList(@Param("userIdList") ArrayList<String> userIdList);
}