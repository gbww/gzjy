package com.gzjy.review.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComInfor;
import com.gzjy.review.service.ComInforService;


/**
 * @Description: 企业信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/info" })
public class ComInforController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComInforController.class);

	@Autowired
	private ComInforService comInforService;

	/**
	 * 分页查询企业信息
	 * @param pageCount
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/select/cominfors", method = RequestMethod.GET)
	public Response selectByPages(
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

		PageInfo<ComInfor> comInfors = comInforService.selectByPages(pageNum,pageSize);
		return Response.success(comInfors);
	}

	/**
	 *  根据主键查询企业信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
	public Response selectByPrimaryKey(@PathVariable(name = "id",required = true) String id){
		ComInfor comInfor = comInforService.selectByPrimaryKey(id);
		return Response.success(comInfor);
	}

	/**
	 * 添加企业信息
	 * @param comInfor
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response add(@RequestBody ComInfor comInfor){

		int n = comInforService.addComInfor(comInfor);

		return Response.success(n);
	}

	/**
	 * 修改企业信息
	 * @param comInfor
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Response update(@RequestBody ComInfor comInfor){
		int n = comInforService.updateComInfor(comInfor);

		return  Response.success(n);
	}

	/**
	 * 根据主键删除企业信息
	 * @param ids 主键
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.DELETE)
	public Response delete(@RequestBody List<String> ids){

		if (ids == null || ids.size() == 0)
			throw new BizException("企业唯一标识不能为空");

		int n = comInforService.deleteCominfors(ids);
		return Response.success(n);
	}
}
