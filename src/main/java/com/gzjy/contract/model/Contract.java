package com.gzjy.contract.model;

import java.util.Date;

public class Contract {
	//合同编号    
	private String id;
    //样品性状
    private String sampleTraits;    
    //检测目的
    private String detectPurpose;    
    //检测类别
    private String detectType;
    //检测项目
    private String detectProject;    
    //检测依据
    private String detectBy;
    //是否同意使用非标准方法
    private Byte isUseStandard;
    //是否同意分包
    private Byte isSubcontracting;
    //是否需要加急
    private Byte isExpedited;
    //是否需要评价
    private Byte isEvaluation;
    //生产单位
    private String productUnit;
    //送检单位
    private String inspectionUnit;
    //采样地点
    private String samplingAddress;
    //送检单位地址
    private String inspectionUnitAddress;
    //邮  编
    private String zipCode;
    //电  话
    private String phone;
    //传真
    private String fax;
    //联系人
    private String contactPerson;
    //取报告方式
    private String reportMethod;
    //需检测报告份数
    private String reportCount;   
    //送检日期
    private Date inspectionDate;
    //受理人
    private String acceptor;
    //受理日期
    private Date acceptanceDate;
    //检测费用
    private Float cost;
    //对应流程ID
    private String processId;    
    //备注
    private String extra;
    //状态
    private Integer status;    
    //创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;
    //附件
	private String appendix;
	//合同类别
	private String type;
	//委托单位名称
	private String entrustment;
	//委托单位地址
	private String entrustmentAddress;
	//流水号
	private String serialNumber;
	//项目负责人号
	private String projectLeader;
	//项目名称
	private String projectName;
	//任务类别
	private String taskCategory;
	//样品类型
	private String sampleType;
	//抽检环节
	private String inspection;
	//任务批次
	private String taskBatch;
	//结算方式
	private String settlementMethod;
	//是否单独结算
	private Byte isSeparateSettlement;
	//签订日期
    private Date signDate;
    //合同有效期
    private String contractTerm;
    //开户银行
    private String depositBank;
    //开户银行账号
    private String account;
    //认证类别
    private String authCategory;
    //认证类型
    private String authType;
    //产品类别
    private String productType;
    //企业提供的文本资料
    private String enterpriseFile;
    //时限要求
    private String limitTime;
    //现场检查时间
    private Date inspectTime;
    //续展到期时间
    private Date expireTime;
    //合同签订时间
    private Date contractSignTime;
    //报告寄送地址
    private String reportSendAddress;
    //发票寄送地址
    private String invoiceSendAddress;
    //是否删除
    private Byte isDelete;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getSampleTraits() {
		return sampleTraits;
	}
	public void setSampleTraits(String sampleTraits) {
		this.sampleTraits = sampleTraits;
	}	
	public String getDetectPurpose() {
		return detectPurpose;
	}
	public void setDetectPurpose(String detectPurpose) {
		this.detectPurpose = detectPurpose;
	}
	public String getDetectType() {
		return detectType;
	}
	public void setDetectType(String detectType) {
		this.detectType = detectType;
	}
	public String getDetectProject() {
		return detectProject;
	}
	public void setDetectProject(String detectProject) {
		this.detectProject = detectProject;
	}	
	public String getDetectBy() {
		return detectBy;
	}
	public void setDetectBy(String detectBy) {
		this.detectBy = detectBy;
	}
	public Byte getIsUseStandard() {
		return isUseStandard;
	}
	public void setIsUseStandard(Byte isUseStandard) {
		this.isUseStandard = isUseStandard;
	}
	public Byte getIsSubcontracting() {
		return isSubcontracting;
	}
	public void setIsSubcontracting(Byte isSubcontracting) {
		this.isSubcontracting = isSubcontracting;
	}
	public Byte getIsExpedited() {
		return isExpedited;
	}
	public void setIsExpedited(Byte isExpedited) {
		this.isExpedited = isExpedited;
	}
	public Byte getIsEvaluation() {
		return isEvaluation;
	}
	public void setIsEvaluation(Byte isEvaluation) {
		this.isEvaluation = isEvaluation;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getInspectionUnit() {
		return inspectionUnit;
	}
	public void setInspectionUnit(String inspectionUnit) {
		this.inspectionUnit = inspectionUnit;
	}
	public String getSamplingAddress() {
		return samplingAddress;
	}
	public void setSamplingAddress(String samplingAddress) {
		this.samplingAddress = samplingAddress;
	}
	public String getInspectionUnitAddress() {
		return inspectionUnitAddress;
	}
	public void setInspectionUnitAddress(String inspectionUnitAddress) {
		this.inspectionUnitAddress = inspectionUnitAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getReportMethod() {
		return reportMethod;
	}
	public void setReportMethod(String reportMethod) {
		this.reportMethod = reportMethod;
	}
	public String getReportCount() {
		return reportCount;
	}
	public void setReportCount(String reportCount) {
		this.reportCount = reportCount;
	}
	public Date getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public String getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getAppendix() {
		return appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEntrustment() {
		return entrustment;
	}
	public void setEntrustment(String entrustment) {
		this.entrustment = entrustment;
	}
	public String getEntrustmentAddress() {
		return entrustmentAddress;
	}
	public void setEntrustmentAddress(String entrustmentAddress) {
		this.entrustmentAddress = entrustmentAddress;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getProjectLeader() {
		return projectLeader;
	}
	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTaskCategory() {
		return taskCategory;
	}
	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	public String getInspection() {
		return inspection;
	}
	public void setInspection(String inspection) {
		this.inspection = inspection;
	}
	public String getTaskBatch() {
		return taskBatch;
	}
	public void setTaskBatch(String taskBatch) {
		this.taskBatch = taskBatch;
	}
	public String getSettlementMethod() {
		return settlementMethod;
	}
	public void setSettlementMethod(String settlementMethod) {
		this.settlementMethod = settlementMethod;
	}
	public Byte getIsSeparateSettlement() {
		return isSeparateSettlement;
	}
	public void setIsSeparateSettlement(Byte isSeparateSettlement) {
		this.isSeparateSettlement = isSeparateSettlement;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getContractTerm() {
		return contractTerm;
	}
	public void setContractTerm(String contractTerm) {
		this.contractTerm = contractTerm;
	}
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAuthCategory() {
		return authCategory;
	}
	public void setAuthCategory(String authCategory) {
		this.authCategory = authCategory;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getEnterpriseFile() {
		return enterpriseFile;
	}
	public void setEnterpriseFile(String enterpriseFile) {
		this.enterpriseFile = enterpriseFile;
	}
	public String getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	public Date getInspectTime() {
		return inspectTime;
	}
	public void setInspectTime(Date inspectTime) {
		this.inspectTime = inspectTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Date getContractSignTime() {
		return contractSignTime;
	}
	public void setContractSignTime(Date contractSignTime) {
		this.contractSignTime = contractSignTime;
	}
	public String getReportSendAddress() {
		return reportSendAddress;
	}
	public void setReportSendAddress(String reportSendAddress) {
		this.reportSendAddress = reportSendAddress;
	}
	public String getInvoiceSendAddress() {
		return invoiceSendAddress;
	}
	public void setInvoiceSendAddress(String invoiceSendAddress) {
		this.invoiceSendAddress = invoiceSendAddress;
	}
	public Byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}    
}