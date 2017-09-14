package com.gzjy.template.service;

import com.gzjy.template.model.Template;

public interface TemplateService {
	Template selectByName(String name);
	int insert(Template record); 
}
