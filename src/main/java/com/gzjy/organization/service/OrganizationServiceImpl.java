package com.gzjy.organization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.jni.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.organization.mapper.OrganizationMapper;
import com.gzjy.organization.model.Organization;
import com.gzjy.organization.model.Organization.OrganizationType;
import com.gzjy.role.RoleService;
import com.gzjy.role.model.Role;
import com.gzjy.role.model.Role.RoleIdentity;
import com.gzjy.role.model.Role.RoleScope;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;
import com.gzjy.user.model.User.UserStatus;
import com.gzjy.user.model.UserOrganization;

@Service
public class OrganizationServiceImpl implements OrganizationService {
  @Autowired
  private OrganizationMapper organizationMapper;
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserService userService;

 

  private final String defaultPassword = "123456";


 



  /**
   * 判断部门名称是否存在
   * 
   * @param name ：部门名称
   * @return 存在为true，否则为false
   */
  private boolean nameExist(String name) {
    Organization organization = organizationMapper.selectByName(name);
    return organization == null ? false : true;
  }

  @Override
  public Organization check(String id) {
    Organization o = organizationMapper.selectById(id);
    if (o == null) {
      throw new BizException("不存在该部门！");
    }
    return o;
  }

  /**
   * 判断当前用户是否允许创建部门
   * 
   * @param organization ：部门
   * @return 允许为true，否则为false
   */
  private boolean allowCreateOrganization(Organization organization) {
    OrganizationType type = OrganizationType.getOrganizationType(organization.getType());
    if (type == null || type == OrganizationType.WHOLE) {
      throw new BizException("不存在该类型的部门");
    }
    Role role = userService.getCurrentUser().getRole(); // 获取当前用户角色
    if (role.isOperationsAdmin() || role.isOperationsUser()) { // 运营管理员
      if (type != OrganizationType.ORGANIZATIONS) // 创建自服务部门
        return false;
    }
    return true;
  }

  /**
   * 根据新部门生成部门管理员
   * 
   * @param organizationId ：部门id
   * @param organizationType ：部门类型
   * @param name ：用户姓名
   * @param username ：用户名
   * @param email ：邮箱
   * @param password ：密码
   * @param phone ：电话
   * @return
   */
  private User generateUser(String organizationId, int organizationType, User user) {
    if (userService.usernameExist(user.getUsername())) throw new BizException("该用户名已经存在，请使用其他名称");
    // if (userService.emailExist(user.getEmail())) throw new BizException("该邮箱不规范或已经存在，请使用其他邮箱");
    // if (userService.phoneExist(user.getPhone())) throw new
    // BizException("该手机号不规范或已经存在，请使用其他联系方式");
    user.setId(UUID.random());
    user.setOrganizationId(organizationId);
    OrganizationType type = OrganizationType.getOrganizationType(organizationType);
    switch (type) {
      case ORGANIZATIONS:
        user.setRoleId(roleService.getDefaultAdminRole(RoleScope.ORGANIZATIONS).getId());
        break;
      case OPERATIONS:
        user.setRoleId(roleService.getDefaultAdminRole(RoleScope.OPERATIONS).getId());
        break;
      case MAINTAINS:
        user.setRoleId(roleService.getDefaultAdminRole(RoleScope.MAINTAINS).getId());
        break;
      default:
        break;
    }
    user.setCreatedAt(new Date());
    user.setUpdatedAt(new Date());
    user.setState(UserStatus.ACTIVATE.getCode());
    return user;
  }

 

  private boolean validateRequestBody(UserOrganization userOrganization) {
    Organization organization = userOrganization.getOrganization();
    User user = userOrganization.getUser();
    if (organization.getName() == null
        || !organization.getName().matches("^[\\S\u4e00-\u9fa5]+$")) {
      throw new BizException("部门名称不规范");
    }
    if (organization.getPools() == null || organization.getPools().size() == 0)
      throw new BizException("资源池不能为空");
    // else {
    // List<Pool> pools = userService.getAvailablePools();
    // for (Pool p : organization.getPools()) {
    // boolean flag = false;
    // for (Pool up : pools) {
    // if (up.getId().equals(p.getId()))
    // flag = true;
    // }
    // if (!flag)
    // throw new BizException("当前用户没有" + poolClient.getPool(p.getId()).getName() + "资源池的操作权限");
    // }
    // }
    if (organization.getType() == null) throw new BizException("部门类型不能为空");

    if (user.getUsername() == null || !user.getUsername().matches("^[\\S\u4e00-\u9fa5]+$"))
      throw new BizException("用户名不规范");
    if (user.getEmail() == null
        || !user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))
      throw new BizException("邮箱不规范");
    if (user.getPhone() == null || !user.getPhone().matches("^(1[3-8])\\d{9}$"))
      throw new BizException("手机号不规范");
    if (user.getPassword() == null || !user.getPassword().matches("^[\\S]+$"))
      throw new BizException("密码不规范");

