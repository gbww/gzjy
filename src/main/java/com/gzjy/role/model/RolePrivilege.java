package com.gzjy.role.model;

import com.google.common.base.MoreObjects;

public class RolePrivilege {
    /**
     * 主键id
     */
    private String id;

    /**
     * 对应Role Model的主键id
     */
    private String roleId;

    /**
     * 对应Privilege Model的主键id
     */
    private String privilegeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId == null ? null : privilegeId.trim();
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).omitNullValues().add("id", id)
          .add("roleId", roleId).add("privilegeId", privilegeId).toString();
    }
}