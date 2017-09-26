package com.gzjy.template.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.checkitems.model.CheckItem;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.template.model.Template;
import com.gzjy.template.service.TemplateService;

@RestController
@RequestMapping(value = "/v1/ahgz")
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
	 * 修改模板表数据记录
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/template", method = RequestMethod.PUT)
	public Response updateTemplate(@RequestBody Template record) {		
		try {			
			templateService.updateByPrimaryKeySelective(record);
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
	public Response uploadTemplate(@RequestParam("file") MultipartFile file, @RequestParam String type,
			@RequestParam String name, @RequestParam String description) {
		try {			
			templateService.uploadFile(file, type, name, description);			
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 获取模板列表
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public Response getCheckItemList(
			@RequestParam(required = false) String name,			
		    @RequestParam(required = false,defaultValue="1") Integer pageNum,
		    @RequestParam(required = false,defaultValue="10") Integer pageSize,
		    @RequestParam(required = false) String type,
		    @RequestParam(required = false) String category) {
		try {	
			PageInfo<Template> result = templateService.getPageList(pageNum, pageSize, name, type, category);
			return Response.success(result);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
}
