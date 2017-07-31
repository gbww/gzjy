package com.gzjy.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
// import org.springframework.security.oauth2.common.OAuth2AccessToken;
// import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.collect.Maps;
import com.gzjy.KaptchaConfiguration;
import com.gzjy.common.Response;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.organization.model.Organization;
import com.gzjy.organization.model.Organization.OrganizationType;
import com.gzjy.organization.service.OrganizationService;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.RoleService;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.Role;
import com.gzjy.role.model.Role.RoleIdentity;
import com.gzjy.role.model.Role.RoleScope;
import com.gzjy.security.HttpHelper;
import com.gzjy.user.mapper.KaptchasMapper;
import com.gzjy.user.mapper.UserMapper;
import com.gzjy.user.model.Kaptchas;
import com.gzjy.user.model.User;
import com.gzjy.user.model.User.UserStatus;
import com.thoughtworks.xstream.core.util.Base64Encoder;






@Service
public class UserServiceImpl implements UserService {
  private final String FAKEPOOLID = "1qaz2wsx3edc4rfv5tgb6yhn7ujm";
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private RoleService roleService;
  @Autowired
  private OrganizationService organizationService;

 
 
  private final String SUPER_ADMIN_USERNAME = "epic";

  @Autowired
  private KaptchasMapper kaptchaMapper;


  
  @Value("${user.oauth_token}")
  private String oauth_token_url;
  

  private Producer kaptchaProducer = null;

  @Autowired
  KaptchaConfiguration kaptchaConfiguration;

/*  @Bean
  public Producer producerBean() {
    return new DefaultKaptcha() {

      @Override
      public Config getConfig() {
        return kaptchaConfiguration;
      }
    };
  }

  @Autowired
  public void setKaptchaProducer(Producer kaptchaProducer) {
    this.kaptchaProducer = kaptchaProducer;
  }*/

  @Override
  public User check(String id) {
    User u = userMapper.selectById(id);
    if (u == null) {
      throw new BizException("不存在该用户！");
    }
    return u;
  }

  @Override
  public User validate(String password) {
    User user = getCurrentUser();
    /**
     * Did not use encrypted password
     * 
     * PasswordEncoder pswEncoder = new PasswordEncoder();
     * if (user.getPassword().equals(pswEncoder.encode(password)))
     */
    
   
    if (user.getPassword().equals(password))
      return user;
    else
      throw new BizException("用户密码错误，请检查！");
  }

  @Override
  public boolean allowAccessUser(String id) {
    User user = check(id);
    User u = getCurrentUser();
    CrabRole r = u.getRole();
    if (r.isSuperAdmin())
      return true;
    else if (r.isOperationsAdmin()) { // 运营管理员
      if (user.getOrganization().getType() == OrganizationType.ORGANIZATIONS.getCode()) // 查看自服务用户
        return true;
      else if (user.getOrganizationId().equals(u.getOrganizationId())) // 本部门用户
        return true;
    } else if (r.isOperationsUser()) { // 运营普通用户
      if (user.getOrganization().getType() == OrganizationType.ORGANIZATIONS.getCode()) // 查看自服务用户
        return true;
      if (user.getId().equals(u.getId())) // 查看自己
        return true;
    } else if (r.isMaintainsAdmin() || r.isOrganizationAdmin()) { // 自服务或运维管理员
      if (user.getOrganizationId().equals(u.getOrganizationId())) // 本部门用户
        return true;
    } else { // 普通用户
      if (user.getId().equals(u.getId())) // 查看自己
        return true;
    }
    // if (getUsers(null, null, null).contains(user)) return true;
    return false;
  }

