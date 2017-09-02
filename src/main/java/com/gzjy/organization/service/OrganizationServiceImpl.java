package com.gzjy.organization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.organization.mapper.OrganizationMapper;
import com.gzjy.organization.model.Organization;
import com.gzjy.organization.model.OrganizationExample;
import com.gzjy.organization.model.OrganizationExample.Criteria;
import com.gzjy.role.RoleService;
import com.gzjy.role.model.Role;
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
      OrganizationExample example=new OrganizationExample();
      Criteria criteria =example.createCriteria();
      criteria.andNameEqualTo(name);
    List<Organization> organization = organizationMapper.selectByExample(example);
    return (organization== null || organization.size()==0)? false : true;
  }

  public Organization check(String id) {
    Organization o = organizationMapper.selectById(id);
    if (o == null) {
      throw new BizException("不存在该部门！");
    }
    return o;
  }
  @Override
  public PageInfo<Organization>  select(Integer pageNum, Integer pageSize, String name,String orderBy){
      OrganizationExample example=new OrganizationExample();
      Criteria criteria =example.createCriteria();
      criteria.andNameLike("%"+name+"%");
      if (StringUtils.isBlank(orderBy)) {
      orderBy = "created_at desc";
  }
      example.setOrderByClause(orderBy);
      
      PageInfo<Organization> pages = null;
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
          @Override
          public void doSelect() {
             
              organizationMapper.selectByExample(example);
           
          }
      });
      return pages;
  }
  @Override
  public List<Organization>  selectAll( String name,String orderBy){
      OrganizationExample example=new OrganizationExample();
      Criteria criteria =example.createCriteria();
      criteria.andNameLike("%"+name+"%");
      if (StringUtils.isBlank(orderBy)) {
      orderBy = "created_at desc";
  }
      example.setOrderByClause(orderBy);
      
      List<Organization>records =new ArrayList<Organization>();
             
      records =   organizationMapper.selectByExample(example);
           
      
      return records;
  }

  /**
   * 判断当前用户是否允许创建部门
   * 
   * @param organization ：部门
   * @return 允许为true，否则为false
   */
  private boolean allowCreateOrganization(Organization organization) {
    
    Role role = userService.getCurrentUser().getRole(); // 获取当前用户角色
    if (role.isAnHuiAdmin() || role.isSuperAdmin()) { // 运营管理员   
        return true;
    }
    return false;
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
 /*   OrganizationType type = OrganizationType.getOrganizationType(organizationType);
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
    }*/
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
  public Organization add(Organization organization) {
   
    String organizationName = organization.getName();
    if (nameExist(organizationName)) {
      throw new BizException("该部门名称已经存在，请使用其他名称");
    }
    organization.setId(UUID.random());
    organization.setCreatedAt(new Date());
    organization.setUpdatedAt(new Date());
    organizationMapper.insert(organization);
 
    return organization;
  }




  @Override
  @Transactional
  public int delete(String id) {
   
    int count = 0;
    roleService.deleteByOrganizationId(id);
    count = organizationMapper.deleteById(id);
    return count;
  }

  @Override
  @Transactional
  public Organization update(String id, Organization organization) {
    

    Organization o = check(id); // 取得指定id的原纪录
    String name = organization.getName();
    if (!o.getName().equals(name) && nameExist(name)) { // 如果修改名字则需去查询是否重名
      throw new BizException("该部门名称已经存在，请使用其他名称");
    }
    organization.setId(id);
    organization.setUpdatedAt(new Date());
    organizationMapper.updateByPrimaryKeySelective(organization);
    return organization;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Organization getById(String id) {
    Organization o = organizationMapper.selectById(id);
  
    return o;
  }









 

 


}
