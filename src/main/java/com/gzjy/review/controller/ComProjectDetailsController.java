package com.gzjy.review.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< Updated upstream

import com.gzjy.common.Response;
import com.gzjy.review.modle.ComProjectDetails;
import com.gzjy.review.service.ComProjectDetailsService;
=======
>>>>>>> Stashed changes

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComProjectDetails;
import com.gzjy.review.service.ComProjectDetailsService;

/**
 * @Description: 企业评审项目明细信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/project/detail" })
public class ComProjectDetailsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComProjectDetailsController.class);
<<<<<<< Updated upstream

	@Autowired
	private ComProjectDetailsService comProjectDetailsService;

	
	/**
	 *  根据企业类型查询企业评审项目明细信息
	 * @param comType 企业类型 
	 * @return
	 */
	@RequestMapping(value = "/selectByComType", method = RequestMethod.GET)
	public Response selectByComType( @RequestParam(name="comType",required = true) String comType){

		List<ComProjectDetails> comProjectDetails = comProjectDetailsService.selectByComType(comType);
		return Response.success(comProjectDetails);

	}

	
=======

	@Autowired
	private ComProjectDetailsService comProjectDetailsService;

	/**
	 * 分页查询企业评审项目明细信息
	 * 
	 * @param pageCount
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/select/infors", method = RequestMethod.GET)
	public Response selectByPages(
			@RequestParam(name = "pageCount", defaultValue = "10") Integer pageCount,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
		try {
			PageInfo<ComProjectDetails> result = comProjectDetailsService.selectALL(pageNum, pageCount);
			return Response.success(result);
		} catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 * 根据主键查询企业评审项目明细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
	public Response selectByPrimaryKey(@PathVariable(name = "id", required = true) String id) {
		try {
			ComProjectDetails itme = comProjectDetailsService.selectByPrimaryKey(id);
			return Response.success(itme);
		} catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 * 添加企业评审项目明细信息
	 * 
	 * @param comProjectDetails
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response add(@RequestBody ComProjectDetails comProjectDetails) {
		try {
			comProjectDetails.setId(ShortUUID.getInstance().generateShortID());
			comProjectDetailsService.insertreviewers(comProjectDetails);
			return Response.success("success");
		} catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 * 修改企业评审项目明细信息
	 * 
	 * @param comProjectDetails
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Response update(@RequestBody ComProjectDetails comProjectDetails) {
		try {
			comProjectDetailsService.updatereviewer(comProjectDetails);
			return Response.success("success");
		} catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 * 根据主键删除企业评审项目明细信息
	 * 
	 * @param ids
	 *            主键
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public Response delete(@RequestBody List<String> ids) {
		try {
			for (String id : ids) {
				comProjectDetailsService.deleteByPrimaryKey(id);
			}

			return Response.success("success");
		} catch (Exception e) {
			throw new BizException(e.toString());
		}
	}
>>>>>>> Stashed changes
}
