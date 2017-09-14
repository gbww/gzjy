package com.gzjy.template.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.template.model.Template;
import com.gzjy.template.service.TemplateService;

@RestController
@RequestMapping(value = "v1/ahgz")
public class TemplateController {
	
	@Autowired
	TemplateService templateService;	
	
	/**
	 * 根据名称获取模板信息
	 * @param name
	 * @return
	 */
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
	
	/**
	 * 添加模板表数据记录
	 * @param record
	 * @return
	 */
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
	
	/**
	 * 上传模板文件
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/template/upload", method = RequestMethod.POST)
	public Response uploadTemplate(@RequestParam("file") MultipartFile file) {
		try {
			templateService.uploadFile(file);
			Template record=new Template();
			record.setId(ShortUUID.getInstance().generateShortID());
			record.setName(file.getName());
			record.setCreatedAt(new Date());
			templateService.insert(record);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
}
