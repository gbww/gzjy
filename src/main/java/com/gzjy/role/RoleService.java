package com.gzjy.role;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.Role;
import com.gzjy.role.model.Role.RoleScope;

public interface RoleService {

  /**
   * 新建角色
   * 
   * @param role : Role对象
   * @return Role对象
   */
  Role add(CrabRole role);

  /**
   * 为角色赋权限
   * 
   * @param id ：角色id
   * @param privileges : 权限id列表
   * @return 赋予的权限数目
   */
  int assignPrivilegeForRole(String id, List<String> privileges);
  
  int initPrivilegeForDefaultRole(String id, List<String> privileges);

  /**
   * 检查对应id的记录
   * 
   * @param id ：角色id
   * @return 存在返回角色记录，否则为null
   */
  Role check(String id);

  /**
   * 判断是否存在指定部门的角色
   * 
   * @param organizationId ：部门id
   * @return 存在与否
   */
  boolean hasRoleInOrganization(String organizationId);

  /**
   * 根据主键id删除记录
   * 
   * @param id ：主键id
   * @return 删除记录数
   */
  int delete(String id);

  /**
   * 根据主键id更新记录
   * 
   * @param id ：主键id
   * @param role ：Role对象
   * @return 角色记录
   */
  Role update(String id, CrabRole role);

  /**
   * 根据主键id查询记录
   * 
   * @param id ：主键id
   * @return 角色记录
   */
  Role getById(String id);

  /**
   * 获取默认管理员角色
   * 
   * @param scope ：角色类型
   * 
   * @return 默认管理员
   */
  Role getDefaultAdminRole(RoleScope scope);

  /**
   * 获取默认普通用户角色
   * 
   * @param scope ：角色类型
   * @return
   */
  Role getDefaultUserRole(RoleScope scope);

  /**
   * 分页查询该角色的所有权限
   * 
   * @param id ：角色id
   * @param category ：权限分类
   * @param pageNum ：查看的页数
   * @param pageSize ：每页显示记录
   * @return 权限列表
   */
  PageInfo<Privilege> getPrivileges(String id, String category, Integer pageNum, Integer pageSize);

  /**
   * 查询该角色的所有权限
   * 
   * @param id ：角色id
   * @param category ：权限 分类
   * @return 权限列表
   */
  List<Privilege> getPrivileges(String id, String category);

  /**
   * 分页查询可见的角色列表
   * @param organizationId ：部门id
   * @param pageNum ：查看的页数
   * @param pageSize ：每页显示记录
   * @return 角色列表
   */
  PageInfo<Role> getRoles(String organizationId, Integer pageNum, Integer pageSize);

  /**
   * 查询可见的角色列表
   * 
   * @return 角色列表
   */
  List<Role> getRoles(String organizationId);
  
  /**
   * 获取可见的角色列表
   * @param organizationId
   * @return
   */
  List<CrabRole> getCrabRoles(String organizationId);

  /**
   * 删除指定部门下的角色
   * @param organizationId ：部门id
   * @return
   */
  int deleteByOrganizationId(String id);
}
