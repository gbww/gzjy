package com.gzjy.oauth.entity;

import com.google.common.base.MoreObjects;
import java.security.Principal;
import javax.security.auth.Subject;

public class OauthUser implements Principal {
  private String name;
  private String username;
  private String password;

  public OauthUser() {
    super();
  }

  public OauthUser(String name, String username) {
    this.name = name;
    this.username = username;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password == null ? null : password.trim();
  }

  @Override
  public boolean implies(Subject subject) {
    if (subject == null) return false;
    return subject.getPrincipals().contains(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name).add("username", username).toString();
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj instanceof OauthUser) {
      return ((OauthUser) obj).getUsername().equals(this.username);
    }
    return super.equals(obj);
  }
}
