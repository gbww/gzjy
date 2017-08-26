package com.gzjy.role.model;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;
import com.gzjy.common.Add;
import com.gzjy.common.Update;


public final class CrabRole implements Role{
  /**
   * 主键id
   */
  private String id;
  
  /**
   * 对应Organization Model的主键id
   */
  private String organizationId;

  /**
   * 角色名称
   */
  @NotEmpty(message="角色名称不能为空",groups={Add.class, Update.class})
  @Pattern(regexp = "^[\\S\u4e00-\u9fa5]+$", message = "该名称不规范，不能包含空格等非法字符", groups = {Add.class, Update.class})
  private String name;

  /**
   * 角色描述
   */
  private String description;

  /**
   * 角色类型
   */
  private Integer scope;

  /**
   * 是否是默认角色
   */
  private Boolean defaults = false;

  /**
   * 角色创建时间
   */
  private Date createdAt;

  /**
   * 角色修改时间
   */
  private Date updatedAt;
  
  private Boolean deleted = false;
  
  private CrabRole(){
    
  }
  
  public static CrabRoleBuilder builder(){
    return new CrabRoleBuilder();
  }
  
  public static class CrabRoleBuilder{
    private CrabRole model;
    
    CrabRoleBuilder() {
      this(new CrabRole());
    }
    
    CrabRoleBuilder(CrabRole role){
      model = role;
    }
    
    public CrabRoleBuilder organizationId(String organizationId){
      model.organizationId = organizationId;
      return this;
    }
    public CrabRoleBuilder id(String id){
      model.id = id;
      return this;
    }
    
    public CrabRoleBuilder name(String name){
      model.name = name;
      return this;
    }
    
    public CrabRoleBuilder description(String description){
      model.description = description;
      return this;
    }
    
    public CrabRoleBuilder scope(Integer scope){
      model.scope = scope;
      return this;
    }
    
    public CrabRoleBuilder createdAt(Date createdAt){
      model.createdAt = createdAt;
      return this;
    }
    
    public CrabRoleBuilder updatedAt(Date updatedAt){
      model.updatedAt = updatedAt;
      return this;
    }
    
    public CrabRoleBuilder deleted(Boolean deleted){
      model.deleted = deleted;
      return this;
    }
    
    protected CrabRoleBuilder defaults(Boolean defaults){
      model.defaults = defaults;
      return this;
    }
    
    public Role build() {
        return model;
    }

    public CrabRoleBuilder from(Role in) {
        model = (CrabRole) in;
        return this;
    }
  }
  
  @Override
  public String getId() {
    return id;
  }
  
  @Override
  public String getOrganizationId() {
    return organizationId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }
  
  public Integer getScope(){
    return scope;
  }

  @Override
  public Boolean getDefaults() {
    return defaults;
  }

  @Override
  public Date getCreatedAt() {
    return createdAt;
  }

  @Override
  public Date getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public Boolean getDeleted() {
    return deleted;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {  
      return false;  
    }     
    if (obj == this) {  
      return true;  
    } 
    if(obj instanceof CrabRole){
      return ((CrabRole) obj).getId().equals(this.id);
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).omitNullValues().add("id", id).add("scope", scope)
        .add("organizationId", organizationId).add("name", name).add("description", description)
        .add("defaults",defaults).add("createdAt",createdAt).add("updatedAt",updatedAt).toString();
  }

  @Override
  public boolean isSuperAdmin() {
    if ("94d99f91ba8d444dae6e4302e18de2a9".equals(id)){
      return true;
    }
    return false;
  }
  /* 
   * @see com.gzjy.role.model.Role#isAnHuiAdmin()
   */
  @Override
  public boolean isAnHuiAdmin() {
      if ("94d99f91ba8d444dae6e4302e18de2a9".equals(id)){
          return true;
        }
        return false;
  }


  @Override
  public RoleIdentity getRoleIdentity() {
    if (isSuperAdmin()){
      return RoleIdentity.SUPER_ADMIN;
    }else if (isAnHuiAdmin()){
      return RoleIdentity.ANHUI_ADMIN;
    }
    return null;
  }


}