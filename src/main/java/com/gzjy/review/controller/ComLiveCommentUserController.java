package com.gzjy.review.controller;

<<<<<<< Updated upstream
import java.util.List;

=======
import com.github.pagehelper.PageInfo;

import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComLiveCommentUser;
import com.gzjy.review.service.ComLiveCommentUserService;
>>>>>>> Stashed changes
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
import com.gzjy.review.modle.ComLiveCommentUser;
import com.gzjy.review.service.ComLiveCommentUserService;


/**
 * @Description: 参与评审人员信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */


@RestController
@RequestMapping({ "/v1/ahgz/company/commentuser" })
public class ComLiveCommentUserController {
	
	
	

<<<<<<< Updated upstream
	private static final Logger LOGGER = LoggerFactory.getLogger(ComLiveCommentUserController.class);

	@Autowired
	private ComLiveCommentUserService comLiveCommentUserService;

	/**
	 *  根据报告编号查询人员信息
	 * @param reviewReportId 报告编号
	 * @return
	 */
	@RequestMapping(value = "/selectByReviewReportId/{reviewReportId}", method = RequestMethod.GET)
	public Response selectByreviewReportId(@PathVariable(name = "reviewReportId",required = true) String reviewReportId){

		try {
			List<ComLiveCommentUser> item= comLiveCommentUserService.selectByReviewReportId(reviewReportId);
			return Response.success(item);
=======
    private static final Logger LOGGER = LoggerFactory.getLogger(ComLiveCommentUserController.class);

    @Autowired
    private ComLiveCommentUserService comLiveCommentUserService;

    /**
     * 分页查询参与评审人员信息
     * @param pageCount
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/select/infors", method = RequestMethod.GET)
    public Response selectByPages(
            @RequestParam(name = "pageCount", defaultValue = "10") Integer pageCount,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum){
    	try {	
			PageInfo<ComLiveCommentUser> result = comLiveCommentUserService.selectALL(pageNum, pageCount);
			return Response.success(result);
>>>>>>> Stashed changes
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
<<<<<<< Updated upstream
	}



	/**
	 * 添加参与评审人员信息
	 * @param comLiveCommentUser
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response add(@RequestBody ComLiveCommentUser comLiveCommentUser){
		try {

			comLiveCommentUserService.insertUser(comLiveCommentUser);
			return Response.success("success");
=======


       
    }

    /**
     *  根据主键查询参与评审人员信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public Response selectByPrimaryKey(@PathVariable(name = "id",required = true) String id){
    	try {
    		ComLiveCommentUser itme=comLiveCommentUserService.selectByPrimaryKey(id);
            return Response.success(itme);
>>>>>>> Stashed changes
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
<<<<<<< Updated upstream
	}
	/**
	 * 批量添加参与评审人员信息
	 * @param comLiveCommentUser
	 * @return
	 */
	@RequestMapping(value = "/add/batch", method = RequestMethod.POST)
	public Response batchAdd(@RequestBody List<ComLiveCommentUser> comLiveCommentUser){
		try {


			comLiveCommentUserService.batchInsertUser(comLiveCommentUser);
=======
    	
    }

    /**
     * 添加参与评审人员信息
     * @param comLiveCommentUser
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody ComLiveCommentUser comLiveCommentUser){
    	try {
    		comLiveCommentUser.setId(ShortUUID.getInstance().generateShortID());
    		comLiveCommentUserService.insertreviewers(comLiveCommentUser);
>>>>>>> Stashed changes
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
<<<<<<< Updated upstream
	}





	/**
	 * 修改参与评审人员信息
	 * @param comLiveCommentUser
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Response update(@RequestBody ComLiveCommentUser comLiveCommentUser){
		try {		
			comLiveCommentUserService.update(comLiveCommentUser);
			return Response.success("success");
		}
		catch (Exception e) {			
			throw new BizException(e.toString());
		}
	}

	/**
	 * 批量删除参与评审人员信息
	 * @param ids 主键
	 * @return
	 */
	@RequestMapping(value = "/batchDelete",method = RequestMethod.DELETE)
	public Response batchDelete(@RequestBody List<String> ids){
		try {	
			comLiveCommentUserService.batchDeleteByIds(ids);
			return Response.success("success");	

		}
		catch (Exception e) {			
			throw new BizException(e.toString());
		}
	}
	
	/**
	 * 根据主键删除参与评审人员信息
	 * @param ids 主键
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
	public Response delete(@PathVariable(name="id",required=true) String id){
		try {	
			comLiveCommentUserService.batchDeleteById(id);
			return Response.success("success");	

		}
		catch (Exception e) {			
			throw new BizException(e.toString());
		}
	}
=======
    }

    /**
     * 修改参与评审人员信息
     * @param comLiveCommentUser
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Response update(@RequestBody ComLiveCommentUser comLiveCommentUser){
    	try {
    		comLiveCommentUserService.updatereviewer(comLiveCommentUser);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
    }

    /**
     * 根据主键删除参与评审人员信息
     * @param ids 主键
     * @return
     */
    @RequestMapping(value = "/delete")
    public Response delete(@RequestBody List<String> ids){
    	try {
    		for (String id : ids) {
    			comLiveCommentUserService.deletereviewer(id);
			}
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
    }
>>>>>>> Stashed changes
}
