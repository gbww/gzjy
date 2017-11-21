package com.gzjy.log.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.gzjy.log.model.LogModel;

@Mapper
public interface LogMapper {

    int insert(LogModel record);
   
}