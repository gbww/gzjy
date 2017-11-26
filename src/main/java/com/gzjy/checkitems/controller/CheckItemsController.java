package com.gzjy.checkitems.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.checkitems.model.CheckItem;
import com.gzjy.checkitems.model.CheckItemsCatalog;
import com.gzjy.checkitems.model.CheckItemsCatalogMapping;
import com.gzjy.checkitems.service.CheckItemService;
import com.gzjy.checkitems.service.CheckItemsCatalogMappingService;
import com.gzjy.checkitems.service.CheckItemsCatalogService;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class CheckItemsController {
	
	@Autowired
	CheckItemsCatalogService checkItemsCatalogService;
	
	@Autowired
	CheckItemService checkItemService;
	
	@Autowired
	CheckItemsCatalogMappingService checkItemsCatalogMappingService;
	
	/**
	 * 根据ID获取当前目录
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/{id}", method = RequestMethod.GET)
	public Response getCheckItemsCatalog(@PathVariable String id) {
		try {
			CheckItemsCatalog item= checkItemsCatalogService.selectByPrimaryKey(id);
			return Response.success(item);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 添加检验项目录
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog", method = RequestMethod.POST)
	public Response createCheckItemsCatalog(@RequestBody CheckItemsCatalog record) {
		if(record.getParentId() == null || record.getProductId() == null 
				||record.getProductName()==null ||record.getIsCatalog()==null) {
			return Response.fail("参数不正确");
		}
		try {
			record.setId(ShortUUID.getInstance().generateShortID());
			checkItemsCatalogService.createCheckItemsCatalog(record);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 根据ID获取当前目录子目录
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/children", method = RequestMethod.GET)
	public Response getCheckItemsCatalogChildren(@RequestParam(required = true) String parentId) {
		try {
			List<CheckItemsCatalog> result = checkItemsCatalogService.selectByParentId(parentId);
			return Response.success(result);
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 修改检验项目录
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog", method = RequestMethod.PUT)
	public Response updateCheckItemsCatalog(@RequestBody CheckItemsCatalog record) {
		try {
			checkItemsCatalogService.updateByPrimaryKeySelective(record);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 根据ID递归删除当前目录
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/{id}", method = RequestMethod.DELETE)
	public Response deleteCheckItemsCatalog(@PathVariable String id) {
		try {
			checkItemsCatalogService.deleteCheckItemsCatalog(id);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 检验项的录入
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item", method = RequestMethod.POST)
	public Response createCheckItem(@RequestBody CheckItem record) {
		try {
			record.setId(ShortUUID.getInstance().generateShortID());
			record.setCreatedAt(new Date());
			checkItemService.insert(record);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 获取检验项列表
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item", method = RequestMethod.GET)
	public Response getCheckItemList(
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) String method,
		    @RequestParam(required = false,defaultValue="1") Integer pageNum,
		    @RequestParam(required = false,defaultValue="10") Integer pageSize,
		    @RequestParam(required = false, name = "searchValue") String search) {
		try {	
			PageInfo<CheckItem> result = checkItemService.getPageList(pageNum, pageSize, name, method);
			return Response.success(result);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 检验项的修改
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/{id}", method = RequestMethod.PUT)
	public Response updateCheckItem(@RequestBody CheckItem record, @PathVariable String id) {
		try {
			record.setId(id);
			record.setUpdatedAt(new Date());
			checkItemService.updateByPrimaryKeySelective(record);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 检验项的删除
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/{id}", method = RequestMethod.DELETE)
	public Response deleteCheckItem(@PathVariable String id) {
		try {
			checkItemService.deleteByPrimaryKey(id);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 检验项的检验
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/check", method = RequestMethod.POST)
	public Response validateCheckItem(@RequestBody CheckItem checkItem) {
		try {
			boolean result=checkItemService.validateCheckItem(checkItem);
			return Response.success(result);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	
	/**
	 * 检验项的查询
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/{id}", method = RequestMethod.GET)
	public Response getCheckItem(@PathVariable String id) {
		try {
			CheckItem item = checkItemService.selectByPrimaryKey(id);
			return Response.success(item);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 获取检验项列表树
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/{catalogId}/tree", method = RequestMethod.GET)
	public Response getCheckItemListById(@PathVariable String catalogId) {
		try {	
			List<HashMap<String, Object>> result = checkItemsCatalogMappingService.selectCheckItemsById(catalogId);
			return Response.success(result);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 插入数据到检验项关系表中
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/mapping", method = RequestMethod.POST)
	public Response createCheckItemMapping(@RequestBody CheckItemsCatalogMapping record) {
		if(record.getCatalogId() ==null || record.getCheckItemId()==null) {
			return Response.fail("Param Invalid");
		}
		try {
			record.setId(ShortUUID.getInstance().generateShortID());
			checkItemsCatalogMappingService.insert(record);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 批量插入数据到检验项关系表中
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/mapping/list", method = RequestMethod.POST)
	public Response getCheckItemListById(@RequestBody List<HashMap<String, String>> records) {
		if(records ==null || records.size()==0) {			
			return Response.fail("Param Invalid");
		}
		try {
			for(HashMap<String, String> record: records) {
				CheckItemsCatalogMapping model = new CheckItemsCatalogMapping();				
				model.setId(ShortUUID.getInstance().generateShortID());
				model.setCatalogId(record.get("catalogId"));
				model.setCheckItemId(record.get("checkItemId"));
				model.setLaboratory(record.get("laboratory"));
				checkItemsCatalogMappingService.insert(model);
			}			
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 删除检验项关系表中数据
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/item/mapping/{id}", method = RequestMethod.DELETE)
	public Response deleteCheckItemListById(@PathVariable String id) {		
		try {
			checkItemsCatalogMappingService.deleteByPrimaryKey(id);
			return Response.success("success");
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
	
	/**
	 * 上传模板文件
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/checkitemscatalog/import", method = RequestMethod.POST)
	public Response multiImport(@RequestParam("file") MultipartFile file) {
		try {
			checkItemService.importFile(file);
			return Response.success("success");
		}
		catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}
}
