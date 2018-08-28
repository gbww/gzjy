/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
package com.gzjy.unit.controller;

import java.util.HashMap;
import java.util.LinkedList;
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

import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.unit.model.GzUnit;
import com.gzjy.unit.service.GzUnitService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
@RestController
@RequestMapping(value = "v1/ahgz/gzunit/")
public class GzUnitController {
    @Autowired
    private GzUnitService gzUnitservice;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    //@Privileges(name = "GZUNIT-ADD", scope = { 1 })
    public Response add(@Validated(value = { Add.class }) @RequestBody GzUnit gzUnit,
            BindingResult result) {
        Boolean flag=gzUnitservice.add(gzUnit);
        return Response.success(flag);
       
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    //@Privileges(name = "GZUNIT-DELETE", scope = { 1 })
    public Response delete( @RequestBody List<String> ids,
            BindingResult result) {
       
        if (result.hasErrors()) {
            return Response.fail(result.getFieldError().getDefaultMessage());
        }
        
        for(String n :ids) {
             gzUnitservice.delete(n);
           
        }
     
        return Response.success("成功删除");
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    //@Privileges(name = "GZUNIT-UPDATE", scope = { 1 })
    public Response update(@Validated(value = { Add.class }) @RequestBody GzUnit gzUnit,
            BindingResult result) {
        Boolean flag=gzUnitservice.update(gzUnit);
        return Response.success(flag);
       
    }
    
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @Privileges(name = "GZUNIT-SELECT", scope = { 1 })
    public Response list( @RequestParam(name = "order", required = false) String order) {
        Map<String, Object> filter = new HashMap<String, Object>();           
        if (StringUtils.isBlank(order)) {
            order = "unit_name desc";
        }
        List<GzUnit> records=new LinkedList<GzUnit>();
         records=gzUnitservice.findAll(filter,order);
        return Response.success(records);
      
    }

}
