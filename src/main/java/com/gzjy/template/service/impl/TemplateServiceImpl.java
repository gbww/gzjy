package com.gzjy.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.template.mapper.TemplateMapper;
import com.gzjy.template.model.Template;
import com.gzjy.template.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateMapper templateMapper;
	
	public Template selectByName(String name) {
		return templateMapper.selectByName(name);
	}

	public int insert(Template record) {
		return templateMapper.insert(record);
	}
	
}
