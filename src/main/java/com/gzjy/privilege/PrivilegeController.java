package com.gzjy.privilege;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.common.Update;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.model.Role;
import com.gzjy.role.model.Role.RoleIdentity;

@RestController
@RequestMapping(value = "v1")
public class PrivilegeController {
  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/privileges/{id}", method = RequestMethod.PUT)
  public Response edit(@PathVariable String id,@Validated(value={Update.class}) @RequestBody Privilege privilege, BindingResult result) {
    if(result.hasErrors()){
      return Response.fail(result.getFieldError().getDefaultMessage());
    }
    Privilege p = privilegeService.update(id, privilege);
    return Response.success(p);
  }
  
  @RequestMapping(value = "/privileges", method = RequestMethod.GET)
  public Response privilege(@RequestParam(required=false) String category, @RequestParam(required=false) Integer pageNum, @RequestParam(required=false) Integer pageSize) {
    if (pageNum == null || pageSize == null) {
      return Response.success(privilegeService.getPrivileges(category));
    }
    return Response.success(privilegeService.getPrivileges(category, pageNum, pageSize));
  }
  
  @RequestMapping(value = "/privileges/categories", method = RequestMethod.GET)
  public Response categories() {
    return Response.success(privilegeService.getCategories());
  }
  
  @RequestMapping(value = "/privileges/captials/categories", method = RequestMethod.GET)
  public Response categoriesWithCapitals() {
    return Response.success(privilegeService.getCategoriesWithCapitals());
  }
  
  @RequestMapping(value = "/privileges/log/op", method = RequestMethod.GET)
  @Privileges(name = "INSTANCE-START", scope = {1, 2}, identity = {Role.RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.SUPER_ADMIN})
  public Response privilege() {
    return Response.success("有权执行");
  }
}
