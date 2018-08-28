package com.gzjy.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.RolePrivilege;
@Mapper
public interface RolePrivilegeMapper {
  /**
   * 根据主键id删除记录
   * 
   * @param id ：主键id
   * @return 删除记录数
   */
  int deleteById(String id);

  /**
   * 根据角色id删除记录
   * 
   * @param roleId ：角色id
   * @return 删除记录数
   */
  int deleteByRoleId(String roleId);

  /**
   * 根据权限id删除记录
   * 
   * @param privilegeId ：权限id
   * @return 删除记录数
   */
  int deleteByPrivilegeId(String privilegeId);

  /**
   * 通过角色id和权限id删除记录
   * 
   * @param roleId ：角色id
   * @param privilegeId ：权限id
   * @return 删除记录数
   */
  int deleteByCombine(String roleId, String privilegeId);

  /**
   * 插入记录
   * 
   * @param record ：RolePrivilege对象 {@link RolePrivilege}
   * @return 插入记录数
   */
  int insert(RolePrivilege record);
  
  /**
   * 批量插入
   * @param list ：RolePrivilege集合
   * @return 插入记录数
   */
  int batchInsert(List<RolePrivilege> list) ;
  
  /**
   * 插入记录
   * 
   * @param record ：RolePrivilege对象 {@link RolePrivilege}
   * @return 插入记录数
   */
  int insertSelective(RolePrivilege record);

  /**
   * 通过主键id查询记录
   * 
   * @param id ：主键id
   * @return 角色权限记录
   */
  RolePrivilege selectById(String id);

  /**
   * 查询所有记录
   * 
   * @return 角色权限列表
   */
  List<RolePrivilege> selectAll();

  /**
   * 查询角色的权限列表
   * 
   * @param category ：权限分类 （可选，过滤条件）
   * @param roleId ：角色id
   * @return 角色的权限列表
   */
  List<Privilege> selectPrivilegesByRoleId(String category, String roleId);

  /**
   * 查询包含该权限的角色列表
   * 
   * @param privilegeId ：权限id
   * @return 角色列表
   */
  List<CrabRole> selectRolesByPrivilegeId(String privilegeId);

  /**
   * 查询是否存在重复记录
   * 
   * @param roleId ：角色id
   * @param privilegeId ：权限id
   * @return 存在是ture，否则为false
   */
  boolean selectIsBothExist(String roleId, String privilegeId);

  /**
   * 更新记录
   * 
   * @param record ：RolePrivilege对象 {@link RolePrivilege}
   * @return 更细记录数
   */
  int updateByIdSelective(RolePrivilege record);

  /**
   * 更新记录
   * 
   * @param record ：RolePrivilege对象 {@link RolePrivilege}
   * @return 更新记录数
   */
  int updateById(RolePrivilege record);
  /**
   * 
   * @param roleId :角色id
   * @param category ：权限分类
   * @return
   */
  List<Privilege> selectCategoryPrivilegesByRoleId(@Param("roleId") String roleId, @Param("category") String category);
}
