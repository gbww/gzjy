package com.gzjy.contract.model;

import java.util.Date;

public class Contract {
    private String id;

    private String sampleId;

    private String protocolId;

    private String sampleName;

    private String processTechnology;

    private String qualityLevel;

    private String specificationQuantity;

    private String productDate;

    private String storageCondition;

    private Date storageTime;

    private String sampleTraits;

    private String processDemand;

    private String detectPurpose;

    private String otherData;

    private String detectType;

    private String detectProject;

    private String executeStandard;

    private String detectBy;

    private Byte isUseStandard;

    private Byte isSubcontracting;

    private Byte isExpedited;

    private Byte isEvaluation;

    private String productUnit;

    private String inspectionUnit;

    private String samplingAddress;

    private String inspectionUnitAddress;

    private String zipCode;

    private String phone;

    private String fax;

    private String contactPerson;

    private String reportMethod;

    private Integer reportCount;

    private String inspectionUser;

    private Date inspectionDate;

    private String acceptor;

    private Date acceptanceDate;

    private Float cost;

    private String activity1;

    private String activity2;

    private String extra;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId == null ? null : protocolId.trim();
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getProcessTechnology() {
        return processTechnology;
    }

    public void setProcessTechnology(String processTechnology) {
        this.processTechnology = processTechnology == null ? null : processTechnology.trim();
    }

    public String getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel) {
        this.qualityLevel = qualityLevel == null ? null : qualityLevel.trim();
    }

    public String getSpecificationQuantity() {
        return specificationQuantity;
    }

    public void setSpecificationQuantity(String specificationQuantity) {
        this.specificationQuantity = specificationQuantity == null ? null : specificationQuantity.trim();
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate == null ? null : productDate.trim();
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public void setStorageCondition(String storageCondition) {
        this.storageCondition = storageCondition == null ? null : storageCondition.trim();
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public String getSampleTraits() {
        return sampleTraits;
    }

    public void setSampleTraits(String sampleTraits) {
        this.sampleTraits = sampleTraits == null ? null : sampleTraits.trim();
    }

    public String getProcessDemand() {
        return processDemand;
    }

    public void setProcessDemand(String processDemand) {
        this.processDemand = processDemand == null ? null : processDemand.trim();
    }

    public String getDetectPurpose() {
        return detectPurpose;
    }

    public void setDetectPurpose(String detectPurpose) {
        this.detectPurpose = detectPurpose == null ? null : detectPurpose.trim();
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData == null ? null : otherData.trim();
    }

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType == null ? null : detectType.trim();
    }

    public String getDetectProject() {
        return detectProject;
    }

    public void setDetectProject(String detectProject) {
        this.detectProject = detectProject == null ? null : detectProject.trim();
    }

    public String getExecuteStandard() {
        return executeStandard;
    }

    public void setExecuteStandard(String executeStandard) {
        this.executeStandard = executeStandard == null ? null : executeStandard.trim();
    }

    public String getDetectBy() {
        return detectBy;
    }

    public void setDetectBy(String detectBy) {
        this.detectBy = detectBy == null ? null : detectBy.trim();
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
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public String getInspectionUnit() {
        return inspectionUnit;
    }

    public void setInspectionUnit(String inspectionUnit) {
        this.inspectionUnit = inspectionUnit == null ? null : inspectionUnit.trim();
    }

    public String getSamplingAddress() {
        return samplingAddress;
    }

    public void setSamplingAddress(String samplingAddress) {
        this.samplingAddress = samplingAddress == null ? null : samplingAddress.trim();
    }

    public String getInspectionUnitAddress() {
        return inspectionUnitAddress;
    }

    public void setInspectionUnitAddress(String inspectionUnitAddress) {
        this.inspectionUnitAddress = inspectionUnitAddress == null ? null : inspectionUnitAddress.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getReportMethod() {
        return reportMethod;
    }

    public void setReportMethod(String reportMethod) {
        this.reportMethod = reportMethod == null ? null : reportMethod.trim();
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
        this.inspectionUser = inspectionUser == null ? null : inspectionUser.trim();
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
        this.acceptor = acceptor == null ? null : acceptor.trim();
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

    public String getActivity1() {
        return activity1;
    }

    public void setActivity1(String activity1) {
        this.activity1 = activity1 == null ? null : activity1.trim();
    }

    public String getActivity2() {
        return activity2;
    }

    public void setActivity2(String activity2) {
        this.activity2 = activity2 == null ? null : activity2.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}