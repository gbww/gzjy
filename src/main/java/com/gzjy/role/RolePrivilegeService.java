package com.gzjy.role;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.privilege.PrivilegeService;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.mapper.RolePrivilegeMapper;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.RolePrivilege;

@Service
public class RolePrivilegeService {
  @Autowired
  private RolePrivilegeMapper rolePrivilegeMapper;
  
  @Autowired
  private RoleService roleService;
  
  @Autowired
  private PrivilegeService privilegeService;

  /**
   * 检查权限列表是否存在,并且判断权限是否符合欲赋角色的类型
   * @param roleId ：角色id
   * @param privileges ：权限id列表
   * @return 存在与否
   */
  public List<String> checkPrivileges(String roleId, List<String> privileges){
    //FIXME
    int roleScope = ((CrabRole) roleService.check(roleId)).getScope();
    Privilege p = null;
    List<String> list = new ArrayList<>();
    List<Privilege> ps = new ArrayList<>();
    for(String privilege : privileges){
      if ((p = privilegeService.check(privilege)).getScope() != roleScope)
        throw new BizException("无法分配该权限： " + p.getDisplayName());
      if (!p.getView() && !list.contains(p.getCategory())) {
        list.add(p.getCategory());
        ps.addAll(privilegeService.getViewPrivileges(p.getCategory(), p.getScope()));
      }
    }
    for (Privilege privilege : ps) {
      String pId = privilege.getId();
      if (!privileges.contains(pId))
        privileges.add(pId);
    }
    return privileges;
  }

  /**
   * 批量插入
   * @param list ：RolePrivilege集合
   * @return 插入记录数
   */
  private int batchInsert(List<RolePrivilege> list){
    return rolePrivilegeMapper.batchInsert(list);
  }

  /**
   * 将角色id与权限id列表转换成RolePrivilege列表
   * 
   * @param roleId : 角色id
   * @param privileges : 欲关联的权限id列表
   * @return RolePrivilege列表
   */
  @Transactional
  private List<RolePrivilege> convert(String roleId, List<String> privileges) {
    roleService.check(roleId);
    RolePrivilege rolePrivilege = null;
    List<RolePrivilege> list = new ArrayList<RolePrivilege>();
    for (String privilegeId : privileges) {
      rolePrivilege = new RolePrivilege();
      rolePrivilege.setId(UUID.random());
      rolePrivilege.setRoleId(roleId);
      rolePrivilege.setPrivilegeId(privilegeId);
      list.add(rolePrivilege);
    }
    return list;
  }
  
  /**
   * 为角色分配权限
   * @param roleId ：角色id
   * @param privileges ：权限id列表
   * @param check ：是否检查权限列表
   * @return 权限数目
   */
  @Transactional
  public int assignPrivilegeForRole(String roleId, List<String> privileges) {
   // privileges = checkPrivileges(roleId, privileges);
    deleteByRoleId(roleId);
    if (privileges.size() == 0)
      return 0;
    return batchInsert(convert(roleId, privileges));
  }

  /**
   * 通过角色id删除绑定权限
   * @param roleId ：角色id
   * @return 删除记录数
   */
  @Transactional
  public int deleteByRoleId(String roleId) {
    int result = rolePrivilegeMapper.deleteByRoleId(roleId);
    return result;
  }

  /**
   * 查询角色的指定资源分类列表
   * @param roleId ：角色id
   * @param category ：权限分类（可选，过滤条件）
   * @return
   */
  public List<Privilege> getCategoryPrivilegesByRoleId(@Param("roleId")String roleId, @Param("category")String category){
    List<Privilege> list = rolePrivilegeMapper.selectCategoryPrivilegesByRoleId(roleId, category);
    return list;
  }
  
  /**
   * 通过权限id删除绑定记录
   * @param roleId ：权限id
   * @return 删除记录数
   */
  public int deleteByPrivilegeId(String privilegeId) {
    int result = rolePrivilegeMapper.deleteByPrivilegeId(privilegeId);
    return result;
  }
}
