package com.gzjy.review.modle;

import java.util.Date;

public class ComInfor {
    private String id;

    private String comName;

    private String comType;

    private String comPrincipal;

    private String comCreditCode;

    private String comLicence;

    private String comAddress;

    private String comScale;

    private Integer comScaleScore;

    private String comVariety;

    private Integer comVarietyScore;

    private String comTelephone;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType == null ? null : comType.trim();
    }

    public String getComPrincipal() {
        return comPrincipal;
    }

    public void setComPrincipal(String comPrincipal) {
        this.comPrincipal = comPrincipal == null ? null : comPrincipal.trim();
    }

    public String getComCreditCode() {
        return comCreditCode;
    }

    public void setComCreditCode(String comCreditCode) {
        this.comCreditCode = comCreditCode == null ? null : comCreditCode.trim();
    }

    public String getComLicence() {
        return comLicence;
    }

    public void setComLicence(String comLicence) {
        this.comLicence = comLicence == null ? null : comLicence.trim();
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress == null ? null : comAddress.trim();
    }

    public String getComScale() {
        return comScale;
    }

    public void setComScale(String comScale) {
        this.comScale = comScale == null ? null : comScale.trim();
    }

    public Integer getComScaleScore() {
        return comScaleScore;
    }

    public void setComScaleScore(Integer comScaleScore) {
        this.comScaleScore = comScaleScore;
    }

    public String getComVariety() {
        return comVariety;
    }

    public void setComVariety(String comVariety) {
        this.comVariety = comVariety == null ? null : comVariety.trim();
    }

    public Integer getComVarietyScore() {
        return comVarietyScore;
    }

    public void setComVarietyScore(Integer comVarietyScore) {
        this.comVarietyScore = comVarietyScore;
    }

    public String getComTelephone() {
        return comTelephone;
    }

    public void setComTelephone(String comTelephone) {
        this.comTelephone = comTelephone == null ? null : comTelephone.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}