package com.gzjy.template.service;

import org.springframework.web.multipart.MultipartFile;

import com.gzjy.template.model.Template;

public interface TemplateService {
	Template selectByName(String name);
	int insert(Template record); 
	void uploadFile(MultipartFile file);
}