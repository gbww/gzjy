package com.gzjy.client.model;

import java.util.Date;

public class ClientScheduler {
    private String id;

    private String schedulerName;   //日程名

    private String schedulerAddress;   //日程地址

    private String executor;         //执行人
 
    private Date schedulerStartTime;  //日程开始时间

    private Date schedulerEndTime;    //  日程结束时间

    private String schedulerDesc;     //日程描述

    private String accessory;        //附件

    private Integer clientNum;       //客户编号

    private String schedulerRepetition;   //日程重复
 
    private String schedulerRemind;      //日程提醒

    private Integer effective;       //有效性:1:有效，0：无效

    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName == null ? null : schedulerName.trim();
    }

    public String getSchedulerAddress() {
        return schedulerAddress;
    }

    public void setSchedulerAddress(String schedulerAddress) {
        this.schedulerAddress = schedulerAddress == null ? null : schedulerAddress.trim();
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public Date getSchedulerStartTime() {
        return schedulerStartTime;
    }

    public void setSchedulerStartTime(Date schedulerStartTime) {
        this.schedulerStartTime = schedulerStartTime;
    }

    public Date getSchedulerEndTime() {
        return schedulerEndTime;
    }

    public void setSchedulerEndTime(Date schedulerEndTime) {
        this.schedulerEndTime = schedulerEndTime;
    }

    public String getSchedulerDesc() {
        return schedulerDesc;
    }

    public void setSchedulerDesc(String schedulerDesc) {
        this.schedulerDesc = schedulerDesc == null ? null : schedulerDesc.trim();
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory == null ? null : accessory.trim();
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public void setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
    }

    public String getSchedulerRepetition() {
        return schedulerRepetition;
    }

    public void setSchedulerRepetition(String schedulerRepetition) {
        this.schedulerRepetition = schedulerRepetition == null ? null : schedulerRepetition.trim();
    }

    public String getSchedulerRemind() {
        return schedulerRemind;
    }

    public void setSchedulerRemind(String schedulerRemind) {
        this.schedulerRemind = schedulerRemind == null ? null : schedulerRemind.trim();
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}