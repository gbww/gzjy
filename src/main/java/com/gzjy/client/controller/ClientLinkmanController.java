/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
package com.gzjy.client.controller;

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
import com.gzjy.client.model.ClientLinkman;
import com.gzjy.client.model.GzClient;
import com.gzjy.client.service.ClientLinkmanService;
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;
import com.gzjy.common.annotation.Privileges;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
@RestController
@RequestMapping(value = "v1/ahgz/clientlinkman")
public class ClientLinkmanController {
    @Autowired
    private ClientLinkmanService clientLinkmanService;
    
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    
    public Response add(@Validated(value = { Add.class }) @RequestBody ClientLinkman record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        ClientLinkman linkman = clientLinkmanService.add(record);
        return Response.success(linkman);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
   
    public Response update(@Validated(value = { Update.class }) @RequestBody ClientLinkman record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        ClientLinkman linkman = clientLinkmanService.update(record);
        return Response.success(linkman);
    }
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
   
    public Response delete( @RequestBody List<String> ids,
            BindingResult result) {
        int record=0;
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        for(String n :ids) {
            int num = clientLinkmanService.delete(n);
            record+=num;
        }
     
        return Response.success("成功删除"+record+"条数据");
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    
    public Response list( @RequestParam(name = "order", required = false) String orderby,
            @RequestParam(name = "clientNum", defaultValue = "1") Integer clientNum) {
        Map<String, Object> filter = new HashMap<String, Object>();
        
        if (StringUtils.isBlank(orderby)) {
            orderby = "created_at desc";
        }
        List<ClientLinkman> records=clientLinkmanService.selectByclientNum(clientNum, orderby, filter);
        return Response.success(records);
      
    }
    
 

}
