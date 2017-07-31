package com.gzjy.user.controller;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;


@RestController
@RequestMapping(value = "v1")
public class UserController {
 /* @Autowired
  private OauthUserController oauthUserController;*/
  @Autowired
  private UserService userService;
  @Autowired
  private EpcRestService epcRestService;
  
  @RequestMapping(value = "/organizations/{organizationId}/users/action/addCheck", method = RequestMethod.POST)
  public Response addCheck(@PathVariable String organizationId,
      @Validated(value = {Add.class}) @RequestBody User user, BindingResult result) {
    if (result.hasErrors()) {
      return Response.fail(result.getFieldError().getDefaultMessage());
    }
    boolean check = userService.allowCreateUser(organizationId, user);
    return Response.success(check);
  }

  @RequestMapping(value = "/organizations/{organizationId}/users/{id}/action/accessCheck", method = RequestMethod.GET)
  public Response accessCheck(@PathVariable String organizationId, @PathVariable String id) {
    userService.match(organizationId, id, null);
    boolean result = userService.allowAccessUser(id);
    return Response.success(result);
  }

  @RequestMapping(value = "/organizations/{organizationId}/users", method = RequestMethod.POST)
  public Response add(@PathVariable String organizationId,
      @Validated(value = {Add.class}) @RequestBody User user, BindingResult result) {
    if (result.hasErrors()) {
      return Response.fail(result.getFieldError().getDefaultMessage());
    }
    User u = userService.add(organizationId, user);
    return Response.success(u);
  }

  @RequestMapping(value = "/organizations/{organizationId}/users/{id}", method = RequestMethod.DELETE)
  public Response delete(@PathVariable String organizationId, @PathVariable("id") String id) {
    int result = userService.delete(organizationId, id);
    return Response.success(result);
  }

  @RequestMapping(value = "/organizations/{organizationId}/users/{id}", method = RequestMethod.PUT)
  public Response edit(@PathVariable String organizationId, @PathVariable String id,
      @RequestBody User user) {
    User u = userService.update(organizationId, id, user);
    return Response.success(u);
  }

  @RequestMapping(value = "/user", method = RequestMethod.PUT)
  public Response editSelf(@RequestBody User user) {
    User u = userService.updateSelf(user);
    return Response.success(u);
  }

 /* @RequestMapping(value = "/organizations/{organizationId}/users/{id}/password", method = RequestMethod.PUT)
  public Response editPassword(@PathVariable String organizationId, @PathVariable String id,
      @RequestBody PasswordEncoder passwordEncoder) {
    User u = userService.updatePassword(organizationId, id, passwordEncoder.getOldPassword(),
        passwordEncoder.getNewPassword());
    return Response.success(u);
  }*/

 /* @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
  public Response validatePassword(@RequestBody PasswordEncoder passwordEncoder) {
    User u = userService.validate(passwordEncoder.getPassword());
    return Response.success(u);
  }*/

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public Response users() {
    return Response.success(userService.getCurrentUser());
  }

   @RequestMapping(value = "/user/oauth/token", method = {RequestMethod.GET, RequestMethod.POST})
   public Response token(@RequestParam String username, @RequestParam String password) {
     return userService.getToken(username, password);
   }

  @RequestMapping(value = "/user/privileges", method = RequestMethod.PUT)
  public Response privileges(@RequestBody(required = false) Map<String, Integer> excludes) {
    return Response.success(userService.getPrivilegesOfCurrentUser(excludes));
  }

/*  @RequestMapping(value = "/users/action/logout", method = RequestMethod.GET)
  public Response logout(HttpServletRequest request) {

    boolean logout = oauthUserController.logout(request);
    if (logout) {
      return Response.success(logout);
    } else {
      return Response.success(false);
    }


    // return Response.success(userService.logout());
  }*/



 

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public Response users(@RequestParam(required = false) String organizationId,
      @RequestParam(required = false) String roleId, @RequestParam(required = false) Integer status,
      @RequestParam(required = false) Integer pageNum,
      @RequestParam(required = false) Integer pageSize,
      @RequestParam(required = false) String search) {
    if (pageNum == null || pageSize == null) {
      return Response.success(userService.getUsers(organizationId, roleId, status, search));
    }
    return Response
        .success(userService.getUsers(organizationId, roleId, status, pageNum, pageSize, search));
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  public Response users(@PathVariable String id) {
    return Response.success(userService.getUser(id));
  }

  @RequestMapping(value = "/organizations/{organizationId}/users/{id}", method = RequestMethod.GET)
  public Response users(@PathVariable String organizationId, @PathVariable String id) {
    return Response.success(userService.getUser(organizationId, id));
  }

  @RequestMapping(value = "/log/users", method = RequestMethod.GET)
  public Response logusers() {
    return Response.success(userService.getLogUsers());
  }


  @RequestMapping(value = "/user/kaptcha/request", method = RequestMethod.GET)
  public Response sendKaptcha(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    return Response.success(userService.getKaptcha(request, response));
  }
  
  @RequestMapping(value = "/user/kaptcha/response", method = RequestMethod.GET)
  public Response receiveKaptcha(@RequestParam String id)
      throws Exception {
    return Response.success(userService.receiveKaptcha(id));
  }

  @RequestMapping(value = "/organizations/users", method = RequestMethod.GET)
  public Response organizationUsers(@RequestParam List<String> organizationIds) {
    return Response.success(userService.getUsersBaseOrganizations(organizationIds));
  }

  @RequestMapping(value = "/organizations/{organizationId}/users/{userId}/roles/{roleId}/action/assignRole", method = RequestMethod.PUT)
  public Response assign(@PathVariable String organizationId, @PathVariable String userId,
      @PathVariable String roleId) {
    return Response.success(userService.assignRoleForUser(organizationId, userId, roleId));
  }

 

  @RequestMapping(value = "/organizations/{organizationId}/users/{userId}/action/resume", method = RequestMethod.PUT)
  public Response activate(@PathVariable String organizationId, @PathVariable String userId) {
    return Response.success(userService.activate(organizationId, userId));
  }

  @RequestMapping(value = "/organizations/{organizationId}/users/{userId}/action/suspend", method = RequestMethod.PUT)
  public Response suspend(@PathVariable String organizationId, @PathVariable String userId) {
    return Response.success(userService.suspend(organizationId, userId));
  }

  @RequestMapping(value = "/organizations/{organizationFromId}/users/{organizationToId}/{userId}/roles/{roleId}/action/change", method = RequestMethod.PUT)
  public Response change(@PathVariable String organizationFromId,
      @PathVariable String organizationToId, @PathVariable String userId,
      @PathVariable String roleId) {
    return Response.success(userService.changeOrganizationForUser(userId, organizationFromId,
        organizationToId, roleId));
  }
  
 /* @RequestMapping(value = "/users/{key}/encode/{str}", method = RequestMethod.GET)
  public Response encode(@PathVariable String str, @PathVariable String key) {
    return Response.success(userService.encode(str, key));
  }*/

  
}

