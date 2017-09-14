package com.gzjy.template.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.template.model.Template;
import com.gzjy.template.service.TemplateService;

@RestController
@RequestMapping(value = "v1/ahgz")
public class TemplateController {
	@Autowired
	TemplateService templateService;
	
	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public Response getTemplateByName(@RequestParam(required = true) String name) {		
		try {
			Template result = templateService.selectByName(name);
			return Response.success(result);
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	@RequestMapping(value = "/template", method = RequestMethod.POST)
	public Response createTemplate(@RequestBody Template record) {		
		try {
			record.setId(ShortUUID.getInstance().generateShortID());
			record.setCreatedAt(new Date());
			templateService.insert(record);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
}
