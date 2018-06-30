package com.gzjy.review.controller;

import com.gzjy.common.Response;
import com.gzjy.review.modle.ComVarietyFormats;
import com.gzjy.review.service.ComVarietyFormatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 品种业态信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/commentuser" })
public class ComVarietyFormatsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComVarietyFormatsController.class);

    @Autowired
    private ComVarietyFormatsService comVarietyFormatsService;

    /**
     * 分页查询品种业态信息
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
     *  根据主键查询品种业态信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public Response selectByPrimaryKey(@PathVariable(name = "id",required = true) String id){

        return Response.success(null);
    }

    /**
     * 添加品种业态信息
     * @param comVarietyFormats
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody ComVarietyFormats comVarietyFormats){
        return Response.success(null);
    }

    /**
     * 修改品种业态信息
     * @param comVarietyFormats
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Response update(@RequestBody ComVarietyFormats comVarietyFormats){
        return  Response.success(null);
    }

    /**
     * 根据主键删除品种业态信息
     * @param ids 主键
     * @return
     */
    @RequestMapping(value = "/delete")
    public Response delete(@RequestBody List<String> ids){
        return Response.success(null);
    }
}
