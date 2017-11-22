package com.gzjy.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.log.model.LogModel;

@Mapper
public interface LogMapper {

    int insert(LogModel record);
    
    List<LogModel> selectAll(LogModel record);
   
    int deleteByCreateTime(@Param("createTime")String createTime);
}