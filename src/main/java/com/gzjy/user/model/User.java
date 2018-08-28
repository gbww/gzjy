package com.gzjy.user.model;



import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;
import com.gzjy.common.Add;
import com.gzjy.common.Update;
import com.gzjy.organization.model.Organization;
import com.gzjy.role.model.CrabRole;

public class User {

  /**
   * 用戶狀態
   *
   */
  public enum UserStatus {
    ACTIVATE(1), SUSPEND(2);

    private int code;

    private UserStatus(int code) {
      this.code = code;
    }

    public int getCode() {
      return code;
    }

    public static UserStatus getUserStatus(int code) {
      switch (code) {
        case 1:
          return ACTIVATE;
        case 2:
          return SUSPEND;
      }
      return null;
    }
  };

  /**
   * 主键id
   */
  private String id;

  /**
   * 对应Organization Model的主键id
   */
  private String organizationId;

  /**
   * 用户所属部门
   */
  private Organization organization;

  /**
   * 对应Role Model的主键id
   */
  private String roleId;
  
  /**
   * 用户的角色
   */
  private CrabRole role;

  /**
   * 用户姓名
   */
  private String name;

  /**
   * 用户名
   */
  @NotEmpty(message = "用户名不能为空", groups = {Add.class, Update.class})
  @Pattern(regexp = "^[\\S\u4e00-\u9fa5]+$", message = "该名称不规范，不能包含空格等非法字符", groups = {Add.class, Update.class})
  private String username;

  /**
   * 用户电子邮箱
   */
  @NotEmpty(message = "电子邮箱不能为空", groups = {Add.class})
  @Email(message = "电子邮箱格式错误", groups = {Add.class, Update.class})
  private String email;
  
  @NotEmpty(message = "手机号码不能为空", groups = {Add.class})
  @Pattern(regexp = "^(1[3-8])\\d{9}$" ,message = "请输入有效手机号码", groups = {Add.class, Update.class})
  private String  phone;

  /**
   * 用户密码
   */
  @NotEmpty(message = "密码不能为空", groups = {Add.class})
  @Pattern(regexp = "^[\\S]+$", message = "密码不规范，不能包含空格等非法字符", groups = {Add.class, Update.class})
  private String password;

  /**
   * 用户状态，具体参见用户状态枚举
   */
  private Integer state;

  /**
   * 用户描述
   */
  private String description;

  /**
   * 创建用户时间
   */
  private Date createdAt;

  /**
   * 修改用户时间
   */
  private Date updatedAt;
  
  public CrabRole getRole() {
    return role;
  }

  public void setRole(CrabRole role) {
    this.role = role;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId == null ? null : organizationId.trim();
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId == null ? null : roleId.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username == null ? null : username.trim();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description == null ? null : description.trim();
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {  
      return false;  
    }     
    if (obj == this) {  
      return true;  
    } 
    if(obj instanceof User){
      return ((User) obj).getId().equals(this.id);
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).omitNullValues().add("id", id).add("name", name)
        .add("username", username).add("description", description).add("state", state)
        .add("organizationId", organizationId).add("organization", organization)
        .add("roleId", roleId).add("email", email).add("phone",phone).add("password", password)
        .add("createdAt",createdAt).add("updatedAt",updatedAt).toString();
  }
}
