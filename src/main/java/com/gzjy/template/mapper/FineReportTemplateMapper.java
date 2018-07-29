package com.gzjy.template.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.template.model.FineReportTemplateModel;

@Mapper
public interface FineReportTemplateMapper {
	FineReportTemplateModel selectByName(@Param("name")String name);
	int insert(FineReportTemplateModel record);
	int updateByPrimaryKeySelective(FineReportTemplateModel record);
	int deleteByPrimaryKey(@Param("id")String id);
	FineReportTemplateModel selectById(@Param("id")String id);
	List<FineReportTemplateModel> selectAll(@Param("name")String name,@Param("type")String type, @Param("category")String category);
	ArrayList<String> selectTypeByCagegory(@Param("category")String category);
}
