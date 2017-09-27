package com.gzjy.template.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.template.model.Template;

public interface TemplateService {
	Template selectByName(String name);
	int insert(Template record); 
	int updateByPrimaryKeySelective(Template record);
	void uploadFile(MultipartFile file, String type, String name, String description,String category);
	PageInfo<Template> getPageList(Integer pageNum, Integer pageSize, String name, String type, String category);
	ArrayList<String> selectTypeByCagegory(String category);
}
