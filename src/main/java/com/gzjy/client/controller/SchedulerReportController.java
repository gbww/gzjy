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
import com.gzjy.client.model.SchedulerReport;
import com.gzjy.client.service.SchedulerReportService;
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
@RestController
@RequestMapping(value = "v1/ahgz/schedulerreport")
public class SchedulerReportController {
    @Autowired
    private SchedulerReportService schedulerReportService;
    
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    
    public Response add(@Validated(value = { Add.class }) @RequestBody SchedulerReport record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        SchedulerReport report = schedulerReportService.add(record);
        return Response.success(report);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
   
    public Response update(@Validated(value = { Update.class }) @RequestBody SchedulerReport record,
            BindingResult result) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        //非空判断
        SchedulerReport report = schedulerReportService.update(record);
        return Response.success(report);
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
            int num = schedulerReportService.delete(n);
            record+=num;
        }
     
        return Response.success("成功删除"+record+"条数据");
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    
    public Response list( @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "schedulerId", required = true) String schedulerId) {
        Map<String, Object> filter = new HashMap<String, Object>();
        String orderby = new String();   
        if (StringUtils.isBlank(order)) {
            orderby = "created_at desc";
        }
        List<SchedulerReport> records=schedulerReportService.selectBySchedulerId(schedulerId, orderby, filter);
        return Response.success(records);
      
    }
    
 

}
