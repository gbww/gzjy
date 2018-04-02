package com.gzjy.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.user.model.UserType;
@Mapper
public interface UserTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserType record);

    int insertSelective(UserType record);

    UserType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserType record);

    int updateByPrimaryKey(UserType record);
    List<UserType >selectAll(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}