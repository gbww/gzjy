package com.gzjy.user.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;
import com.gzjy.common.Add;


public class AccessToken {
    /**
     * 主键
     */
    private String id;

    /**
     * 对应User Model的id
     */
    @NotEmpty(message="用户id不能为空",groups={Add.class})
    private String userId;

    /**
     * 对应Pool Model的id
     */
    @NotEmpty(message="资源id不能为空",groups={Add.class})
    private String poolId;

    /**
     * 对应Tenant Model的id
     */
    private String tenantId;

    /**
     * openstack token的过期时间
     */
    private Date expireTime;

    /**
     * openstack token字符串
     */
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId == null ? null : poolId.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).omitNullValues().add("id", id).add("userId", userId)
          .add("poolId", poolId).add("tenantId", tenantId).add("expireTime", expireTime)
          .add("token",token).toString();
    }
}