package com.gzjy.checkitems.model;

import java.util.Date;

public class CheckItem {
	//序号
	private String id; 
	//名称
	private String name;
	//检测方法
	private String method;	
	//单位
	private String unit;
	//标准值
	private String standardValue;
	//检出限
	private String detectionLimit;
	//定量限
	private String quantitationLimit;
	//设备仪器
	private String device;
	//默认价格
	private double defaultPrice;
	//创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;	
    //默认分配的部门
    private String department;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStandardValue() {
		return standardValue;
	}
	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}
	public String getDetectionLimit() {
		return detectionLimit;
	}
	public void setDetectionLimit(String detectionLimit) {
		this.detectionLimit = detectionLimit;
	}
	public String getQuantitationLimit() {
		return quantitationLimit;
	}
	public void setQuantitationLimit(String quantitationLimit) {
		this.quantitationLimit = quantitationLimit;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public double getDefaultPrice() {
		return defaultPrice;
	}
	public void setDefaultPrice(double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}	
}
