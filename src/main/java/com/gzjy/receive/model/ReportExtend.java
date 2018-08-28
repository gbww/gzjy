package com.gzjy.receive.model;

import java.io.Serializable;

public class ReportExtend implements Serializable{
   
    private static final long serialVersionUID = 3186945089719476726L;
    
    private String id;  
    //报告编号
    private String reportId;   //查询
    //模板编号
    private String templateId;
    //模板名称
    private String templateName;
    //模板描述
    private String templateDesc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}