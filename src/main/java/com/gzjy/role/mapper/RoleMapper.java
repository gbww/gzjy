package com.gzjy.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.Role;


@Mapper
public interface RoleMapper {
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
   * @param record ：Role对象 {@link CrabRole}
   * @return 插入记录数
   */
  int insert(CrabRole record);

  /**
   * 插入记录
   * 
   * @param record ：Privilege对象 {@link Privilege}
   * @return 插入记录数
   */
  int insertSelective(CrabRole record);
  
  /**
   * 运营管理员获取其可见的角色列表
   * @param organizationId ：运营管理员所在部门的id
   * @return
   */
  //List<Role> selectRolesByOperationAdmin(@Param("organizationId") String organizationId);
  
  /**
   * 运营管理用户获取其可见的角色列表
   * @param roleId ：运营普通用户的角色id
   * @return
   */
  //List<Role> selectRolesByOperationUser(@Param("roleId") String roleId);
  
  /**
   * 查询部门角色
   * @param scope ：角色类型
   * @param organizationId ：部门id
   * @return 角色列表
   */
  List<Role> selectRolesBasedOrganization(@Param("scope") int scope, @Param("organizationId") String organizationId);
  
  /**
   * 查询不包含全局角色的部门角色
   * @param organizationId ：部门id
   * @return 角色列表
   */
  List<Role> selectRolesBasedOrganizationExceptGlobalRoles(@Param("organizationId") String organizationId);
  
  /**
   * 查询是否存在该部门的角色
   * 
   * @param organizationId ：部门id
   * @return 存在未true，否则为false
   */
  Boolean selectHasOrganizationRole(String organizationId);

  /**
   * 查询所有角色
   * 
   * @return 角色列表
   */
  List<Role> selectAll();

  /**
   * 查询对应范围的全局角色
   * @param scope 角色类型
   * @return
   */
  List<Role> selectGlobalRoles(int scope);
  
  /**
   * 根据admin属性、默认角色属性以及角色类型查询角色
   * 
   * @param scope ：角色类型
   * @return 角色记录
   */
  CrabRole selectDefaultsAdminRole(@Param("scope") int scope);
  
  /**
   * 查询默认普通用户角色
   * @param scope ：角色类型
   * @return
   */
  CrabRole selectDefaultsUserRole(@Param("scope") int scope);

  /**
   * 通过主键id查询记录
   * 
   * @param id ：主键id
   * @return 角色记录
   */
  CrabRole selectById(String id);

  /**
   * 通过角色名称查询角色记录
   * 
   * @param name ：角色名称
   * @return 角色记录
   */
  CrabRole selectByName(String name);
  
  CrabRole selectByNameBasedOrganization(@Param("scope") int scope, @Param("organizationId") String organizationId, @Param("name") String name);

  /**
   * 更新记录
   * 
   * @param record ：Role对象 {@link CrabRole}
   * @return 更新记录数
   */
  int updateByIdSelective(CrabRole record);

  /**
   * 更新记录
   * 
   * @param record ：Role对象 {@link CrabRole}
   * @return 更新记录数
   */
  int updateById(CrabRole record);
}
