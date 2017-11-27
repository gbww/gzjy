package com.gzjy.client.model;

import java.util.Date;

public class ClientLinkman {
    private String id;

    private Integer clientNum;

    private String linkmanName;

    private String linkmanPosition;

    private String linkmanTelephone;

    private String linkmanEmail;

    private String linkmanRemark;

    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public void setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName == null ? null : linkmanName.trim();
    }

    public String getLinkmanPosition() {
        return linkmanPosition;
    }

    public void setLinkmanPosition(String linkmanPosition) {
        this.linkmanPosition = linkmanPosition == null ? null : linkmanPosition.trim();
    }

    public String getLinkmanTelephone() {
        return linkmanTelephone;
    }

    public void setLinkmanTelephone(String linkmanTelephone) {
        this.linkmanTelephone = linkmanTelephone == null ? null : linkmanTelephone.trim();
    }

    public String getLinkmanEmail() {
        return linkmanEmail;
    }

    public void setLinkmanEmail(String linkmanEmail) {
        this.linkmanEmail = linkmanEmail == null ? null : linkmanEmail.trim();
    }

    public String getLinkmanRemark() {
        return linkmanRemark;
    }

    public void setLinkmanRemark(String linkmanRemark) {
        this.linkmanRemark = linkmanRemark == null ? null : linkmanRemark.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}