package com.gzjy.privilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.privilege.model.Privilege;


@Mapper
public interface PrivilegeMapper {
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
   * @param record ：Privilege对象 {@link Privilege}
   * @return 插入记录数
   */
  int insert(Privilege record);

  /**
   * 插入记录
   * 
   * @param record ：Privilege对象 {@link Privilege}
   * @return 插入记录数
   */
  int insertSelective(Privilege record);

  /**
   * 通过主键id查询记录
   * 
   * @param id ：主键id
   * @return 权限记录
   */
  Privilege selectById(String id);
  
  /**
   * 通过权限类型查询记录
   * 
   * @param scope ：权限类型
   * @return 权限列表
   */
  List<Privilege> selectByScope(int scope);

  /**
   * 查询所有记录
   * 
   * @return 权限列表
   */
  List<Privilege> selectAll();

  /**
   * 获取对应资源类型的权限集
   * @param category ：权限资源分类
   * @param scope ：权限类型
   * @return 权限列表
   */
  List<Privilege> selectByCategoryAndScope(@Param("category")String category, @Param("scope")Integer scope);
  
  /**
   * 获取对应资源类型的查看权限集
   * @param category ：权限资源分类
   * @param scope ：权限类型
   * @return 权限列表
   */
  List<Privilege> selectViewPrivilegesByCategoryAndScope(@Param("category")String category, @Param("scope")Integer scope);

  /**
   * 查询是否存在记录
   * 
   * @param name ：权限名称
   * @param scope ：权限类型
   * @return
   */
  Privilege selectByNameAndScope(@Param("name")String name, @Param("scope")int scope);

  /**
   * 更新记录
   * 
   * @param record ：Privilege对象 {@link Privilege}
   * @return 更新记录数
   */
  int updateByIdSelective(Privilege record);

  /**
   * 更新记录
   * 
   * @param record ：Privilege对象 {@link Privilege}
   * @return 更新记录数
   */
  int updateById(Privilege record);
}
