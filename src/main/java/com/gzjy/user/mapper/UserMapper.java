package com.gzjy.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.organization.model.Organization;
import com.gzjy.user.model.User;


@Mapper
public interface UserMapper {
  /**
   * 根据主键id删除记录
   * 
   * @param id ：主键id
   * @return 删除记录数
   */

  int deleteById(String id);

  /**
   * 插入记录
   * 
   * @param record ：User对象 {@link User}
   * @return 插入记录数
   */
  int insert(User record);

  /**
   * 插入记录
   * 
   * @param record ：User对象 {@link User}
   * @return 插入记录数
   */
  int insertSelective(User record);

  /**
   * 通过主键id查询记录
   * 
   * @param id ：主键id
   * @return 用户记录
   */
  User selectById(String id);

  /**
   * 查询是否存在关联该角色的用户
   * 
   * @param roleId ：角色id
   * @return 存在未true，否则为false
   */
  boolean selectHasRoleUser(String roleId);

  /**
   * 查询是否存在该部门的用户
   * 
   * @param organizationId ：部门id
   * @return 存在未true，否则为false
   */
  boolean selectHasOrganizationUser(String organizationId);

  /**
   * 查询所有用户记录
   * 
   * @return 用户列表
   */
  List<User> selectAll();
  
  /**
   * 查询对应平台的用户
   * @param scopes ：1表示自服务，2表示运营，3表示运维
   * @param domainId 
   * @return
   */
  List<User> selectUsersBasedScope(@Param("scopes")List<Integer> scopes);
  
  /**
   * 查询指定部门和角色的指定状态用户
   * @param organizationId ：部门id
   * @param roleId ：角色id
   * @param status ：状态
   * @return 用户列表
   */
  List<User> selectStatusUsersBasedOrganizationAndRole(@Param("organizationId")String organizationId, @Param("roleId")String roleId, @Param("status")Integer status, @Param("username")String username);
  
  /**
   * 查询指定部门列表中指定角色的指定状态 用户
   * @param organizations ：部门列表
   * @param roleId ：角色id
   * @param status ：状态
   * @return 用户列表
   */
  List<User> selectStatusUsersBasedOrganizationsAndRole(@Param("organizations")List<Organization> organizations, @Param("roleId")String roleId, @Param("status")Integer status, @Param("username")String username);
  
  List<User> seletTest();
  /**
   * 更新记录
   * 
   * @param record ：User对象 {@link User}
   * @return 更新记录数
   */
  int updateByIdSelective(User record);

  /**
   * 更新记录
   * 
   * @param record ：User对象 {@link User}
   * @return 更新记录数
   */
  int updateById(User record);

  /**
   * 通过用户名查询记录
   * 
   * @param username ：用户名
   * @return 用户记录
   */
  User selectByUsername(String username);
  
  /**
   * 通过邮箱查询
   * @param email ：邮箱
   * @return
   */
  User selectByEmail(String email);

  /**
   * 通过手机号查询
   * @param phone ：手机号
   * @return
   */
  User selectByPhone(String phone);
}
