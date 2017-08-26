package com.gzjy.checkitems.model;

public class CheckItemsCatalogMapping {
	//序号
	private String id; 
	//名称
	private String name;
	//检测方法
	private String method;
	//实验室
	private String laboratory;
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
	//目录编号
	private String catalogId;
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
	public String getLaboratory() {
		return laboratory;
	}
	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
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
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
	
}