    return true;
  }





 


  @Override
  @Transactional
  @Privileges(name = "ORGANIZATION-ADD", scope = 2, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN})
  public Organization add(UserOrganization userOrganization) {
    validateRequestBody(userOrganization);
    Organization organization = userOrganization.getOrganization();
    User user = userOrganization.getUser();
    if (!allowCreateOrganization(organization)) {
      throw new BizException("没有权限创建部门");
    }

    String organizationName = organization.getName();
    if (nameExist(organizationName)) {
      throw new BizException("该部门名称已经存在，请使用其他名称");
    }

    // process domain
    String domainId = organization.getDomainId();
    User current = userService.getCurrentUser();
    Role currentRole = current.getRole();
    Organization currentOrganization = current.getOrganization();
    if (domainId == null) {
      if (currentRole.isSuperAdmin()) throw new BizException("必须指定域！");
      domainId = currentOrganization.getDomainId();
    } else {
      if (!domainId.equals(currentOrganization.getDomainId()) && !currentRole.isSuperAdmin())
        throw new BizException("没有权限选择该域！");
    }

    organization.setId(UUID.random());
    organization.setCreatedAt(new Date());
    organization.setUpdatedAt(new Date());

    User admin = generateUser(organization.getId(), organization.getType(), user);
    userService.initDefaultAdminForOrganization(admin);
    organizationMapper.insert(organization);
 
    return organization;
  }

  @Override
  public boolean allowAccessOrganization(String id) {
    Organization o = check(id);
    String domainId = o.getDomainId();
    User user = userService.getCurrentUser();
    Organization currentOrg = user.getOrganization();
    String currentDomainId = currentOrg.getDomainId();
    Role role = user.getRole();
    if (role.isSuperAdmin())
      return true; // 超管
    else if (role.isOperationsAdmin() || role.isOperationsUser() || role.isMaintainsAdmin()
        || role.isMaintainsUser()) { // 运营用户
      if ((o.getType() == OrganizationType.ORGANIZATIONS.getCode()
          || o.getId().equals(user.getOrganizationId())) && currentDomainId.equals(domainId))
        return true;
    } else {
      if (o.getId().equals(user.getOrganizationId())) return true;
    }
    // if (getOrganizations().contains(o)) return true;
    return false;
  }

  @Override
  public boolean allowManipulateOrganization(String id) {
    Organization o = check(id);
    String domainId = o.getDomainId();
    User user = userService.getCurrentUser();
    Role role = user.getRole();
    Organization currentOrg = user.getOrganization();
    if (role.isSuperAdmin())
      return true; // 超管
    else if (role.isOperationsAdmin()) { // 运营管理员
      if ((o.getType() == OrganizationType.ORGANIZATIONS.getCode()
          && currentOrg.getDomainId().equals(domainId))
          || o.getId().equals(user.getOrganizationId()))
        return true;
    } else if (role.isOperationsUser()) { // 运营普通用户
      if (o.getType() == OrganizationType.ORGANIZATIONS.getCode()
          && currentOrg.getDomainId().equals(domainId))
        return true;
    } else if (role.isMaintainsAdmin() || role.isOrganizationAdmin()) { // 自服务或运维管理员
      if (o.getId().equals(user.getOrganizationId())) return true;
    }
    // if (getAdminOrganizations().contains(o)) return true;
    return false;
  }

  @Override
  @Transactional
  @Privileges(name = "ORGANIZATION-DELETE", scope = 2, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.OPERATIONS_ADMIN})
  public int delete(String id) {
    if (!allowManipulateOrganization(id)) throw new BizException("没有权限删除该部门");
    if (userService.hasUserbasedOrganization(id)) {
      throw new BizException("该部门下还有用户存在，请先确保不存在任何用户");
    }
    int count = 0;
    roleService.deleteByOrganizationId(id);
    count = organizationMapper.deleteById(id);
    return count;
  }

  @Override
  @Transactional
  @Privileges(name = "ORGANIZATION-UPDATE", scope = {2, 3}, identity = {RoleIdentity.SUPER_ADMIN,
      RoleIdentity.ORGANIZATIONS_ADMIN, RoleIdentity.MAINTAINS_ADMIN})
  public Organization update(String id, Organization organization) {
    if (!allowAccessOrganization(id)) throw new BizException("没有权限更新该部门");

    Organization o = check(id); // 取得指定id的原纪录
    String name = organization.getName();
    if (!o.getName().equals(name) && nameExist(name)) { // 如果修改名字则需去查询是否重名
      throw new BizException("该部门名称已经存在，请使用其他名称");
    }
    organization.setId(id);
    organization.setType(null); // 不能修改部门类型
    organization.setEnabled(null); // 无法不启用部门
    organization.setUpdatedAt(new Date());

    return organization;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Organization getById(String id) {
    if (!allowAccessOrganization(id)) throw new BizException("没有权限访问该部门");
    Organization o = organizationMapper.selectById(id);
  
    return o;
  }

  @Override
  public List<Organization> getOrganizations(String name) {
    User user = userService.getCurrentUser();
    Role role = user.getRole();
    if (role.isSuperAdmin()) { // 超级管理员
      return organizationMapper.selectAll(name);
      // return getAll();
    } else if (role.isOperationsAdmin() || role.isOperationsUser()) { // 运营用户
      return organizationMapper.selectOrganizationsByTypeWithId(name,
          OrganizationType.ORGANIZATIONS.getCode(), user.getOrganizationId(),
          user.getOrganization().getDomainId());
      // return getOrganizationsByTypeWithId(OrganizationType.ORGANIZATIONS.getCode(),
      // user.getOrganizationId());
    } else {
      List<Organization> list = new ArrayList<Organization>();
      list.add(organizationMapper.selectById(user.getOrganizationId()));
      // list.add(getById(user.getOrganizationId()));
      return list;
    }
  }

  @Override
  @Privileges(name = "ORGANIZATION-LIST-VIEW", scope = {1, 2, 3}, identity = {
      RoleIdentity.SUPER_ADMIN, RoleIdentity.OPERATIONS_ADMIN, RoleIdentity.ORGANIZATIONS_ADMIN,
      RoleIdentity.MAINTAINS_ADMIN})
  public PageInfo<Organization> getOrganizations(String name, Integer pageNum, Integer pageSize) {
    User user = userService.getCurrentUser();
    Role role = user.getRole();
    List<Organization> list = new ArrayList<>();
    PageInfo<Organization> pages = null;
    if (role.isSuperAdmin()) {
      // list = getAll();
      list = organizationMapper.selectAll(name);
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
          organizationMapper.selectBaseAll(name);
        }
      });
    } else if (role.isOperationsAdmin() || role.isOperationsUser()) {
      // list = getOrganizationsByTypeWithId(OrganizationType.ORGANIZATIONS.getCode(),
      // user.getOrganizationId());
      list = organizationMapper.selectBaseOrganizationsByTypeWithId(name,
          OrganizationType.ORGANIZATIONS.getCode(), user.getOrganizationId(),
          user.getOrganization().getDomainId());
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
          organizationMapper.selectBaseOrganizationsByTypeWithId(name,
              OrganizationType.ORGANIZATIONS.getCode(), user.getOrganizationId(),
              user.getOrganization().getDomainId());
        }
      });
    } else {
      // list.add(getById(user.getOrganizationId()));
      list.add(organizationMapper.selectById(user.getOrganizationId()));
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
          organizationMapper.selectBaseById(user.getOrganizationId());
        }
      });
    }
    List<Organization> pageList = pages.getList();
    int margin = pageSize * (pageNum - 1);
    for (int i = 0; i < pageList.size(); i++) {
      Organization o = list.get(i + margin); // 必须加上差值
      pageList.set(i, o);
    }
    return pages;
  }



 

 


}
