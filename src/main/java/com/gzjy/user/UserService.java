package com.gzjy.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.user.model.Kaptchas;
import com.gzjy.user.model.User;


public interface UserService {

  /**
   * 检查指定id的用户
   * @param id ：用户id
   * @return 存在返回用户，否则为null
   */
  User check(String id);

  /**
   * 验证当前用户密码是否正确
   * @param password ：密码
   * @return 验证用户
   */
  User validate(String password);

  /**
   * 判断当前用户是否允许访问该用户
   * 
   * @param id ：用户id
   * @return 允许为true，否则为false
   */
  boolean allowAccessUser(String id);

  /**
   * 判断当前用户是否允许创建用户
   * @param organizationId ：部门id
   * @param user ：新建用户
   * @return 允许为true，否则为false
   */
  boolean allowCreateUser(String organizationId, User user);

  /**
   * 初始化新增部门管理员
   * @param user ：部门管理员
   * @return 部门管理员
   */
  User initDefaultAdminForOrganization(User user);

  /**
   * 新增用户
   * @param organizationId ：部门id
   * @param user ：新增用户
   * @return 新增用户
   */
  User add(String organizationId, User user);

  
  
  /**
   * 为用户分配角色
   * 
   * @param organizationId ：部门id
   * @param userId : 用户id
   * @param roleId : 角色id id
   * @return
   */
  User assignRoleForUser(String organizationId, String userId, String roleId);

  /**
   * 为用户换部门
   * @param id ：用户id
   * @param organizationFromId ：旧部门id
   * @param organizationToId ：新部门id
   * @param roleId ：角色id
   * @return 用户
   */
  User changeOrganizationForUser(String id, String organizationFromId, String organizationToId,
      String roleId);

  /**
   * 激活用户
   * 
   * @param organizationId ：部门id
   * @param id : 用户id
   * @return 激活用户
   */
  User activate(String organizationId, String id);

  /**
   * 挂起用户
   * @param organizationId ：部门id
   * @param id ：用户id
   * @return 挂起用户
   */
  User suspend(String organizationId, String id);

  /**
   * 注销登录用户
   * @return 登录用户
   */
  //User logout();

  /**
   * 删除用户
   * @param organizationId ：部门id
   * @param id ：用户id
   * @return 删除记录数
   */
  int delete(String organizationId, String id);

  /**
   * 判断 organization id 和 user是否匹配
   * 
   * @param organizationId ：部门id
   * @param userId ：用户id
   * @param user ：用户
   * @return 匹配与否
   */
  User match(String organizationId, String userId, User user);

  /**
   * 判断是否重名
   * @param username ：用户名
   * @return
   */
  boolean usernameExist(String username);
  
  /**
   * 判断email是否存在
   * @param email ：电子邮箱
   * @return
   */
  boolean emailExist(String email);

  /**
   * 判断手机号是否重复
   * @param phone ：手机号
   * @return
   */
  boolean phoneExist(String phone);

  /**
   * 判断是否存在对应role id的关联用户
   * 
   * @param roleId
   * @return 是否存在
   */
  boolean hasUserBasedRole(String roleId);

  /**
   * 判断该部门是否存在用户
   * 
   * @param organizationId
   * @return 是否存在
   */
  boolean hasUserbasedOrganization(String organizationId);

  /**
   * 获取当前用户
   * @return 当前用户
   */
  User getCurrentUser();
  
  User getSuperAdmin();
  
  //OAuth2AccessToken getToken(String username, String password);
  
  /**
   * 获取当前用户的权限列表
   * @param excludes ：排除该分类
   * @return
   */
  List<Privilege> getPrivilegesOfCurrentUser(Map<String, Integer> excludes);

  /**
   * 获取用户
   * @param id ：用户id
   * @return 用户
   */
  User getUser(String id);
  
  
  
 

  /**
   * 获取用户
   * @param organizationId ：部门id
   * @param id ：用户id
   * @return 用户
   */
  User getUser(String organizationId, String id);
  
  /**
   * 运营用户获取日志管理的用户
   * @return 用户
   */
  List<User> getLogUsers();
    
  /**
   * 通过用户名获取用户
   * @param username ：用户名
   * @return 用户
   */
  User getUserByUsername(String username);
  

  
  String getKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException;
  
  Kaptchas receiveKaptcha(String id);
  
  /**
   * 获取指定部门的用户
   * @param organizationIds ：部门id列表
   * @return 用户列表
   */
  public List<User> getUsersBaseOrganizations(List<String> organizationIds);
  
  /**
   * 分页查询当前用户可见的用户列表
   * @param organizationId ：部门id
   * @param roleId ：角色id
   * @param status ：用户状态
   * @param pageNum ：查看的页数
   * @param pageSize ：每页显示记录
   * @return
   */
  PageInfo<User> getUsers(String organizationId, String roleId, Integer status, Integer pageNum, Integer pageSize, String search);

  /**
   * @param organizationId ：部门id
   * @param roleId ：角色id
   * @param status ：用户状态
   * 查询当前用户可见的用户列表
   * @return 用户列表
   */
  List<User> getUsers(String organizationId, String roleId, Integer status, String search);

  /**
   * 更新用户
   * @param organizationId ：部门id
   * @param id ：用户id
   * @param user ：更新用户
   * @return 更新用户
   */
  User update(String organizationId, String id, User user);
  
  /**
   * 更新自己的信息
   * @param user ：更新用户
   * @return
   */
  User updateSelf(User user);
  
  /**
   * 更新密码
   * @param organizationId ：部门id
   * @param id ：用户id
   * @param oldPassword ：旧密码
   * @param newPassword ：新密码
   * @return
   */
  User updatePassword(String organizationId, String id, String oldPassword, String newPassword);

  Response<OAuth2AccessToken> getToken(String username, String password);
  
  /**
   * Epc密文加密方法
   * @param username : 
   * @return
   */
  String encode(String str, String key);
}
