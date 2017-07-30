package com.gzjy.organization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gzjy.organization.model.Organization;
import com.gzjy.user.model.UserOrganization;


public interface OrganizationService {

  /**
   * 检查指定id的部门
   * 
   * @param id ：部门id
   * @return 存在返回部门，否则为null
   */
  Organization check(String id);



  /**
   * 判断当前用户是否允许访问该部门
   * 
   * @param id ：部门id
   * @return 允许为true，否则为false
   */
  boolean allowAccessOrganization(String id);

  /**
   * 判断当前用户是否允许修改该部门
   * 
   * @param id
   * @return
   */
  boolean allowManipulateOrganization(String id);

  /**
   * 删除部门
   * 
   * @param id ：部门id
   * @return 删除部门数
   */
  int delete(String id);

  /**
   * 更新部门
   * 
   * @param id ：部门id
   * @param organization ：部门
   * @return 更新部门
   */
  Organization update(String id, Organization organization);

  /**
   * 查看部门
   * 
   * @param id ：部门id
   * @return 部门
   */
  Organization getById(String id);



  /**
   * 获取当前用户可访问的部门列表
   * 
   * @return 部门列表
   */
  List<Organization> getOrganizations(String name);

  /**
   * 获取当前用户可见的部门列表
   * 
   * @return 部门列表
   */
  PageInfo<Organization> getOrganizations(String name, Integer pageNum, Integer pageSize);

  
  public Organization add(UserOrganization userOrganization);

  

}
