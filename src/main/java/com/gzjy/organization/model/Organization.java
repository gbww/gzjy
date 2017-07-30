package com.gzjy.organization.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.tomcat.jni.Pool;
import org.hibernate.validator.constraints.NotEmpty;
import com.google.common.base.MoreObjects;
import com.gzjy.common.Add;
import com.gzjy.common.Update;

public class Organization {
  /**
   * 部门类型： 1、自服务 2、运营 3、运维 4、全平台
   */
  public enum OrganizationType {
    ORGANIZATIONS(1), OPERATIONS(2), MAINTAINS(3), WHOLE(4);

    /**
     * 部门类型代号
     */
    private int code;

    private OrganizationType(int code) {
      this.code = code;
    }

    /**
     * 取得部门代号
     * 
     * @return 部门代号
     */
    public int getCode() {
      return code;
    }

    /**
     * 根据部门类型代号获取部门类型枚举
     * 
     * @param code ：部门类型代号
     * @return 部门枚举
     */
    public static OrganizationType getOrganizationType(int code) {
      switch (code) {
        case 1:
          return ORGANIZATIONS;
        case 2:
          return OPERATIONS;
        case 3:
          return MAINTAINS;
        case 4:
          return WHOLE;
      }
      return null;
    }
  }

  private String id;

  private String domainId;

  /**
   * 部门名称
   */
  @NotEmpty(message = "部门名不能为空", groups = {Add.class, Update.class})
  @Pattern(regexp = "^[\\S\u4e00-\u9fa5]+$", message = "该名称不规范，不能包含空格等非法字符", groups = {Add.class,
      Update.class})
  private String name;

  /**
   * 所属的资源池
   */
  @NotEmpty(message = "资源池不能为空", groups = {Add.class})
  private List<Pool> pools;

  /**
   * 部门描述
   */
  private String description;

  /**
   * 父组织id 不存在未-1
   */
  @NotNull(message = "父部门不能为空", groups = {Add.class})
  private String parent = "-1";

  /**
   * 部门类型，具体详见枚举OrganizationType {@link OrganizationType}
   */
  @NotNull(message = "部门类型不能为空", groups = {Add.class})
  private Integer type;

  private Date createdAt;

  private Date updatedAt;

  /**
   * 是否启用部门
   */
  private Boolean enabled = true;

  private Boolean deleted = false;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public List<Pool> getPools() {
    return pools;
  }

  public void setPools(List<Pool> pools) {
    this.pools = pools;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description == null ? null : description.trim();
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj instanceof Organization) {
      return ((Organization) obj).getId().equals(this.id);
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).omitNullValues().add("id", id).add("name", name)
        .add("pools", pools).add("parent", parent).add("type", type).add("enabled", enabled)
        .toString();
  }
}
