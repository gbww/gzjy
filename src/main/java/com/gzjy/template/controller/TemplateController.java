package com.gzjy.template.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.template.model.Template;
import com.gzjy.template.service.TemplateService;

@RestController
@RequestMapping(value = "/v1/ahgz")
public class TemplateController {
	
	@Autowired
	TemplateService templateService;	
	
	/**
	 * 根据名称获取模板信息
	 * @param name 模板名称
	 * @return Response
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
     * 修改模板
     * @param file 模板文件
     * @param id 模板编号
     * @param name 模板名称
     * @param description 模板描述
     * @param category 模板分类
     * @param type 模板类型
     * @param roleIdList 角色数组
     * @return Response
     */
	@Privileges(name = "TEMPLATE-UPDATE", scope = { 1 })
	@RequestMapping(value = "/template/{id}", method = RequestMethod.PUT)
	public Response updateTemplate(
	        @RequestParam("file") MultipartFile file,@PathVariable String id,
			@RequestParam String name, @RequestParam String description,
            @RequestParam String category,	@RequestParam Integer type,
            @RequestParam String roleIdList) {
	    if(type ==0 && !(file.getOriginalFilename().endsWith(".jasper"))){
            return Response.fail("jasper模板文件仅支持.jasper");
        }
        if(type ==1 && !(file.getOriginalFilename().endsWith(".cpt"))) {
            return Response.fail("帆软模板文件仅支持.cpt");
        }
		Template record = new Template();
		record.setId(id);
		record.setName(name);
		record.setDescription(description);
		record.setCategory(category);
		record.setType(type);
		try {
			templateService.ModifyTemplateFile(file, record, roleIdList);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 删除模板表数据记录
	 * @param id 模板编号
	 * @return Response
	 */
	@RequestMapping(value = "/template/{id}", method = RequestMethod.DELETE)
	@Privileges(name = "TEMPLATE-DELETE", scope = { 1 })
	public Response deleteTemplate(@PathVariable String id) {		
		try {			
			templateService.deleteByPrimaryKey(id);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	

    /**
     * 上传模板文件
     * @param file 模板文件
     * @param name 模板名称
     * @param description 模板描述
     * @param category 模板分类
     * @param type 模板类型（0-Jasper模板 1-帆软模板）
     * @param roleIdList 角色编号（1l2;456;789）
     * @return Response
     */
	@RequestMapping(value = "/template/upload", method = RequestMethod.POST)
	@Privileges(name = "TEMPLATE-UPLOAD", scope = { 1 })
	public Response uploadTemplate(
			@RequestParam("file") MultipartFile file, @RequestParam String name,
			@RequestParam String description, @RequestParam String category,
			@RequestParam Integer type,@RequestParam(required = false) String roleIdList
	) {
        if(type ==0 && !(file.getOriginalFilename().endsWith(".jasper"))){
            return Response.fail("jasper模板文件仅支持.jasper");
        }
        if(type ==1 && !(file.getOriginalFilename().endsWith(".cpt"))) {
            return Response.fail("帆软模板文件仅支持.cpt");
        }
		if(type ==1 && (roleIdList==null||"".equals(roleIdList))){
            return Response.fail("上传帆软模板文件需绑定对应的角色");
        }
		try {
			templateService.uploadFile(file, name, description, category, type, roleIdList);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 获取模板列表
	 * @param type 模板类型
	 * @param category 模板分类
	 * @return Response
	 */
	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	@Privileges(name = "TEMPLATE-SELECT", scope = { 1 })
	public Response getTemplateList(
			@RequestParam(required = false) String name,			
		    @RequestParam(required = false,defaultValue="1") Integer pageNum,
		    @RequestParam(required = false,defaultValue="10") Integer pageSize,
		    @RequestParam(required = false) String type,
		    @RequestParam(required = false) String category)
	{
		try {
			PageInfo<Template> result = templateService.getPageList(pageNum, pageSize, name, type, category);
			return Response.success(result);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 获取模板类别列表
	 * @param category 类别
	 * @return Response
	 */
	@RequestMapping(value = "/template/types", method = RequestMethod.GET)
	@Privileges(name = "TEMPLATE-TYPE-SELECT", scope = { 1 })
	public Response getTypeList(@RequestParam(required = true) String category) {
		try {
			ArrayList<String> data = templateService.selectTypeByCategory(category);
			return Response.success(data);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
}
