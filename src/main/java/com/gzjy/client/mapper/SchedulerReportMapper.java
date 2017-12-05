package com.gzjy.client.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.client.model.ClientScheduler;
import com.gzjy.client.model.SchedulerReport;
@Mapper
public interface SchedulerReportMapper {
    int deleteByPrimaryKey(String id);
    int deleteBySchedulerId(String schedulerId);

    int insert(SchedulerReport record);

    int insertSelective(SchedulerReport record);

    SchedulerReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SchedulerReport record);

    int updateByPrimaryKey(SchedulerReport record);
    
    List<SchedulerReport> selectBySchedulerId(@Param("schedulerId")String schedulerId,@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}