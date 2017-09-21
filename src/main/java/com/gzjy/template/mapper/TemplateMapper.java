package com.gzjy.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.template.model.Template;

@Mapper
public interface TemplateMapper {
	Template selectByName(@Param("name")String name);
	int insert(Template record);
	List<Template> selectAll(@Param("name")String name);
}
