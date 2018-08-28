package com.gzjy.contract.model;

public class Sample {
	//样品编号
	private String id;
	//样品名称
	private String name;
	//检测依据
    private String detectBy;	
	//质量等级
    private String qualityLevel;	
	//加工工艺
    private String processTechnology;
    //样品形状
    private String sampleShape;
    //生产日期或批号
    private String productDate;
    //规格数量
    private String specificationQuantity;
    //保存条件
    private String storageCondition;
    //保 存 期
    private String storageTime;
    //样品处理要求
    private String processDemand;
    //执行标准
    private String executeStandard;
    //合同编号
    private String contractId;
    
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
	public String getDetectBy() {
		return detectBy;
	}
	public void setDetectBy(String detectBy) {
		this.detectBy = detectBy;
	}
	public String getQualityLevel() {
		return qualityLevel;
	}
	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}
	public String getProcessTechnology() {
		return processTechnology;
	}
	public void setProcessTechnology(String processTechnology) {
		this.processTechnology = processTechnology;
	}
	public String getSampleShape() {
		return sampleShape;
	}
	public void setSampleShape(String sampleShape) {
		this.sampleShape = sampleShape;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getSpecificationQuantity() {
		return specificationQuantity;
	}
	public void setSpecificationQuantity(String specificationQuantity) {
		this.specificationQuantity = specificationQuantity;
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
	public String getProcessDemand() {
		return processDemand;
	}
	public void setProcessDemand(String processDemand) {
		this.processDemand = processDemand;
	}
	public String getExecuteStandard() {
		return executeStandard;
	}
	public void setExecuteStandard(String executeStandard) {
		this.executeStandard = executeStandard;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}	
	
}
