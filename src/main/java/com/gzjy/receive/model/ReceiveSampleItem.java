package com.gzjy.receive.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class ReceiveSampleItem {
    //接样检验项目的id
    private String id;
    //接样编号
    @NotEmpty
    private String receiveSampleId;
    //项目名称
    @NotEmpty
    private String name;
    //检测方法
    @NotEmpty
    private String method;
    //单位
    private String unit;
    //标准值
    private String standardValue;
    //检出限
    private String detectionLimit;
    //定量限
    private String quantitationLimit;
    //设备
    private String device;
    //价格
    private Double defaultPrice;
    //检验室
    private String testRoom;
    //分包
    private String subpackage;
    //结果
    private String itemResult;
   //实测值
    private String measuredValue;
    //检验员
    private String testUser;
    //检验项状态
    private Integer status;
    //更新时间
    private Date updatedAt;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReceiveSampleId() {
        return receiveSampleId;
    }

    public void setReceiveSampleId(String receiveSampleId) {
        this.receiveSampleId = receiveSampleId == null ? null : receiveSampleId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue == null ? null : standardValue.trim();
    }

    public String getDetectionLimit() {
        return detectionLimit;
    }

    public void setDetectionLimit(String detectionLimit) {
        this.detectionLimit = detectionLimit == null ? null : detectionLimit.trim();
    }

    public String getQuantitationLimit() {
        return quantitationLimit;
    }

    public void setQuantitationLimit(String quantitationLimit) {
        this.quantitationLimit = quantitationLimit == null ? null : quantitationLimit.trim();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device == null ? null : device.trim();
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getTestRoom() {
        return testRoom;
    }

    public void setTestRoom(String testRoom) {
        this.testRoom = testRoom == null ? null : testRoom.trim();
    }

    public String getSubpackage() {
        return subpackage;
    }

    public void setSubpackage(String subpackage) {
        this.subpackage = subpackage == null ? null : subpackage.trim();
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult == null ? null : itemResult.trim();
    }

    public String getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue == null ? null : measuredValue.trim();
    }

    public String getTestUser() {
        return testUser;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser == null ? null : testUser.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}