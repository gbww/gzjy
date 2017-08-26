package com.gzjy.checkitems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.checkitems.model.CheckItemsCatalog;
import com.gzjy.checkitems.service.CheckItemsCatalogService;
import com.gzjy.common.Response;
import com.gzjy.common.util.UUID;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class CheckItemsController {
	
	@Autowired
	CheckItemsCatalogService checkItemsCatalogService;
	
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
				||record.getProductName()==null) {
			return Response.fail("参数不正确");
		}
		try {
			record.setId(UUID.random());
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
	@RequestMapping(value = "/checkitemscatalog/{parentId}/children", method = RequestMethod.GET)
	public Response getCheckItemsCatalogChildren(@PathVariable String parentId) {
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
}
