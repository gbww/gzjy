package com.gzjy.template.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.template.model.FineReportTemplateRoleMappingModel;;

@Mapper
public interface FineReportTemplateRoleMappingMapper {
	FineReportTemplateRoleMappingModel selectByName(@Param("name")String name);
	FineReportTemplateRoleMappingModel selectByRoleId(@Param("roleId")String roleId);
	int insert(FineReportTemplateRoleMappingModel record);
	int updateByPrimaryKeySelective(FineReportTemplateRoleMappingModel record);
	int deleteByPrimaryKey(@Param("id")String id);
	FineReportTemplateRoleMappingModel selectById(@Param("id")String id);
	List<FineReportTemplateRoleMappingModel> selectAll(@Param("name")String name,@Param("type")String type, @Param("category")String category);
	ArrayList<String> selectTypeByCagegory(@Param("category")String category);
}
