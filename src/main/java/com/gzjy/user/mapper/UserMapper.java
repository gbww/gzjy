package com.gzjy.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gzjy.user.model.User;
@Mapper
public interface UserMapper {
   
   // @Select("select * from user where userName = #{name}")
  User selectByPrimaryKey(@Param("name")String name);
    
}