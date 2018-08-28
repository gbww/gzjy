package com.gzjy.organization.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gzjy.organization.model.Organization;
import com.gzjy.user.model.UserOrganization;


public interface OrganizationService {

 

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


  public PageInfo<Organization>  select(Integer pageNum, Integer pageSize, String name,String orderBy);
  
  public List<Organization>  selectAll(String name,String orderBy);


  
  public Organization add(Organization organization);

  

}
