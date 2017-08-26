package com.gzjy.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.Update;

import com.gzjy.organization.model.Organization;
import com.gzjy.organization.service.OrganizationService;


@RestController
@RequestMapping(value = "v1")
public class OrganizationController {
  @Autowired
  private OrganizationService organizationService;
  @RequestMapping(value = "/organizations", method = RequestMethod.POST)
  public Response add(@Validated(value={Add.class}) @RequestBody Organization organization, BindingResult result) {
    if (result.hasErrors()) {
      return Response.fail(result.getFieldError().getDefaultMessage());
    }
    Organization o = organizationService.add(organization);
    return Response.success(o);
  }

  
  
  @RequestMapping(value = "/organizations/{id}", method = RequestMethod.GET)
  public Response organizations(@PathVariable String id) {
    return Response.success(organizationService.getById(id));
  }
  @RequestMapping(value = "/organizations", method = RequestMethod.GET)
  public Response list(@RequestParam(value = "pageNum", required = false, defaultValue="1") Integer pageNum,
          @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize,
          @RequestParam(value = "name", required = false, defaultValue="")String name,
          @RequestParam(value = "orderby", required = false, defaultValue="")String orderBy
                      ) {
    

    return Response.success(organizationService.select(pageNum, pageSize, name, orderBy));
  }
 

  @RequestMapping(value = "/organizations/{id}", method = RequestMethod.DELETE)
  public Response delete(@PathVariable("id") String id) {
    int result = organizationService.delete(id);
    return Response.success(result);
  }

  @RequestMapping(value = "/organizations/{id}", method = RequestMethod.PUT)
  public Response edit( @PathVariable String id, @Validated(value = {Update.class}) @RequestBody Organization organization, 
                       BindingResult result) {
    if (result.hasErrors()) {
      return Response.fail(result.getFieldError().getDefaultMessage());
    }
    Organization o = organizationService.update(id, organization);
    return Response.success(o);
  }
  
  
  
}
