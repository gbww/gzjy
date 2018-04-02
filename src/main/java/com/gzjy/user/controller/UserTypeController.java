/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
package com.gzjy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;
import com.gzjy.user.UserTypeServiceImpl;
import com.gzjy.user.model.UserType;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
@RestController
@RequestMapping(value = "v1/ahgz/usertype")
public class UserTypeController {
    
    @Autowired
    private UserTypeServiceImpl userTypeService;
    
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@Validated(value = { Add.class }) @RequestBody UserType record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        record = userTypeService.add(record);
        return Response.success(record);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Response update(@Validated(value = { Update.class }) @RequestBody UserType record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        UserType client = userTypeService.update(record);
        return Response.success(client);
    }
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    //@Privileges(name = "CLIENT-DELETE", scope = { 1 })
    public Response delete( @RequestBody List<String> ids,
            BindingResult result) {
        int record=0;
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        for(String n :ids) {
            int num = userTypeService.delete(n);
            record+=num;
        }
     
        return Response.success("成功删除"+record+"条数据");
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    //@Privileges(name = "CLIENT-SELECT", scope = { 1 })
    public Response list( @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name="type",required=false) String type,
            @RequestParam(name="name",required=false) String name) {
        Map<String, Object> filter = new HashMap<String, Object>();   
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        if(!StringUtils.isBlank(type)) {
            filter.put("type", type);
        }
        if(!StringUtils.isBlank(name)) {
            filter.put("name", name);
        }
        PageInfo<UserType> pages=userTypeService.select(pageNum, pageSize, order, filter);
        return Response.success(pages);
      
    }
    //不分页查找
    @RequestMapping(value = "/selectall", method = RequestMethod.GET)
    //@Privileges(name = "CLIENT-SELECT", scope = { 1 })
    public Response listAll( @RequestParam(name = "order", required = false) String order,
            @RequestParam(name="type",required=false) String type,
            @RequestParam(name="name",required=false) String name) {
        Map<String, Object> filter = new HashMap<String, Object>();   
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        if(!StringUtils.isBlank(type)) {
            filter.put("type", type);
        }
        if(!StringUtils.isBlank(name)) {
            filter.put("name", name);
        }
        List<UserType> pages=userTypeService.select( filter,order);
        return Response.success(pages);
      
    }
    

}
