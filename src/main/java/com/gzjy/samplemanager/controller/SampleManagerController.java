/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
package com.gzjy.samplemanager.controller;

import java.util.ArrayList;
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
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.samplemanager.model.SampleManager;
import com.gzjy.samplemanager.service.SampleManagerService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
@RestController
@RequestMapping(value = "v1/ahgz/sampleManager/")
public class SampleManagerController {
    @Autowired
    private SampleManagerService sampleManagerService;
      //要求getSampleNumber和getLiuyangCount是整数
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@Validated(value = { Add.class }) @RequestBody ReceiveSample receive,
            BindingResult result ) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        String reportId=receive.getReportId();
        if(StringUtils.isBlank(reportId)) {
            throw new BizException("报告编号不存在");
        }
        String sampleName=receive.getSampleName();
        int chouyangshuliang=Integer.valueOf(receive.getSampleNumber());
        int liuynagshu=Integer.valueOf(receive.getLiuyangCount());
        int jianyangshu=0;
        
        if(chouyangshuliang>liuynagshu&&liuynagshu>0) {
            jianyangshu=chouyangshuliang-liuynagshu;
        }
        else {
            throw new BizException("抽样数或留样数不正确");
        }
        int zhibaoqi=receive.getZhibaoqi();  //天
        Date sampleCirculateDate= receive.getSampleCirculateDate();  //样品流通日期
        Date receiveDate= receive.getReceiveDate();   //收样日期
        Date expire=new Date(sampleCirculateDate.getTime()+zhibaoqi*24*60*60*1000);
        String status="normal";
        if(expire.getTime()<receiveDate.getTime()) {
            status="abandon";
        }
        List<SampleManager> records=new ArrayList<SampleManager>();
        
        for(int i=0;i<jianyangshu;i++) {
            SampleManager record =new SampleManager();
            record.setReportId(reportId);
            record.setCreatedAt(new Date());
            record.setExpiryTime(expire);
            record.setReceiveDate(receiveDate);
            record.setSampleCirculateDate(sampleCirculateDate);
            record.setSampleName(sampleName);
            record.setStatus(status);
            record.setZhibaoqi(zhibaoqi);
            record.setManagerType("检样");
            records.add(record);
        }
        for(int i=0;i<liuynagshu;i++) {
            SampleManager record =new SampleManager();
            record.setReportId(reportId);
            record.setCreatedAt(new Date());
            record.setExpiryTime(expire);
            record.setReceiveDate(receiveDate);
            record.setSampleCirculateDate(sampleCirculateDate);
            record.setSampleName(sampleName);
            record.setStatus(status);
            record.setZhibaoqi(zhibaoqi);
            record.setManagerType("留样");
            records.add(record);
        }      
        return Response.success(sampleManagerService.autoAddSameReportIdSamples(records));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@Validated(value = { Update.class }) @RequestBody SampleManager record,
            BindingResult result ) {
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
     
        return Response.success(sampleManagerService.updateSampleManager(record));
        
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete( @RequestBody List<String> ids,
            BindingResult result) {
        int record=0;
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }      
        return Response.success(sampleManagerService.deleteSampleManager(ids));      
    }
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public Response list( @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "managerType", required = false) String managerType,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> filter = new HashMap<String, Object>();
  
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        if (!StringUtils.isBlank(managerType)) {
            filter.put("manager_type", managerType);
        }
        PageInfo<SampleManager> pages=sampleManagerService.selectSampleManagerList(pageNum, pageSize, order, filter);
        return Response.success(pages);
      
    }
    

}
