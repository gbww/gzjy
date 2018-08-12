package com.gzjy.review.controller;

<<<<<<< Updated upstream
import java.util.List;

=======
import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComLiveCommentUser;
import com.gzjy.review.modle.ComProjectReview;
import com.gzjy.review.service.ComProjectReviewService;
>>>>>>> Stashed changes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComProjectReview;
import com.gzjy.review.service.ComProjectReviewService;


/**
 * @Description: 企业评审信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/project/review" })
public class ComProjectReviewController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComProjectReviewController.class);

	@Autowired
	private ComProjectReviewService comProjectReviewService;

<<<<<<< Updated upstream
	/**
	 *  根据报告编码查询审核信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/select/{reviewReportId}", method = RequestMethod.GET)
	public Response selectByReviewReportId(@PathVariable(name = "reviewReportId",required = true) String reviewReportId){

		List <ComProjectReview> comProjectReview=comProjectReviewService.selectByReviewReportId(reviewReportId);

		return Response.success(comProjectReview);
	}
=======
    	try {	
			PageInfo<ComProjectReview> result = comProjectReviewService.selectALL(pageNum, pageCount);
			return Response.success(result);
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
    }
>>>>>>> Stashed changes

	/**
	 * 批量修改审核信息
	 * @param comProjectReview
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Response batchupdate(@RequestBody List<ComProjectReview> comProjectReview){

<<<<<<< Updated upstream
		try {
			comProjectReviewService.batchupdate(comProjectReview);
			return Response.success("success");
=======
    	try {
    		ComProjectReview itme=comProjectReviewService.selectByPrimaryKey(id);
            return Response.success(itme);
>>>>>>> Stashed changes
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
<<<<<<< Updated upstream

	}


=======
    }

    /**
     * 添加企业评审信息
     * @param comProjectReview
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody ComProjectReview comProjectReview){
    	try {
    		comProjectReview.setId(ShortUUID.getInstance().generateShortID());
    		comProjectReviewService.insertreviewers(comProjectReview);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
    }

    /**
     * 修改企业评审信息
     * @param comProjectReview
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Response update(@RequestBody ComProjectReview comProjectReview){
    	try {
    		comProjectReviewService.updatereviewer(comProjectReview);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
    }

    /**
     * 根据主键删除企业评审信息
     * @param ids 主键
     * @return
     */
    @RequestMapping(value = "/delete")
    public Response delete(@RequestBody List<String> ids){
    	try {
    		for (String id : ids) {
    			comProjectReviewService.deletereviewer(id);
			}
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
    }
>>>>>>> Stashed changes
}
