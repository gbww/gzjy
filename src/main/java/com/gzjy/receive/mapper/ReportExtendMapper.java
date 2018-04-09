package com.gzjy.receive.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.receive.model.ReportExtend;
@Mapper
public interface ReportExtendMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(ReportExtend record);    

    ReportExtend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReportExtend record);

    List<ReportExtend> selectAll();
    
    ReportExtend selectByReportId(String reportId);
}