  @Override
  @Privileges(name = "USER-ADD", scope = 2, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public boolean allowCreateUser(String organizationId, User user) {
    match(organizationId, null, user);
    if (usernameExist(user.getUsername())) {
      throw new BizException("该用户名已经存在，请使用其他名称");
    }
    String roleId = user.getRoleId();
    User u = getCurrentUser();
    Role role = u.getRole();
    Organization o = organizationService.check(organizationId);
    Role r = roleService.getDefaultUserRole(RoleScope.getRoleScope(o.getType())); // 获取对应部门的默认用户角色
    if (role.isOperationsUser() || role.isOperationsAdmin()) { // 运营操作员创建自服务用户
      if (o.getType() == 1)
        if (roleId != null && !roleId.equals(r.getId()))
          throw new BizException("当前用户创建自服务用户无法指定角色");
        else
          roleId = r.getId();
      else if (organizationId.equals(u.getOrganizationId()))
        if (role.isOperationsUser())
          throw new BizException("没有权限创建该部门的新用户");
        else if (roleId == null)
          roleId = r.getId(); // 获取默认运营用户角色
        else {
          if (!roleService.getRoles(organizationId).contains(roleService.check(roleId))) // 判断该角色是否是该部门的角色
            throw new BizException("没有权限分配该角色");
        }
      else
        throw new BizException("没有权限创建该部门的新用户");
    } else {
      if (roleId == null) roleId = r.getId(); // 获取对应部门的默认用户角色
      if (!roleService.getRoles(organizationId).contains(roleService.check(roleId))) // 判断该角色是否是该部门的角色
        throw new BizException("没有权限分配该角色");
    }
    return true;
  }

  @Override
  @Transactional
  public User initDefaultAdminForOrganization(User user) {
   
    user.setPassword(user.getPassword());
    userMapper.insert(user);
    return user;
  }

  @Override
  @Transactional
  @Privileges(name = "USER-ADD", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public User add(String organizationId, User user) {
    // FIXME
    match(organizationId, null, user);
    if (usernameExist(user.getUsername())) throw new BizException("该用户名已经存在，请使用其他名称");
    // if (emailExist(user.getEmail())) throw new BizException("该邮箱已经存在，请使用其他邮箱");
    // if (phoneExist(user.getPhone())) throw new BizException("该手机号已经存在，请使用其他联系方式");
    String roleId = user.getRoleId();
    User u = getCurrentUser();
    Role role = u.getRole();
    Organization o = organizationService.check(organizationId);
    Role r = roleService.getDefaultUserRole(RoleScope.getRoleScope(o.getType())); // 获取对应部门的默认用户角色
    if (role.isOperationsUser() || role.isOperationsAdmin()) { // 运营操作员创建自服务用户
      if (o.getType() == 1) {
        // if (roleId != null && !roleId.equals(r.getId()))
        boolean legal = false;
        if (roleId == null)
          roleId = r.getId();
        else {
          List<Role> orgRoles = roleService.getRoles(organizationId);
          for (Role cr : orgRoles) {
            if (cr.getId().equals(roleId)) {
              legal = true;
              break;
            }
          }
          if (!legal) throw new BizException("没有权限为新用户指定所选角色");
        }
      } else if (organizationId.equals(u.getOrganizationId()))
        if (role.isOperationsUser())
          throw new BizException("没有权限创建该部门的新用户");
        else if (roleId == null)
          roleId = r.getId(); // 获取默认运营用户角色
        else {
          if (!roleService.getRoles(organizationId).contains(roleService.check(roleId))) // 判断该角色是否是该部门的角色
            throw new BizException("没有权限分配该角色");
        }
      else
        throw new BizException("没有权限创建该部门的新用户");
    } else {
      if (roleId == null) roleId = r.getId(); // 获取对应部门的默认用户角色
      if (!roleService.getRoles(organizationId).contains(roleService.check(roleId))) // 判断该角色是否是该部门的角色
        throw new BizException("没有权限分配该角色");
    }
    // END HERE

    user.setId(UUID.random());
    user.setRoleId(roleId);
    user.setOrganizationId(organizationId);
    user.setPassword(user.getPassword());
    user.setState(UserStatus.ACTIVATE.getCode());
    user.setCreatedAt(new Date());
    user.setUpdatedAt(new Date());
    userMapper.insert(user);
    return user;
  }

  

  @Override
  @Transactional
  @Privileges(name = "USER-ROLE-CHANGE", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.ORGANIZATIONS_ADMIN, RoleIdentity.OPERATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public User assignRoleForUser(String organizationId, String userId, String roleId) {
    User u = match(organizationId, userId, null);
    if (getCurrentUser().getId().equals(userId)) {
      throw new BizException("不能更改自己的角色");
    }
    if (!roleService.getRoles(organizationId).contains(roleService.check(roleId))) // 判断该角色是否是该部门的角色
      throw new BizException("没有权限分配该角色");
    u.setId(userId);
    u.setRoleId(roleId);
    userMapper.updateByIdSelective(u);
    return u;
  }

  @Override
  @Privileges(name = "USER-ORGANIZATION-CHANGE", scope = 2, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN})
  public User changeOrganizationForUser(String id, String organizationFromId,
      String organizationToId, String roleId) {
    // 运营运维不能换部门
    User u = match(organizationFromId, id, null);
    if (id.equals(getCurrentUser().getId())) throw new BizException("无法将自己调换部门");
    if (!organizationService.allowAccessOrganization(organizationFromId)) {
      throw new BizException("没有权限调换: " + organizationFromId + " 部门的用户");
    }
    if (organizationService.check(organizationFromId).getType() != organizationService
        .check(organizationToId).getType()) {
      throw new BizException("无法将用户换到不同平台的部门");
    }
    // TODO 使用roleService allow方法
    if (!roleService.getRoles(organizationToId).contains(roleService.check(roleId))) {
      throw new BizException("无法为用户分配不属于新部门的角色");
    }
    u.setOrganizationId(organizationToId);
    u.setRoleId(roleId);
    u.setUpdatedAt(new Date());
    userMapper.updateByIdSelective(u);
    return u;
  }

  @Override
  @Transactional
  @Privileges(name = "USER-STATUS-CHANGE", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public User activate(String organizationId, String id) {
    User u = match(organizationId, id, null);
    if (id.equals(getCurrentUser().getId())) throw new BizException("无法激活自己");
    if (!allowAccessUser(id)) {
      throw new BizException("没有权限访问该用户");
    }
    u.setId(id);
    u.setState(UserStatus.ACTIVATE.getCode());
    userMapper.updateByIdSelective(u);
    return u;
  }

  @Override
  @Transactional
  @Privileges(name = "USER-STATUS-CHANGE", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public User suspend(String organizationId, String id) {
    User u = match(organizationId, id, null);
    if (id.equals(getCurrentUser().getId())) throw new BizException("无法挂起自己");
    if (!allowAccessUser(id)) {
      throw new BizException("没有权限访问该用户");
    }
    u.setId(id);
    u.setState(UserStatus.SUSPEND.getCode());
    userMapper.updateByIdSelective(u);
    return u;
  }

  /*
   * @Override
   * 
   * @Transactional public User logout() { HttpServletRequest request = ((ServletRequestAttributes)
   * RequestContextHolder.getRequestAttributes()).getRequest(); String authHeader =
   * request.getHeader("Authorization"); Principal principal = request.getUserPrincipal(); if
   * (authHeader != null) { System.out.println(authHeader); String tokenValue =
   * authHeader.replace("bearer", "").trim(); OAuth2AccessToken accessToken =
   * tokenStore.readAccessToken(tokenValue); tokenStore.removeAccessToken(accessToken); } return
   * getUserByUsername(principal.getName()); }
   */

  @Override
  @Transactional
  @Privileges(name = "USER-DELETE", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public int delete(String organizationId, String id) {
    match(organizationId, id, null);
    if (getCurrentUser().getId().equals(id)) {
      throw new BizException("不能删除自己");
    }
    if (!allowAccessUser(id)) throw new BizException("没有权限删除该用户");
    return userMapper.deleteById(id);
  }

  private void deleteInvalidKaptchas() {
    Date now = new Date();
    List<Kaptchas> kaptchas = kaptchaMapper.selectAll();
    for (Kaptchas kaptcha : kaptchas) {
      if (kaptcha.getExpireTime().before(now)) kaptchaMapper.deleteById(kaptcha.getId());
    }
  }

  @Override
  public String getKaptcha(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Set to expire far in the past.
    response.setDateHeader("Expires", 0);
    // Set standard HTTP/1.1 no-cache headers.
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    // Set standard HTTP/1.0 no-cache header.
    response.setHeader("Pragma", "no-cache");

    // return a jpeg
    response.setContentType("image/jpeg");
    kaptchaProducer=new DefaultKaptcha() {
        @Override
        public Config getConfig() {
            return kaptchaConfiguration;
        }
    };
   

    // create the text for the image
    String capText = kaptchaProducer.createText();

    String id = UUID.random();

    // Clean the invalid kaptchas
    deleteInvalidKaptchas();

    response.setHeader("id", id);
    // store the text in the session
   /* Kaptchas record = kaptchaMapper.selectById(id);
    if (null == record) {
      record = new Kaptchas();
      record.setId(id);
      record.setSessionId(id);
      record.setCheckcode(capText);
      record.setExpireTime(DateUtils.addSeconds(new Date(), 60));
      kaptchaMapper.insert(record);
    } else {
      record.setCheckcode(capText);
      record.setExpireTime(DateUtils.addSeconds(new Date(), 60));
      kaptchaMapper.updateByIdSelective(record);
    }*/

    // session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

    // create the image with the text
    BufferedImage bi = kaptchaProducer.createImage(capText);

//    ServletOutputStream out = response.getOutputStream();
    
    // Convert into byte array
    ByteArrayOutputStream baos = new ByteArrayOutputStream();    
    ImageIO.write(bi, "jpg", baos);
    byte[] bytes = baos.toByteArray();
    String images = new Base64Encoder().encode(bytes);
    // write the data out
//    ImageIO.write(bi, "jpg", out);
    try {
      baos.flush();
    } finally {
      baos.close();
    }
    return images.toString();
  }

  @Override
  public Kaptchas receiveKaptcha(String id) {
    Kaptchas kaptchas = kaptchaMapper.selectById(id);
    if (null == kaptchas) {
      return null;
    }
    return kaptchas;
  }

  @Override
  public User match(String organizationId, String userId, User user) {
    User u = user;
    if (user != null && user.getOrganizationId() != null
        && !organizationId.equals(user.getOrganizationId()))
      throw new BizException("用户信息不匹配，请确认！");
    if (userId != null && !(u = check(userId)).getOrganizationId().equals(organizationId))
      throw new BizException("用户信息不匹配，请确认！");
    return u;
  }

  @Override
  public boolean usernameExist(String username) {
    return userMapper.selectByUsername(username) == null ? false : true;
  }

  @Override
  public boolean emailExist(String email) {
    return userMapper.selectByEmail(email) == null ? false : true;
  }

  @Override
  public boolean phoneExist(String phone) {
    return userMapper.selectByPhone(phone) == null ? false : true;
  }

  @Override
  @Transactional
  public boolean hasUserBasedRole(String roleId) {
    return userMapper.selectHasRoleUser(roleId);
  }

  @Override
  public boolean hasUserbasedOrganization(String organizationId) {
    return userMapper.selectHasOrganizationUser(organizationId);
  }
  
  @Override
  public Response<OAuth2AccessToken> getToken(String username, String password) {
    String url = oauth_token_url;
    ParameterizedTypeReference<Response<OAuth2AccessToken>> type = new ParameterizedTypeReference<Response<OAuth2AccessToken>>() {};   
    Map<String,Object> requestParams = Maps.newHashMap();
    requestParams.put("grant_type","password");
    requestParams.put("username", username);
    requestParams.put("password", password);
    
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Basic Q0xJRU5UOlNFQ1JFVA==");
    HttpHelper httpHelper = new HttpHelper();
    Response<OAuth2AccessToken> tokenResult = httpHelper.post(url, type, requestParams, headers);
    return tokenResult;
  }

  @Override
  public User getCurrentUser() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    Principal principal = request.getUserPrincipal();
    return getUserByUsername(principal.getName());
  }

  @Override
  public User getSuperAdmin() {
    return getUserByUsername(SUPER_ADMIN_USERNAME);
  }

 /* @Override
  public Response<OAuth2AccessToken> getToken(String username, String password) {
    String url = oauth_token_url;
    ParameterizedTypeReference<Response<OAuth2AccessToken>> type = new ParameterizedTypeReference<Response<OAuth2AccessToken>>() {};
    LoginEncoder encoder = new LoginEncoder();
    Map<String,Object> requestParams = Maps.newHashMap();
    requestParams.put("grant_type","password");
    requestParams.put("username", encoder.encodeUsername(username));
    requestParams.put("password", encoder.encodePassword(password));
    
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Basic Q0xJRU5UOlNFQ1JFVA==");
    HttpHelper httpHelper = new HttpHelper();
    Response<OAuth2AccessToken> tokenResult = httpHelper.post(url, type, requestParams, headers);
    return tokenResult;
  }*/

  @Override
  public List<Privilege> getPrivilegesOfCurrentUser(Map<String, Integer> excludes) {
    User user = getCurrentUser();
    List<Privilege> list = roleService.getPrivileges(user.getRoleId(), null);
    if (excludes != null) {// 排除此分类的权限
      Iterator<Privilege> iterator = list.iterator();
      while (iterator.hasNext()) {
        Privilege privilege = iterator.next();
        for (Map.Entry<String, Integer> entry : excludes.entrySet()) {
          if (privilege.getCategory().equals(entry.getKey())
              && privilege.getScope() == entry.getValue())
            iterator.remove();
        }
      }
    }
    return list;
  }

  @Override
  public User getUser(String id) {
    return userMapper.selectById(id);
  }




  @Override
  public List<User> getLogUsers() {
    User current = getCurrentUser();
    Role role = current.getRole();
    String domainId = current.getOrganization().getDomainId();
    List<Integer> scopes = new ArrayList<>();
    scopes.add(1);
    scopes.add(2);
    if (role.isSuperAdmin())
      return userMapper.selectUsersBasedScope(scopes, null);
    return userMapper.selectUsersBasedScope(scopes, domainId);
  }

  @Override
  public User getUser(String organizationId, String id) {
    match(organizationId, id, null);
    if (!allowAccessUser(id)) {
      throw new BizException("没有权限访问该用户");
    }
    return userMapper.selectById(id);
  }

  @Override
  public User getUserByUsername(String username) {
    User user = userMapper.selectByUsername(username);
    return user;
  }

  /**
   * (向往专用接口) 获取指定部门的用户
   * 
   * @param organizationIds ：部门id列表
   */
  @Override
  public List<User> getUsersBaseOrganizations(List<String> organizationIds) {
    User current = getCurrentUser();
    Role role = current.getRole();
    String domainId = current.getOrganization().getDomainId();
    List<Organization> orgs = new ArrayList<>();
    Organization o = null;
    for (String organizationId : organizationIds) {
      o = organizationService.check(organizationId);
      if (!role.isSuperAdmin() && !domainId.equals(o.getDomainId()))
        throw new BizException("没有权限获取id = " + organizationId);
      o.setId(organizationId);
      orgs.add(o);
    }
    return userMapper.selectStatusUsersBasedOrganizationsAndRole(orgs, null, null, null);
  }

  

  @Override
  @Privileges(name = "USER-LIST-VIEW", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public PageInfo<User> getUsers(String organizationId, String roleId, Integer status,
      Integer pageNum, Integer pageSize, String search) {
    List<Organization> orgs = organizationService.getOrganizations(null);
    if (orgs.size() == 0) throw new BizException("不存在可见的用户");
    User u = getCurrentUser();
    Role r = u.getRole();
    if (!r.isOrganizationAdmin() && !r.isOperationsAdmin() && !r.isMaintainsAdmin()
        && !r.isSuperAdmin()) {
      orgs.remove(u.getOrganization()); // 非管理员无法看到本部门的其他用户
    }
    PageInfo<User> pages = null;
    if (organizationId != null) {
      Organization o = organizationService.check(organizationId);
      if (!orgs.contains(o)) { // 不可见该部门
        if (u.getOrganizationId().equals(organizationId)) { // 非管理员查看本部门
          if ((roleId != null && !u.getRoleId().equals(roleId))
              || (status != null && u.getState() != status)) {
            pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
              @Override
              public void doSelect() {}
            });
          } else {
            pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {

              @Override
              public void doSelect() {
                userMapper.selectById(u.getId());
              }
            });
          }
        } else
          throw new BizException("没有权限查看该部门的用户");
      } else { // 有权查看该部门
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
          @Override
          public void doSelect() {
            userMapper.selectStatusUsersBasedOrganizationAndRole(organizationId, roleId, status,
                search);
          }
        });
      }
    } else {
      if (orgs.size() == 0) {
        if ((roleId != null && !u.getRoleId().equals(roleId))
            || (status != null && u.getState() != status)) {
          pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {

            @Override
            public void doSelect() {}
          });
        } else {
          pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
              userMapper.selectById(u.getId());
            }
          });
        }
        return pages;
      }
      if (r.isOperationsUser()) {
        if (roleId != null && roleId.equals(u.getRoleId())) {
          if (status != null && u.getState() != status)
            pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {

              @Override
              public void doSelect() {}
            });
          else
            pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
              @Override
              public void doSelect() {
                userMapper.selectById(u.getId());
              }
            });
        } else {
          pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
              userMapper.selectStatusUsersBasedOrganizationsAndRole(orgs, roleId, status, search);
            }
          });
        }
      } else {
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {

          @Override
          public void doSelect() {
            userMapper.selectStatusUsersBasedOrganizationsAndRole(orgs, roleId, status, search);
          }

        });
      }
    }
    return pages;
  }

  @Override
  public List<User> getUsers(String organizationId, String roleId, Integer status, String search) {
    List<User> list = new ArrayList<>();
    List<Organization> orgs = organizationService.getOrganizations(null); // 获取用户可见的部门列表
    if (orgs.size() == 0) throw new BizException("不存在可见的用户");
    User u = getCurrentUser();
    Role r = u.getRole();
    if (!r.isOrganizationAdmin() && !r.isOperationsAdmin() && !r.isMaintainsAdmin()
        && !r.isSuperAdmin()) {
      orgs.remove(u.getOrganization()); // 非管理员无法看到本部门的其他用户
      list.add(u); // 可以查看自己
    }
    if (orgs.size() > 0) // 必须加此判断，否则会查询所有用户
      list.addAll(userMapper.selectStatusUsersBasedOrganizationsAndRole(orgs, null, null, search));
    Iterator<User> iterator = list.iterator();
    if (roleId != null) // 根据角色过滤
      while (iterator.hasNext())
      if (!iterator.next().getRoleId().equals(roleId)) iterator.remove();

    if (organizationId != null) // 根据部门过滤
      while (iterator.hasNext())
      if (!iterator.next().getOrganizationId().equals(organizationId)) iterator.remove();

    if (status != null) // 根据状态过滤
      while (iterator.hasNext())
      if (iterator.next().getState() != status) iterator.remove();
    return list;
  }

  @Override
  @Transactional
  @Privileges(name = "USER-UPDATE", scope = {1, 2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public User update(String organizationId, String id, User user) {
    User u = match(organizationId, id, user);
    if (!allowAccessUser(id)) throw new BizException("没有权限更新该用户");

    String username = user.getUsername();
    if (username != null && !u.getUsername().equals(username)) { // 如果修改名字则需去查询是否重名
      throw new BizException("用户名不可修改");
    }
    // TODO 去掉唯一性限制
    /**
    if (user.getEmail() != null && !user.getEmail().equals(u.getEmail())
        && emailExist(user.getEmail()))
      throw new BizException("该电子邮箱已经存在，请使用其他邮箱");
    if (user.getPhone() != null && !user.getPhone().equals(u.getPhone())
        && phoneExist(user.getPhone()))
      throw new BizException("该手机号码已经存在，请使用其他联系方式");
      */
    u.setPassword(null); //确保密码不会被修改
    u.setName(user.getName() == null ? null : user.getName());
    u.setEmail(user.getEmail() == null ? null : user.getEmail());
    u.setPhone(user.getPhone() == null ? null : user.getPhone());
    u.setDescription(user.getDescription() == null ? null : user.getDescription());
    u.setUpdatedAt(new Date());
    userMapper.updateByIdSelective(u);
    return u;
  }

  @Override
  @Transactional
  public User updateSelf(User user) {
    User self = getCurrentUser();
    self.setPassword(null);
    self.setName(user.getName() == null ? null : user.getName());
    self.setEmail(user.getEmail() == null ? null : user.getEmail());
    self.setPhone(user.getPhone() == null ? null : user.getPhone());
    self.setDescription(user.getDescription() == null ? null : user.getDescription());
    
    //TODO 去掉唯一性限制
    /**
     * 
    if (user.getEmail() != null && !user.getEmail().equals(self.getEmail())
        && emailExist(user.getEmail()))
      throw new BizException("该电子邮箱已经存在，请使用其他邮箱");
    if (user.getPhone() != null && !user.getPhone().equals(self.getPhone())
        && phoneExist(user.getPhone()))
      throw new BizException("该手机号码已经存在，请使用其他联系方式");
      */
    self.setUpdatedAt(new Date());
    userMapper.updateByIdSelective(self);
    return self;
  }

  @Override
  @Transactional
  public User updatePassword(String organizationId, String id, String oldPassword,
      String newPassword) {
    match(organizationId, id, null);
    User u = getCurrentUser();
    if (!id.equals(u.getId())) throw new BizException("只能更新自己的密码，无法更改其他用户密码！");
    validate(oldPassword);
    u.setPassword(newPassword);
    userMapper.updateByIdSelective(u);
    return u;
  }


}
