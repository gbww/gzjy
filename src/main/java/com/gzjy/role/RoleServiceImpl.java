package com.gzjy.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.organization.model.Organization;
import com.gzjy.organization.service.OrganizationService;
import com.gzjy.privilege.PrivilegeService;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.mapper.RoleMapper;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.Role;
import com.gzjy.role.model.Role.RoleIdentity;
import com.gzjy.role.model.Role.RoleScope;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private UserService userService;
  @Autowired
  private RolePrivilegeService rolePrivilegeService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private PrivilegeService privilegeService;


  /**
   * 判断当前用户是否允许访问该角色
   * 
   * @param id ：角色id
   * @return 允许为true，否则为false
   */
  private boolean allowAccessRole(String id) {
    CrabRole role = (CrabRole) check(id);
    User user = userService.getCurrentUser();
    CrabRole r = user.getRole();
    if (r.isSuperAdmin()||r.isAnHuiAdmin()) 
      return true;       //超管
    return false;
  }

  /**
   * 判断当前用户是否允许操作该角色
   * 
   * @param id ：角色id
   * @return 允许为true，否则为false
   */
  private boolean allowManipulateRole(String id) {
    CrabRole role = (CrabRole) check(id);
    User user = userService.getCurrentUser();
    CrabRole r = user.getRole();
    if (r.isSuperAdmin()||r.isAnHuiAdmin()) 
      return true;       //超管
   
    return false;
  }

  @Override
  @Transactional
  public Role add(CrabRole role) {
  //  User user = userService.getCurrentUser();
    //CrabRole roleOfUser = user.getRole();
   // Integer scope = role.getScope(); // 获取scope参数
    Integer scope =1;
    String organizationId = role.getOrganizationId(); // 获取用户部门
    if (role.getDefaults()) {
      throw new BizException("无法创建默认角色");
    }
  
    role = (CrabRole) CrabRole.builder().from(role).id(UUID.random()).scope(scope)
        .organizationId(organizationId).createdAt(new Date()).updatedAt(new Date()).build();
    roleMapper.insert(role);
    return role;
  }

  @Override
  @Transactional
  public int assignPrivilegeForRole(String id, List<String> privileges) {
    /*if (!allowManipulateRole(id)) {
      throw new BizException("没有权限为该角色分配权限");
    }*/
    Role role = check(id);
    if (role.getDefaults()) {
      throw new BizException("无法为默认角色重新分配权限");
    }
   /* if (role.getOrganizationId().equals("-1")
        && !userService.getCurrentUser().getRole().isSuperAdmin()) { // 非超级管理员不能修改全局角色
      throw new BizException("当前用户没有权限为该角色重新分配权限");
    }*/
    // FIXME 完善分配权限（检查权限是否存在，是否允许分配指定权限）
    return rolePrivilegeService.assignPrivilegeForRole(id, privileges);
  }

 
  @Override
  public int initPrivilegeForDefaultRole(String id, List<String> privileges) {
      Role role=check(id);
    // FIXME 完善分配权限（检查权限是否存在，是否允许分配指定权限）
    if (role.getDefaults()) {
        throw new BizException("不能修改默认角色");
      }
    return rolePrivilegeService.assignPrivilegeForRole(id, privileges);
  }

  @Override
  public Role check(String id) {
    Role r = roleMapper.selectById(id);
    if (r == null) {
      throw new BizException("不存在该角色！");
    }
    return r;
  }

  /**
   * 判断角色名称是否已经存在
   * 
   * @param name ：角色名称
   * @return 存在为ture，否则为false
   */
  private boolean nameExist(int scope, String organizationId, String name) {
    Role role = roleMapper.selectByNameBasedOrganization(scope, organizationId, name);
    return role == null ? false : true;
  }

  @Override
  public boolean hasRoleInOrganization(String organizationId) {
    return roleMapper.selectHasOrganizationRole(organizationId);
  }

  @Override
  @Transactional
  public int delete(String id) {
  /*  if (!allowManipulateRole(id)) {
      throw new BizException("没有权限删除该角色");
    }*/
    Role role = check(id); // 获取对应id的角色
    if (role.getDefaults()) {
      throw new BizException("不能删除默认角色");
    }
    /*if (role.getOrganizationId().equals("-1")
        && !userService.getCurrentUser().getRole().isSuperAdmin()) { // 非超级管理员不能删除全局角色
      throw new BizException("没有权限删除该角色");
    }*/

    if (userService.hasUserBasedRole(id)) {
      throw new BizException("请确保该角色没有任何关联用户");
    }
    rolePrivilegeService.deleteByRoleId(id);
    return roleMapper.deleteById(id);
  }

  @Override
  public int deleteByOrganizationId(String organizationId) {
   
    List<Role> roles = roleMapper.selectRolesBasedOrganizationExceptGlobalRoles(organizationId);
    for (Role r : roles) {
      rolePrivilegeService.deleteByRoleId(r.getId());
      roleMapper.deleteById(r.getId());
    }
    return roles.size();
  }

  @Override
  @Transactional
  public Role update(String id, CrabRole role) {
  /*  if (!allowManipulateRole(id)) {
      throw new BizException("没有权限更新该角色");
    }*/

    CrabRole r = (CrabRole) check(id); // 取得指定id的原纪录
    if (r.getDefaults()) {
      throw new BizException("默认角色不能修改");
    }
    String name = role.getName();
    if (!r.getName().equals(name) && nameExist(r.getScope(), r.getOrganizationId(), name)) { // 如果修改名字则需去查询是否重名
      throw new BizException("该角色名称已经存在，请使用其他名称");
    }
    role = (CrabRole) CrabRole.builder().from(r).name(name).description(role.getDescription())
        .updatedAt(new Date()).build();
    roleMapper.updateByIdSelective(role);
    return role;
  }

  @Override
  public Role getById(String id) {
   /* if (!allowAccessRole(id)) {
      throw new BizException("没有权限访问该角色");
    }*/
    Role role = roleMapper.selectById(id);
    return role;
  }

  @Override
  public Role getDefaultAdminRole(RoleScope scope) {
    Role role = roleMapper.selectDefaultsAdminRole(scope.getCode());
    return role;
  }

  @Override
  public Role getDefaultUserRole(RoleScope scope) {
    return roleMapper.selectDefaultsUserRole(scope.getCode());
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public PageInfo<Privilege> getPrivileges(String id, String category, Integer pageNum, Integer pageSize) {
    CrabRole role = (CrabRole) getById(id);
    PageInfo<Privilege> pages = null;
    if (!role.isSuperAdmin() && !role.isAnHuiAdmin()) {
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
          rolePrivilegeService.getCategoryPrivilegesByRoleId(role.getId(), category);
        }
      });
      return pages;
    }
    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
      @Override
      public void doSelect() {
        privilegeService.getPrivilegesByAdmin(category, role.getScope());
      }
    });
    return pages;
  }

  @Override
  public List<Privilege> getPrivileges(String id, String category) {
    CrabRole role = (CrabRole) getById(id);
    if (!role.isSuperAdmin() && !role.isAnHuiAdmin()) {
      return rolePrivilegeService.getCategoryPrivilegesByRoleId(id, category);
    }
    return privilegeService.getPrivilegesByAdmin(category, role.getScope());
  }

  @Override
  public PageInfo<Role> getRoles(String organizationId, Integer pageNum, Integer pageSize) {
    PageInfo<Role> pages;
    
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
        
          roleMapper.selectAll();
           
          
        }
      });
  
    return pages;
  }

  @Override
  public List<Role> getRoles(String organizationId) {
    List<Role> roles = new ArrayList<>();
        roles = roleMapper.selectAll();
 
    return roles;
  }

  @Override
  public List<CrabRole> getCrabRoles(String organizationId) {
    List<Role> roles = getRoles(organizationId);
    List<CrabRole> crabRoles = new ArrayList<>();
    for (Role role : roles) {
      crabRoles.add((CrabRole) role);
    }
    return crabRoles;
  }
}
