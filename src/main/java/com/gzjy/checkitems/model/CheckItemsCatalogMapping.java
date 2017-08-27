package com.gzjy.checkitems.model;

public class CheckItemsCatalogMapping {
	//序号
	private String id; 
	//名称目录编号
	private String catalogId;
	//检测方法
	private String checkItemId;
	//实验室
	private String laboratory;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getCheckItemId() {
		return checkItemId;
	}
	public void setCheckItemId(String checkItemId) {
		this.checkItemId = checkItemId;
	}
	public String getLaboratory() {
		return laboratory;
	}
	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
	}	
}
