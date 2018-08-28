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

import com.gzjy.client.model.ClientScheduler;
import com.gzjy.client.model.SchedulerRemind;
import com.gzjy.client.model.SchedulerRepetition;
import com.gzjy.client.service.ClientSchedulerService;
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
@RestController
@RequestMapping(value = "v1/ahgz/clientscheduler")
public class ClientSchedulerController {
    @Autowired
    private ClientSchedulerService clientSchedulerService;
    
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    
    public Response add(@Validated(value = { Add.class }) @RequestBody ClientScheduler record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        ClientScheduler scheduler = clientSchedulerService.add(record);
        return Response.success(scheduler);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
   
    public Response update(@Validated(value = { Update.class }) @RequestBody ClientScheduler record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        ClientScheduler scheduler = clientSchedulerService.update(record);
        return Response.success(scheduler);
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
            int num = clientSchedulerService.delete(n);
            record+=num;
        }
     
        return Response.success("成功删除"+record+"条数据");
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    
    public Response list( @RequestParam(name = "order", required = false) String orderby,
            @RequestParam(name = "clientNum", required = true) Integer clientNum) {
        Map<String, Object> filter = new HashMap<String, Object>(); 
        if (StringUtils.isBlank(orderby)) {
            orderby = "created_at desc";
        }
        List<ClientScheduler> records=clientSchedulerService.selectByclientNum(clientNum, orderby, filter);
        return Response.success(records);
      
    }
    
     @RequestMapping(value = "/repetition", method = RequestMethod.GET)   
    public Response getSchedulerRepetition( ) {       
        Map<String, String> result = new HashMap<String, String>();       
        for (SchedulerRepetition node : SchedulerRepetition.values()) {           
            result.put(String.valueOf(node.getCode()), node.getName());
        }
        return Response.success(result);
      
    }
     
     
     @RequestMapping(value = "/remind", method = RequestMethod.GET)   
     public Response getSchedulerRemind( ) {       
         Map<String, String> result = new HashMap<String, String>();       
         for (SchedulerRemind node : SchedulerRemind.values()) {           
             result.put(String.valueOf(node.getCode()), node.getName());
         }
         return Response.success(result);
       
     }
    
    
    
    
 

}
