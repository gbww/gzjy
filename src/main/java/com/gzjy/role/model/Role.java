package com.gzjy.role.model;

import java.util.Date;

//@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = Role2CrabRoleJackson2Serializer.class)
//@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Role2CrabRoleJackson2Deserializer.class)
public interface Role {
  /**
   * 角色范围：
   * 1、自服务  2、运营    3、运维   4、超级角色
   */
  public enum RoleScope{
    ORGANIZATIONS(1), OPERATIONS(2), MAINTAINS(3), SUPER(4);
    /**
     * 角色范围代号
     */
    private int code;
    
    private RoleScope(int code){
      this.code = code;
    }
    
    /**
     * 取得角色代号
     * @return 角色代号
     */
    public int getCode(){
      return code;
    }

    /**
     * 根据角色类型代号获取角色类型枚举
     * @param code ：角色类型代号
     * @return 角色枚举
     */
    public static RoleScope getRoleScope(int code){
      switch(code){
        case 1:
          return ORGANIZATIONS;
        case 2:
          return OPERATIONS;
        case 3:
          return MAINTAINS;
        case 4:
          return SUPER;
      }
      return null;
    }
  }
  
  /**
   * 角色类型：
   * 1、自服务管理员  2、自服务用户  3、运营管理员   4、运营用户  5、运维管理员  6、运维用户  7、超级管理员
   */
  public enum RoleIdentity{
    ORGANIZATIONS_ADMIN, ORGANIZATIONS_USER, 
    OPERATIONS_ADMIN, OPERATIONS_USER, 
    MAINTAINS_ADMIN, MAINTAINS_USER, SUPER_ADMIN;
  }
  
  public String getId();
  
  public String getOrganizationId();
  
  public String getName();
  
  public String getDescription();
  
  public Boolean getDefaults();
  
  public Date getCreatedAt();
  
  public Date getUpdatedAt();
  
  public Boolean getDeleted();
  /**
   * 是否是超级管理员
   * @return
   */
  public boolean isSuperAdmin();
  
  /**
   * 判断是否是自服务部门管理员
   * @return
   */
  public boolean isOrganizationAdmin();
  
  /**
   * 判断是否是自服务普通用户
   * @return
   */
  public boolean isOrganizationUser();
  
  /**
   * 判断是否是运营管理员
   * @return
   */
  public boolean isOperationsAdmin();
  
  /**
   * 判断是否是运营用户
   * @return
   */
  public boolean isOperationsUser();
  
  /**
   * 判断是否是运维管理员
   * @return
   */
  public boolean isMaintainsAdmin();
  
  /**
   * 判断是否是运维普通用户
   * @return
   */
  public boolean isMaintainsUser();
  
  /**
   * 返回角色枚举
   * @return
   */
  public RoleIdentity getRoleIdentity();
}
