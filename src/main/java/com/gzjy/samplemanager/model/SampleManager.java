package com.gzjy.samplemanager.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SampleManager {
    private String sampleId;

    private String reportId;

    private String sampleName;
   //检样，留样
    private String managerType;

    private Integer zhibaoqi;  //单位天
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sampleCirculateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiryTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;
   //normal, abandon
    private String status;

    private Date createdAt;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType == null ? null : managerType.trim();
    }

    public Integer getZhibaoqi() {
        return zhibaoqi;
    }

    public void setZhibaoqi(Integer zhibaoqi) {
        this.zhibaoqi = zhibaoqi;
    }

    public Date getSampleCirculateDate() {
        return sampleCirculateDate;
    }

    public void setSampleCirculateDate(Date sampleCirculateDate) {
        this.sampleCirculateDate = sampleCirculateDate;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}