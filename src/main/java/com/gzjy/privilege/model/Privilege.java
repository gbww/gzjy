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
    INSTANCE("INSTANCE", "虚拟机"), 
    INSTANCETEMPLATE("INSTANCETEMPLATE", "虚拟机模板"),
    INSTANCEIMAGE("INSTANCEIMAGE", "虚拟机镜像"),
    TICKET("TICKET","工单"),
    VPC("VPC", "VPC服务"),
    VPN("VPN", "VPN服务"),
    VOLUME("VOLUME", "块存储"), 
    VOLUMETEMPLATE("VOLUMETEMPLATE", "块存储模板"),
    SECURITYGROUP("SECURITYGROUP", "安全组"), 
    QUOTA("QUOTA", "配额"),
    PROJECT("PROJECT", "项目管理"),
    BUSINESS("BUSINESS", "业务系统"),
    PRODUCT("PRODUCT", "产品"),
    PHYSICALINSTANCETEMPLATE("PHYSICALINSTANCETEMPLATE","物理机模板"),
    PHYSIALINSTANCEIMAGE("PHYSIALINSTANCEIMAGE","物理机镜像"),
    ORDER("ORDER","订单"),
    NOTICE("NOTICE","公告"),
    LOADBALANCE("LOADBALANCE", "负载均衡"),
    KEYPAIR("KEYPAIR", "密钥对"),
    FLOATINGIP("FLOATINGIP","浮动IP"), 
    BANDWIDTHTEMPLATE("BANDWIDTHTEMPLATE","宽带模板"),
    APPROVAL("APPROVAL","审批"),
    ALARM("ALARM","告警"),
    POOL("POOL","资源池"),
    USAGE("USAGE","资源使用"),
    LOG("LOG","日志管理"),
    ASSET("ASSET","资产管理"),
    BACKUP("BACKUP","备份管理"),
    OVERVIEW("OVERVIEW","总览"),
    HYPERVISOR("HYPERVISOR","宿主机"),
    KNOWLEDGE("KNOWLEDGE","知识库"),
    SHARE("SHARE","分布式文件系统");
      
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
    INSTANCE("INSTANCE", new String[]{"虚拟机", "X"}), 
    INSTANCETEMPLATE("INSTANCETEMPLATE", new String[]{"虚拟机模板", "X"}),
    INSTANCEIMAGE("INSTANCEIMAGE", new String[]{"虚拟机镜像", "X"}),
    TICKET("TICKET", new String[]{"工单", "G"}),
    VPC("VPC", new String[]{"VPC服务", "V"}),
    VPN("VPN", new String[]{"VPN服务", "V"}),
    VOLUME("VOLUME", new String[]{"块存储", "K"}), 
    VOLUMETEMPLATE("VOLUMETEMPLATE", new String[]{"块存储模板", "K"}),
    SECURITYGROUP("SECURITYGROUP", new String[]{"安全组", "A"}), 
    QUOTA("QUOTA", new String[]{"配额", "P"}),
    PROJECT("PROJECT", new String[]{"项目管理", "X"}),
    BUSINESS("BUSINESS", new String[]{"业务系统", "Y"}),
    PRODUCT("PRODUCT", new String[]{"产品", "C"}),
    PHYSICALINSTANCETEMPLATE("PHYSICALINSTANCETEMPLATE", new String[]{"物理机模板", "W"}),
    PHYSIALINSTANCEIMAGE("PHYSIALINSTANCEIMAGE", new String[]{"物理机镜像", "W"}),
    ORDER("ORDER", new String[]{"订单", "D"}),
    NOTICE("NOTICE", new String[]{"公告", "G"}),
    LOADBALANCE("LOADBALANCE", new String[]{"负载均衡", "F"}),
    KEYPAIR("KEYPAIR", new String[]{"密钥对", "M"}),
    FLOATINGIP("FLOATINGIP", new String[]{"浮动IP", "F"}), 
    BANDWIDTHTEMPLATE("BANDWIDTHTEMPLATE", new String[]{"宽带模板", "K"}),
    APPROVAL("APPROVAL", new String[]{"审批", "S"}),
    ALARM("ALARM", new String[]{"告警", "G"}),
    POOL("POOL", new String[]{"资源池", "Z"}),
    USAGE("USAGE", new String[]{"资源使用", "Z"}),
    LOG("LOG", new String[]{"日志管理", "R"}),
    ASSET("ASSET", new String[]{"资产管理", "Z"}),
    BACKUP("BACKUP", new String[]{"备份管理", "B"}),
    OVERVIEW("OVERVIEW", new String[]{"总览", "Z"}),
    HYPERVISOR("HYPERVISOR", new String[]{"宿主机", "H"}),
    KNOWLEDGE("KNOWLEDGE", new String[]{"知识库", "Z"}),
    SHARE("SHARE", new String[]{"分布式文件系统", "F"});
      
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
    ORGANIZATIONS(1), OPERATIONS(2), MAINTAINS(3);  
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
          return ORGANIZATIONS;
        case 2:
          return OPERATIONS;
        case 3:
          return MAINTAINS;
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