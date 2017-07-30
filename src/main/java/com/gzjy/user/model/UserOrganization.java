package com.gzjy.user.model;

import javax.validation.constraints.NotNull;

import com.gzjy.common.Add;
import com.gzjy.organization.model.Organization;

public class UserOrganization {
  @NotNull(message="部门不能为空", groups={Add.class})
  private Organization organization;
  
  @NotNull(message="部门管理员不能为空", groups={Add.class})
  private User user;
  
  public Organization getOrganization() {
    return organization;
  }
  public void setOrganization(Organization organization) {
    this.organization = organization;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
}
