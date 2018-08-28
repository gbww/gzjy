package com.gzjy.privilege;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.rest.EpcRestService;
import com.gzjy.privilege.model.Privilege;

@RestController
public class PrivilegeInitController {
  private static final String OrganizationUserId = "f7a026cd281c41bb832dc7ce4f0468a2";
  private static final String OrganizationAdminId = "461ce92f8ebd4773867aed7cbb23928a";
  private static final String OperateUserId = "8968e187cb7640298defe7ad8d64a3e8";
  private static final String OperateAdminId = "9f6ad093eace4239bf5f6c43fa0606bb";
  private static final String MaintainsUserId = "2c763c43f2e04bc394f72087e033e8de";
  private static final String MaintainsAdminId = "7f2d72d01d5a4b7f81635894e0f469ce";
  private static final String OAUTH_TOKEN_URL = "http://localhost:8001/oauth/token";
  private static final String RoleUrl = "http://localhost:8001/v1/roles/";
  private static final String AssignPrivilege = "/action/privileges/init";
  private String assignPrivilegeForRoleUrl(String roleId) {
    return RoleUrl + roleId + AssignPrivilege;
  }
  
  @Autowired
  private EpcRestService epcRestService;
  
  private String getOAuthToken(String username, String password) {
    if (username == null || password == null) {
      throw new BizException("用户名或密码不能为空！");
    }
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
    headers.setContentType(type);
    headers.add("Authorization", "Basic Q0xJRU5UOlNFQ1JFVA==");

    HttpEntity<String> formEntity = new HttpEntity<String>("grant_type=password&username=" + username + "&password=" + password, headers);
    ResponseEntity<Object> tokenResult = restTemplate.exchange(OAUTH_TOKEN_URL, 
      HttpMethod.POST, formEntity, Object.class, "");
    if (tokenResult.getStatusCode().value() == 200) {
      LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) tokenResult.getBody();
      String identity = map.get("token_type") + " " + map.get("access_token");
      return identity;
    }
    else {
      throw new BizException("用户身份验证失败");
    }
  }
  
  private List<String> getPrivilegeIdsOfRole(String roleId, Map<Map<String, Integer>, Boolean> excludes, String token) {
    Type type = new TypeReference<List<Privilege>>() {}.getType();
    Response<List<Privilege>> res =
        epcRestService.exchangeBody("http://localhost:8001/v1/roles/" + roleId + "/privileges", HttpMethod.GET, null, type, token);
    if (!res.isSuccess()) {
      throw new BizException(res.getMessage());
    }
    List<Privilege> pList = new ArrayList<>();
    List<String> pIds = new ArrayList<>();
    if (res.isSuccess()) {
      for (Privilege p : res.getEntity()) {
        Privilege pri = new Privilege();
        pri.setId(p.getId());
        pri.setName(p.getName());
        pri.setDisplayName(p.getDisplayName());
        pri.setDescription(p.getDescription());
        pri.setCategory(p.getCategory());
        pri.setScope(p.getScope());
        pri.setView(p.getView());
        pList.add(pri);
        pIds.add(p.getId());
      }

      if (excludes != null) {// 排除此分类的权限
        Iterator<Privilege> iterator = pList.iterator();
        Iterator<String> pIdIterator = pIds.iterator();
        while (iterator.hasNext()) {
          Privilege privilege = iterator.next();
          pIdIterator.next();
          for (Map.Entry<Map<String, Integer>, Boolean> entry : excludes.entrySet()) {
            Map<String, Integer> key = entry.getKey();
            String category = key.keySet().iterator().next();
            if (privilege.getCategory().equals(category)
                && privilege.getScope() == key.get(category)) {
              if (!privilege.getView() || entry.getValue()) {
                iterator.remove();
                pIdIterator.remove();
              }
            }
          }
        }
      }
    }
    return pIds;
  }
  
  private HttpHeaders getHeaderWithOAuthToken(String token) {
    HttpHeaders headersPrivilege = new HttpHeaders();
    MediaType typeOrganization = MediaType.parseMediaType("application/json; charset=UTF-8");
    headersPrivilege.setContentType(typeOrganization);
    headersPrivilege.add("Accept", MediaType.APPLICATION_JSON_VALUE.toString());
    headersPrivilege.add("Authorization", token);
    return headersPrivilege;
  }
  
  private void assignPrivilegesForRole(String roleId, List<String> privilegeIds, String token) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headersPrivilege = getHeaderWithOAuthToken(token);

    ObjectMapper om = new ObjectMapper();
    String body = null;
    try {
      body = om.writeValueAsString(privilegeIds);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    HttpEntity<String> assignPrivilegeEntity = new HttpEntity<String>(body, headersPrivilege);
    ResponseEntity<Response> resE1 = restTemplate.exchange(assignPrivilegeForRoleUrl(roleId),
        HttpMethod.PUT, assignPrivilegeEntity, Response.class, new HashMap<>());
    Response res1 = resE1.getBody();
    if (!res1.isSuccess()) throw new BizException(res1.getMessage());
  }

  @RequestMapping(value = "/health/privileges/init", method = RequestMethod.GET)
  public Response init() {
    initOrganizationUsers();
    initOperatorUsers();
    initMaintainsUsers();
    return Response.success("ture");
  }
  
  @RequestMapping(value = "/health/organizations/privileges/init", method = RequestMethod.GET)
  public Response initOrganizationUsers() {
    /**
     * 没有审批权限，因此把审批Approve模块隐藏掉
     * 没有订单的通过和不通过权限
     */
    Map<Map<String, Integer>, Boolean> map = new HashMap<>();
    Map<String, Integer> role = new HashMap<>();
    role.put("ROLE", 1);
    Map<String, Integer> org = new HashMap<>();
    org.put("ORGANIZATION", 1);
    Map<String, Integer> user = new HashMap<>();
    user.put("USER", 1);
    Map<String, Integer> approval = new HashMap<>();
    approval.put("APPROVAL", 1);
    map.put(user, true);
    map.put(role, true);
    map.put(org, true);
    map.put(approval, true);
    String token = getOAuthToken("epic", "epic1234");
    List<String> pIds = getPrivilegeIdsOfRole(OrganizationAdminId, map, token);
    System.out.println(pIds.size());
    System.out.println(pIds);
    assignPrivilegesForRole(OrganizationUserId, pIds, token);
    return Response.success("ture");
  }
  
  @RequestMapping(value = "/health/operators/privileges/init", method = RequestMethod.GET)
  /**
   * 去除审批和驳回订单的权限
   * @return
   */
  public Response initOperatorUsers() {
    Map<Map<String, Integer>, Boolean> map = new HashMap<>();
    Map<String, Integer> role = new HashMap<>();
    role.put("ROLE", 2);
    Map<String, Integer> org = new HashMap<>();
    org.put("ORGANIZATION", 2);
    Map<String, Integer> user = new HashMap<>();
    user.put("USER", 2);
    map.put(user, false);
    map.put(role, true);
    map.put(org, false);
    String token = getOAuthToken("epic", "epic1234");
    List<String> pIds = getPrivilegeIdsOfRole(OperateAdminId, map, token);
    System.out.println(pIds.size());
    System.out.println(pIds);
    assignPrivilegesForRole(OperateUserId, pIds, token);
    return Response.success("ture");
  }
  
  @RequestMapping(value = "/health/maintains/privileges/init", method = RequestMethod.GET)
  public Response initMaintainsUsers() {
    String token = getOAuthToken("epic", "epic1234");
    Map<Map<String, Integer>, Boolean> map = new HashMap<>();
    Map<String, Integer> role = new HashMap<>();
    role.put("ROLE", 3);
    Map<String, Integer> org = new HashMap<>();
    org.put("ORGANIZATION", 3);
    Map<String, Integer> user = new HashMap<>();
    user.put("USER", 3);
    map.put(user, true);
    map.put(role, true);
    map.put(org, true);
    List<String> pIds = getPrivilegeIdsOfRole(MaintainsAdminId, map, token);
    System.out.println(pIds.size());
    System.out.println(pIds);
    assignPrivilegesForRole(MaintainsUserId, pIds, token);
    return Response.success("ture");
  }
}
