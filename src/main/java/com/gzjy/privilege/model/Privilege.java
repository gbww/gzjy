package com.gzjy.privilege.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;
import com.gzjy.common.Add;
import com.gzjy.common.Update;

public class Privilege {
  public enum Category{
    USER("USER", "用户"), 
    ORGANIZATION("ORGANIZATION", "部门"),
    ROLE("ROLE", "角色"),
    CONTRACT("CONTRACT", "合同"),
    SAMPLE("SAMPLE", "接样"),
	REPORT("REPORT", "报告");
      
    private String name;
    private String description;

    private Category(String name, String description) {
      this.name = name;
      this.description = description;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }
  };
  
  public enum CategoryWithCapitals{
    USER("USER", new String[]{"用户", "Y"}), 
    ORGANIZATION("ORGANIZATION", new String[]{"部门", "B"}),
    ROLE("ROLE", new String[]{"角色", "J"}),
    CONTRACT("CONTRACT", new String[]{"合同", "H"}),
    SAMPLE("SAMPLE", new String[]{"接样", "S"}),
	REPORT("REPORT", new String[]{"报告", "R"});
	 
    private String name;
    private String[] details;

    private CategoryWithCapitals(String name, String[] details) {
      this.name = name;
      this.details = details;
    }

    public String getName() {
      return name;
    }

    public String[] getDetails () {
      return details;
    }
  };
  
  /**
   * 权限类型：
   * 1、自服务  2、运营    3、运维 
   */
  public enum PrivilegeScope{
    ANHUI(1);  
    /**
     * 权限类型代号
     */
    private int code;
    
    private PrivilegeScope(int code){
      this.code = code;
    }
    
    /**
     * 取得权限代号
     * @return 权限代号
     */
    public int getCode(){
      return code;
    }

    /**
     * 根据权限类型代号获取权限类型枚举
     * @param code ：权限类型代号
     * @return 权限枚举
     */
    public static PrivilegeScope getPrivilegeScope(int code){
      switch(code){
        case 1:
          return ANHUI;
       
      }
      return null;
    }
  }
  
  /**
   * 主键id
   */
  private String id;
  
  /**
   * 权限分类
   */
  private String category;

  /**
   * 权限名称
   */
  @NotEmpty(message="权限名称不能为空",groups={Add.class})
  private String name;
  
  /**
   * 权限显示名称
   */
  @NotEmpty(message="权限显示名称不能为空",groups={Add.class})
  private String displayName;
  
  /**
   * 权限描述
   */
  @NotEmpty(message="权限描述不能为空",groups={Add.class, Update.class})
  private String description;

  /**
   * 权限类型
   */
  @NotNull(message="权限类型不能为空",groups={Add.class})
  private Integer scope;
  
  /**
   * 是否是查看权限
   */
  private Boolean view;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getScope() {
    return scope;
  }

  public void setScope(Integer scope) {
    this.scope = scope;
  }

  public Boolean getView() {
    return view;
  }

  public void setView(Boolean view) {
    this.view = view;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {  
      return false;  
    }     
    if (obj == this) {  
      return true;  
    } 
    if(obj instanceof Privilege){
      Privilege p = (Privilege) obj;
      if (p.getId() != null && p.getId().equals(this.id))
        return true;
      else if (p.getName().equals(this.name) && p.getScope() == this.scope)
        return true;
      return false;
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).omitNullValues().add("id", id).add("name", name)
        .add("resource", category).add("scope", scope).toString();
  }
}