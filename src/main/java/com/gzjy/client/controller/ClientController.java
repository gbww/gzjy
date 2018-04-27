/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
package com.gzjy.client.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.gzjy.client.model.GzClient;
import com.gzjy.client.service.ClientService;
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;
import com.gzjy.common.annotation.Privileges;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
@RestController
@RequestMapping(value = "v1/ahgz/client")
public class ClientController {
    @Autowired
    private ClientService clientManagerService;
    
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Privileges(name = "CLIENT-ADD", scope = { 1 })
    public Response add(@Validated(value = { Add.class }) @RequestBody GzClient record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        GzClient client = clientManagerService.add(record);
        return Response.success(client);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @Privileges(name = "CLIENT-UPDATE", scope = { 1 })
    public Response update(@Validated(value = { Update.class }) @RequestBody GzClient record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        GzClient client = clientManagerService.update(record);
        return Response.success(client);
    }
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Privileges(name = "CLIENT-DELETE", scope = { 1 })
    public Response delete( @RequestBody List<Integer> ids,
            BindingResult result) {
        int record=0;
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        for(Integer n :ids) {
            int num = clientManagerService.delete(n);
            record+=num;
        }
     
        return Response.success("成功删除"+record+"条数据");
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @Privileges(name = "CLIENT-SELECT", scope = { 1 })
    public Response list( @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> filter = new HashMap<String, Object>();
        //filter.put("client_name", "文");
       
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        PageInfo<GzClient> pages=clientManagerService.select(pageNum, pageSize, order, filter);
        return Response.success(pages);
      
    }
    
 

}
