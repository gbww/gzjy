/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
package com.gzjy.samplemanager.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Add;
import com.gzjy.common.Response;
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
    
   /* @RequestMapping(value = "/add", method = RequestMethod.GET)
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
        receive
        sampleManagerService.autoAddSameReportIdSamples(records)
        return Response.success(true);
    }*/

}
