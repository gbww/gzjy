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
  
  private String id;


  /**
   * 部门名称
   */
  @NotEmpty(message = "部门名不能为空", groups = {Add.class, Update.class})
  @Pattern(regexp = "^[\\S\u4e00-\u9fa5]+$", message = "该名称不规范，不能包含空格等非法字符", groups = {Add.class,
      Update.class})
  private String name;

 

  /**
   * 部门描述
   */
  private String description;

  /**
   * 父组织id 不存在未-1
   */
  @NotNull(message = "父部门不能为空", groups = {Add.class})
  private String parent = "-1";


  private Date createdAt;

  private Date updatedAt;
  private Boolean deleted = false;
  


  public Boolean getDeleted() {
    return deleted;
}

public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
}

public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

 

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
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
        .add("parent", parent)
        .toString();
  }
}
