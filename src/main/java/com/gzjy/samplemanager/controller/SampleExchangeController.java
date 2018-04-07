/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
package com.gzjy.samplemanager.controller;


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
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;
import com.gzjy.common.exception.BizException;
import com.gzjy.samplemanager.model.SampleExchange;
import com.gzjy.samplemanager.service.SampleManagerService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
@RestController
@RequestMapping(value = "v1/ahgz/sampleExchange/")
public class SampleExchangeController {
    @Autowired
    private SampleManagerService sampleManagerService;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@Validated(value = { Add.class }) @RequestBody List<SampleExchange> records,
            BindingResult result ) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        } 
        if(records.size()>0) {
            for(SampleExchange re:records)
                re.setCreatedAt(new Date());
            }
        else {
            throw new BizException("样品流通记录为空");
        }
        
        return Response.success(sampleManagerService.addSampleExchange(records));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@Validated(value = { Update.class }) @RequestBody SampleExchange record,
            BindingResult result ) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
     
        return Response.success(sampleManagerService.updateSampleExchange(record));
        
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete( @RequestBody List<String> ids,
            BindingResult result) {
        
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }      
        return Response.success(sampleManagerService.deleteSampleExchange(ids));    
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public Response list( @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "reportId", required = false) String reportId,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> filter = new HashMap<String, Object>();
  
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        if (!StringUtils.isBlank(reportId)) {
            filter.put("report_id", reportId);
        }
        PageInfo<SampleExchange> pages=sampleManagerService.selectSampleExchangeList(pageNum, pageSize, order, filter);
        return Response.success(pages);
      
    }
    

}
