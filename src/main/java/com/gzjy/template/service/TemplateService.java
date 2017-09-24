package com.gzjy.template.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.template.model.Template;

public interface TemplateService {
	Template selectByName(String name);
	int insert(Template record); 
	void uploadFile(MultipartFile file, String type, String name, String description);
	PageInfo<Template> getPageList(Integer pageNum, Integer pageSize, String name, String type);
}
