package com.gzjy.role;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.rest.EpcRestService;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.Role;

@RestController
@RequestMapping(value = "v1")
public class RoleController {
  @Autowired
  private RoleService roleService;
  @Autowired
  private EpcRestService epcRestService;
  
  @RequestMapping(value = "/roles", method = RequestMethod.POST)
  public Response add(@Validated(value={Add.class}) @RequestBody CrabRole role, BindingResult result) {
    if(result.hasErrors()){
      return Response.fail(result.getFieldError().getDefaultMessage());
    }
    Role r = roleService.add(role);
    return Response.success(r);
  }
  
  /*@RequestMapping(value = "/roles/{id}/action/assign", method = RequestMethod.PUT)
  public Response assign(@PathVariable String id, @RequestBody List<String> privileges) {
    int result = roleService.assignPrivilegeForRole(id, privileges);
    return Response.success(result);
  }*/
  
  @RequestMapping(value = "/roles/{id}/action/privileges/init", method = RequestMethod.PUT)
  public Response initPrivileges(@PathVariable String id, @RequestBody List<String> privileges) {
    int result = roleService.initPrivilegeForDefaultRole(id, privileges);
    return Response.success(result);
  }
  
  @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
  public Response delete(@PathVariable("id") String id) {
    int result = roleService.delete(id);
    return Response.success(result);
  }
  
  @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
  public Response roles(@PathVariable String id) {
    return Response.success(roleService.getById(id));
  }
  
/*  @RequestMapping(value = "/crabroles", method = RequestMethod.GET)
  public Response crabroles(@RequestParam(required=false) String organizationId) {
    return Response.success(roleService.getCrabRoles(organizationId));
  }*/
  
  @RequestMapping(value = "/roles", method = RequestMethod.GET)
  public Response roles(@RequestParam(required=false) String organizationId, @RequestParam(required=false) Integer pageNum, @RequestParam(required=false) Integer pageSize) {
    if (pageNum == null || pageSize == null) {
      return Response.success(roleService.getRoles(organizationId));
    }
    return Response.success(roleService.getRoles(organizationId, pageNum, pageSize));
  }

  @RequestMapping(value = "/roles/{id}/privileges", method = RequestMethod.GET)
  public Response privileges(@PathVariable String id, @RequestParam(required=false) String category, @RequestParam(required=false) Integer pageNum, @RequestParam(required=false) Integer pageSize) {
    if (pageNum == null || pageSize == null) {
      return Response.success(roleService.getPrivileges(id, category));
    }
    return Response.success(roleService.getPrivileges(id, category, pageNum, pageSize));
  }
  
  @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
  public Response edit(@PathVariable String id, @RequestBody CrabRole role) {
    Role r = roleService.update(id, role);
    return Response.success(r);
  }
  
 /* @RequestMapping(value = "/roles/get/recall/privilege/{roleId}", method = RequestMethod.GET)
  public Response get(@PathVariable String roleId) {
    Type type = new TypeReference<List<Privilege>>() {}.getType();
    Response<List<Privilege>> res = epcRestService.exchangeBody("http://localhost:8001/v1/roles/" + roleId + "/privileges", HttpMethod.GET, null, type);
    if(!res.isSuccess()){
      throw new BizException(res.getMessage());
    }
    List<Privilege> list = res.getEntity();
    return Response.success(list);
  }*/
}
