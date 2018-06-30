package com.gzjy.review.controller;

import com.gzjy.common.Response;
import com.gzjy.review.modle.ComProjectReview;
import com.gzjy.review.service.ComProjectReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 企业评审信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/commentuser" })
public class ComProjectReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComProjectReviewController.class);

    @Autowired
    private ComProjectReviewService comProjectReviewService;

    /**
     * 分页查询企业评审信息
     * @param pageCount
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/select/infors", method = RequestMethod.GET)
    public Response selectByPages(
            @RequestParam(name = "pageCount", defaultValue = "10") Integer pageCount,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum){



        return Response.success(null);
    }

    /**
     *  根据主键查询企业评审信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public Response selectByPrimaryKey(@PathVariable(name = "id",required = true) String id){

        return Response.success(null);
    }

    /**
     * 添加企业评审信息
     * @param comProjectReview
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody ComProjectReview comProjectReview){
        return Response.success(null);
    }

    /**
     * 修改企业评审信息
     * @param comProjectReview
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Response update(@RequestBody ComProjectReview comProjectReview){
        return  Response.success(null);
    }

    /**
     * 根据主键删除企业评审信息
     * @param ids 主键
     * @return
     */
    @RequestMapping(value = "/delete")
    public Response delete(@RequestBody List<String> ids){
        return Response.success(null);
    }
}
