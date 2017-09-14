package com.gzjy.template.model;

import java.util.Date;

public class Template {
	private String id;
	private String name;	
	private String description;
	private Date createdAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}	
}
