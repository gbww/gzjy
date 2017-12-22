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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.organization.service.OrganizationService;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.RoleService;
import com.gzjy.role.model.Role;
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
  @Transactional
  public User initDefaultAdminForOrganization(User user) {
   
    user.setPassword(user.getPassword());
    userMapper.insert(user);
    return user;
  }

  @Override
  @Transactional
  public User add(String organizationId, User user) {
    // FIXME
    match(organizationId, null, user);
    if (usernameExist(user.getUsername())) throw new BizException("该用户名已经存在，请使用其他名称");
    // if (emailExist(user.getEmail())) throw new BizException("该邮箱已经存在，请使用其他邮箱");
     if (nameExist(user.getName())) throw new BizException("该用户名姓名已经存在");
    String roleId = user.getRoleId();
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
  @Transactional
  public User activate(String organizationId, String id) {
    User u = match(organizationId, id, null);
    if (id.equals(getCurrentUser().getId())) throw new BizException("无法激活自己");
    
    u.setId(id);
    u.setState(UserStatus.ACTIVATE.getCode());
    userMapper.updateByIdSelective(u);
    return u;
  }

  @Override
  @Transactional
  public User suspend(String organizationId, String id) {
    User u = match(organizationId, id, null);
    if (id.equals(getCurrentUser().getId())) throw new BizException("无法挂起自己");
    
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
  public int delete(String organizationId, String id) {
    match(organizationId, id, null);
    if (getCurrentUser().getId().equals(id)) {
      throw new BizException("不能删除自己");
    }

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
  public boolean nameExist(String name) {
    return userMapper.selectByName(name) == null ? false : true;
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
 public List<User> getReviewers(){
     String  roleId="f7a026cd281c41bb832dc7ce4f0468a2";
      List<User> users=userMapper.selectByRoleId(roleId);
      return users;
  }
  
  @Override
  public PageInfo<User> getUsers(Integer pageNum, Integer pageSize, String order, String name) {
    List<User> list = new ArrayList<User>();
    PageInfo<User> pages = new PageInfo<User>(list);
    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
          userMapper.selectAll(name, order);
        }
    });
  
        return pages;
  }




  @Override
  public List<User> getLogUsers() {
    User current = getCurrentUser();
    Role role = current.getRole();
    
    List<Integer> scopes = new ArrayList<>();
    scopes.add(1);
    scopes.add(2);
    if (role.isSuperAdmin())
      return userMapper.selectUsersBasedScope(scopes);
    return userMapper.selectUsersBasedScope(scopes);
  }

  @Override
  public User getUser(String organizationId, String id) {
    match(organizationId, id, null);
    return userMapper.selectById(id);
  }

  @Override
  public User getUserByUsername(String username) {
    User user = userMapper.selectByUsername(username);
    return user;
  }



  


 

  @Override
  @Transactional
  public User update(String organizationId, String id, User user) {
    User u = match(organizationId, id, user);

    String username = user.getUsername();
    if (username != null && !u.getUsername().equals(username)) { // 如果修改名字则需去查询是否重名
      throw new BizException("用户名不可修改");
    }

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

/* 
 * @see com.gzjy.user.UserService#selectUsersBasedOrganization(java.lang.String)
 */
@Override
public List<User> selectUsersBasedOrganization(String organizationId) {
   
    return  userMapper.selectUsersBasedOrganization(organizationId);
}













}
