package com.gzjy.contract.model;

import java.util.Date;

public class Contract {
	//合同编号    
	private String id;
    //样品编号
    private String sampleId;
    //协议编号
    private String protocolId;
    //样品名称
    private String sampleName;
    //加工工艺
    private String processTechnology;
    //质量等级
    private String qualityLevel;
    //规格数量
    private String specificationQuantity;
    //生产日期或批号
    private String productDate;
    //保存条件
    private String storageCondition;
    //保 存 期
    private String storageTime;
    //样品性状
    private String sampleTraits;
    //样品处理要求
    private String processDemand;
    //检测目的
    private String detectPurpose;
    //提供的其它有关资料
    private String otherData;
    //检测类别
    private String detectType;
    //检  测  项  目
    private String detectProject;
    //执行标准
    private String executeStandard;
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
    private Integer reportCount;
    //送检人
    private String inspectionUser;
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
    //加工工艺
    private String activity1;
    //备注
    private String extra;
    //状态
    private Integer status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	public String getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public String getProcessTechnology() {
		return processTechnology;
	}
	public void setProcessTechnology(String processTechnology) {
		this.processTechnology = processTechnology;
	}
	public String getQualityLevel() {
		return qualityLevel;
	}
	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}
	public String getSpecificationQuantity() {
		return specificationQuantity;
	}
	public void setSpecificationQuantity(String specificationQuantity) {
		this.specificationQuantity = specificationQuantity;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getStorageCondition() {
		return storageCondition;
	}
	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}
	public String getStorageTime() {
		return storageTime;
	}
	public void setStorageTime(String storageTime) {
		this.storageTime = storageTime;
	}
	public String getSampleTraits() {
		return sampleTraits;
	}
	public void setSampleTraits(String sampleTraits) {
		this.sampleTraits = sampleTraits;
	}
	public String getProcessDemand() {
		return processDemand;
	}
	public void setProcessDemand(String processDemand) {
		this.processDemand = processDemand;
	}
	public String getDetectPurpose() {
		return detectPurpose;
	}
	public void setDetectPurpose(String detectPurpose) {
		this.detectPurpose = detectPurpose;
	}
	public String getOtherData() {
		return otherData;
	}
	public void setOtherData(String otherData) {
		this.otherData = otherData;
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
	public String getExecuteStandard() {
		return executeStandard;
	}
	public void setExecuteStandard(String executeStandard) {
		this.executeStandard = executeStandard;
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
	public Integer getReportCount() {
		return reportCount;
	}
	public void setReportCount(Integer reportCount) {
		this.reportCount = reportCount;
	}
	public String getInspectionUser() {
		return inspectionUser;
	}
	public void setInspectionUser(String inspectionUser) {
		this.inspectionUser = inspectionUser;
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
	public String getActivity1() {
		return activity1;
	}
	public void setActivity1(String activity1) {
		this.activity1 = activity1;
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
	
}