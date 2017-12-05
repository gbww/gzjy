package com.gzjy.client.model;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public class SchedulerReport {
    private String id;

    private String clientSchedulerId;

    private String reportContent;

    private Date createdAt;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClientSchedulerId() {
        return clientSchedulerId;
    }

    public void setClientSchedulerId(String clientSchedulerId) {
        this.clientSchedulerId = clientSchedulerId == null ? null : clientSchedulerId.trim();
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent == null ? null : reportContent.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}