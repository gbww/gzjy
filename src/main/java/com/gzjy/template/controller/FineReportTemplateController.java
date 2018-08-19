package com.gzjy.template.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.template.model.FineReportTemplateModel;
import com.gzjy.template.service.FineReportTemplateService;;

@RestController
@RequestMapping(value = "/v1/ahgz/template")
public class FineReportTemplateController {
	
		@Autowired
		FineReportTemplateService fineReportTemplateService;
		
		/**
		 * 根据名称获取模板信息
		 * @param name
		 * @return
		 */
		@RequestMapping(value = "/fr", method = RequestMethod.GET)
		public Response getTemplateByName(@RequestParam(required = true) String name) {		
			try {
				FineReportTemplateModel result = fineReportTemplateService.selectByName(name);
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
		@RequestMapping(value = "/fr", method = RequestMethod.POST)
		public Response createFineReportTemplate(@RequestBody FineReportTemplateModel record) {		
			try {
				record.setId(ShortUUID.getInstance().generateShortID());
				record.setCreatedAt(new Date());
				fineReportTemplateService.insert(record);
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
		@Privileges(name = "TEMPLATE-UPDATE", scope = { 1 })
		@RequestMapping(value = "/fr/{id}", method = RequestMethod.PUT)
		public Response updateFineReportTemplate(@RequestParam("file") MultipartFile file,@PathVariable String id,
				@RequestParam String name, @RequestParam String description, @RequestParam String category) {
			if(!file.getOriginalFilename().endsWith(".cpt")) {
				return Response.fail("模板文件仅支持.cpt后缀文件");
			}
			FineReportTemplateModel record = new FineReportTemplateModel();
			record.setId(id);
			record.setName(name);
			record.setDescription(description);
			record.setCategory(category);
			record.setFileName(file.getOriginalFilename());
			try {			
				fineReportTemplateService.ModifyTemplateFile(file, record);			
				return Response.success("success");
			}
			catch (Exception e) {
				return Response.fail(e.getMessage());
			}
		}
		
		/**
		 * 删除模板表数据记录
		 * @param record
		 * @return
		 */
		@RequestMapping(value = "/fr/{id}", method = RequestMethod.DELETE)
		@Privileges(name = "TEMPLATE-DELETE", scope = { 1 })
		public Response deleteTemplate(@PathVariable String id) {		
			try {			
				fineReportTemplateService.deleteByPrimaryKey(id);
				return Response.success("success");
			}
			catch (Exception e) {
				return Response.fail(e.getMessage());
			}
		}
		
		
		/**
		 * 上传帆软模板文件
		 * @param file
		 * @return
		 */
		@RequestMapping(value = "/fr/upload", method = RequestMethod.POST)
		@Privileges(name = "TEMPLATE-UPLOAD", scope = { 1 })
		public Response uploadTemplate(@RequestParam("file") MultipartFile file, 
				@RequestParam String name, @RequestParam String description, @RequestParam String category,@RequestParam String roleId) {
			if(!file.getOriginalFilename().endsWith(".cpt")) {
				return Response.fail("模板文件仅支持.cpt后缀文件");
			}
			try {
				fineReportTemplateService.uploadFile(file, name, description, category, roleId);
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
		@RequestMapping(value = "/frs", method = RequestMethod.GET)
		@Privileges(name = "TEMPLATE-SELECT", scope = { 1 })
		public Response getTemplateList(
				@RequestParam(required = false) String name,			
			    @RequestParam(required = false,defaultValue="1") Integer pageNum,
			    @RequestParam(required = false,defaultValue="10") Integer pageSize,
			    @RequestParam(required = false) String type,
			    @RequestParam(required = false) String category) {
			try {
				PageInfo<FineReportTemplateModel> result = fineReportTemplateService.getPageList(pageNum, pageSize, name, type, category);
				return Response.success(result);
			}
			catch (Exception e) {
				System.out.println(e);
				return Response.fail(e.getMessage());
			}
		}
}
