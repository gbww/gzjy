package com.gzjy.checkitems.model;

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
	
	private String device;
	private double defaultPrice;
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
	
	
}
