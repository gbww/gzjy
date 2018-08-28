package com.gzjy.role.model;

import java.util.Date;


public interface Role {
  /**
   * 角色范围：
   * 1、自服务  2、运营    3、运维   4、超级角色
   */
  public enum RoleScope{
    ANHUI(1);
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
          return ANHUI;
      }
      return null;
    }
  }
  
  /**
   * 角色类型：
   * 1、安徽管理员  2、超级管理员
   */
  public enum RoleIdentity{
    ANHUI_ADMIN,SUPER_ADMIN;
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
   * 判断是否是安徽管理员
   * @return
   */
  public boolean isAnHuiAdmin();
  

  
  /**
   * 返回角色枚举
   * @return
   */
  public RoleIdentity getRoleIdentity();
}
