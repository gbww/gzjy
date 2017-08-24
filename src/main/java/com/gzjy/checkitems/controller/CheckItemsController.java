package com.gzjy.checkitems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.checkitems.model.CheckItems;
import com.gzjy.checkitems.service.CheckItemsService;
import com.gzjy.common.Response;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class CheckItemsController {
	
	@Autowired
	CheckItemsService checkItemsService;
	
	@RequestMapping(value = "/checkitems", method = RequestMethod.GET)
	public Response createContract(@RequestParam(required = true) String id) {
		try {
			CheckItems item= checkItemsService.selectByPrimaryKey(id);
			return Response.success(item);
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.fail(e.getMessage());
		}
	}
}
