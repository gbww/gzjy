package com.gzjy.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.log.model.LogModel;
import com.gzjy.log.model.LogTag;

@Mapper
public interface LogTagMapper {

    int insert(LogTag record);
    
    List<LogTag> selectAll();   
   
}