package com.gzjy.organization.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.organization.model.Organization;

@Mapper
public interface OrganizationMapper {
  /**
   * 通过主键删除记录
   * 
   * @param id ：主键id
   * @return 删除记录数
   */
  int deleteById(String id);

  /**
   * 插入记录
   * 
   * @param record ：Organization对象 {@link Organization}
   * @return 插入记录数
   */
  int insert(Organization record);

  /**
   * 插入新纪录
   * 
   * @param record ：Organization对象 {@link Organization}
   * @return 插入记录数
   */
  int insertSelective(Organization record);

  /**
   * 通过主键id查询不包含资源池的部门记录
   * 
   * @param id ：部门id
   * @return 部门记录
   */
  Organization selectBaseById(String organizationId);
  
  /**
   * 通过主键id查询记录
   * 
   * @param id ：部门id
   * @return 部门记录
   */
  Organization selectById(String id);

  /**
   * 通过部门名称查询记录
   * 
   * @param name ：部门名称
   * @return 部门记录
   */
  Organization selectByName(String name);

  /**
   * 查询不包含资源池的部门列表
   * @param name 
   * @return 部门列表
   */
  List<Organization> selectBaseAll(String name);
  
  /**
   * 查询所有部门记录
   * @param name 
   * 
   * @return 部门记录列表
   */
  List<Organization> selectAll(String name);

  /**
   * 查询运营用户可以查询到的不包含资源池的部门列表
   * @param name 
   * @param type ：部门类型
   * @param id ：部门id
   * @return
   */
  List<Organization> selectBaseOrganizationsByTypeWithId(@Param("name")String name, @Param("type")Integer type, @Param("id")String id,  @Param("domainId")String domainId);
  
  /**
   * 通过类型或指定部门id查询部门
   * @param name 
   * @param type ：部门类型
   * @param id ：部门id
   * @param domainId 
   * @return
   */
  List<Organization> selectOrganizationsByTypeWithId(@Param("name")String name, @Param("type")Integer type, @Param("id")String id, @Param("domainId")String domainId);
  
  /**
   * 通过部门类型查询部门
   * @param type : 部门类型
   * @return 部门列表
   */
  List<Organization> selectOrganizationsByType(@Param("type")Integer type, @Param("domainId")String domainId);

  /**
   * 更新记录
   * 
   * @param record ：Organization对象 {@link Organization}
   * @return 更新记录数
   */
  int updateByIdSelective(Organization record);

  /**
   * 更新记录
   * 
   * @param record ：Organization对象 {@link Organization}
   * @return 更新记录数
   */
  int updateById(Organization record);

  List<Organization> selectByDomainId(String domainId);
}
