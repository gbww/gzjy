package com.gzjy.log.model;

import java.util.Date;

import org.joda.time.DateTime;

public class LogModel {	
	private String id;
	private String operateUserId;
	private String operateUser;
	private String operation;
	private String loginIp;
	private Date createTime;
	private String extra;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
}
