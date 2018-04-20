package com.gzjy.receive.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class ReceiveSample implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 3186945089719476726L;
    //抽样单编号
    @NotEmpty
    private String receiveSampleId;  //查询
    //报告编号
    private String reportId;   //查询
    
    //样品类别
    @NotEmpty
    private String sampleType;
    //检验类型
    @NotEmpty
    private String checkType;
  
    //委托单位（或委托人）
    private String entrustedUnit;   //查询
    //委托单位地址
    private String entrustedUnitAddress;
    //委托人号码
    private String entrustedUserPhone;
    //委托人邮箱(暂时保存)
    private String entrustedUserEmail;
    //受检单位
    private String inspectedUnit;   //查询
    //受检单位地址
    private String inspectedUnitAddress;
    //受检联系人
    private String inspectedUser;
    //受检联系人电话
    private String inspectedUserPhone;
     //受检联系人邮箱（暂时保存）
    private String inspectedUserEmail;
    //抽样地点
    private String sampleAddress;
    //抽样名称
    private String sampleName;  //查询
    //抽样环节
    private String sampleLink;
    //商标
    private String sampleTrademark;
   //样品流通方式
    private String sampleCirculate;
   //样品流通日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sampleCirculateDate;
    //抽样时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sampleDate;
   //抽样方式
    private String sampleWay;
    //规格型号
    private String specificationModel;
     //执行标准
    @NotEmpty
    private String executeStandard;   //查询
    //抽样人或单位
    private String sampleNames;
    //等级或加工工艺
    private String processingTechnology;
    //封样状态
    private String closedStatus;
    //样品数
    private String sampleNumber;
    
  //抽样单位
    private String sampleUnit;
    //样品状态
    private String sampleStatus;
    //抽样基数
    private String sampleBasenumber;
    //保存条件
    private String saveWay;
    //生产单位
    private String productionUnit;  //查询
    //生成单位地址
    private String productionAddress;
    //生成单位联系人
    private String productionUser;
    //生成单位联系人号码
    private String productionPhone;
     //价格
    private String cost;
     //备注
    private String remarks;
    //数据页备注
    private String dataRemarks;
    //业务负责人
    private String responsiblePerson;
    //交样人
    private String sampleHolder;
    //收样人
    private String receiveUser;
    //收样日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;
    //约定完成日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrangeFinishDate;
    //要求完成日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    //协议编号
    private String protocolId;
    //分包
    private String subpackage;
    //结论
    private String result;
    //判定
    private String determine;
    //批准人
    private String approvalUser;
    //审核人
    private String examineUser;
    //编制人
    private String drawUser;
    //主检人    
    private String principalInspector;
   //留样数量
    private String liuyangCount;
    //食品大类
    private String foodDalei;
    //食品亚类
    private String foodYalei;
    
    private String foodCiyalei;

    private String foodXilei;
    //签发日期
    private Date qianfaDate;
    //样品类型
    private String sampleLeixing;
    //包装分类
    private String packageClassify;
    //质保期
    private Integer zhibaoqi;
    //生成许可证
    private String licence;
    //报告流程id
    private String reportProcessId;
    //附件
    private String appendix;
    
    //接样状态(0:未完成，1：完成)5作为查询时判断是否是全部查询的判断
    private Integer status;
    //创建时间
    private Date createdAt;
    //报告状态（0:待编辑，1：待审核、2待批准、3、待打印，4、完成）5作为查询时判断是否是全部查询的判断
    private Integer reportStatus;
    //主要设备
    private String devices;

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getReceiveSampleId() {
        return receiveSampleId;
    }

    public void setReceiveSampleId(String receiveSampleId) {
        this.receiveSampleId = receiveSampleId == null ? null : receiveSampleId.trim();
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType == null ? null : sampleType.trim();
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }



    public String getEntrustedUnit() {
        return entrustedUnit;
    }

    public void setEntrustedUnit(String entrustedUnit) {
        this.entrustedUnit = entrustedUnit == null ? null : entrustedUnit.trim();
    }

    public String getEntrustedUnitAddress() {
        return entrustedUnitAddress;
    }

    public void setEntrustedUnitAddress(String entrustedUnitAddress) {
        this.entrustedUnitAddress = entrustedUnitAddress == null ? null : entrustedUnitAddress.trim();
    }



    public String getEntrustedUserPhone() {
        return entrustedUserPhone;
    }

    public void setEntrustedUserPhone(String entrustedUserPhone) {
        this.entrustedUserPhone = entrustedUserPhone == null ? null : entrustedUserPhone.trim();
    }

    public String getEntrustedUserEmail() {
        return entrustedUserEmail;
    }

    public void setEntrustedUserEmail(String entrustedUserEmail) {
        this.entrustedUserEmail = entrustedUserEmail == null ? null : entrustedUserEmail.trim();
    }

    public String getInspectedUnit() {
        return inspectedUnit;
    }

    public void setInspectedUnit(String inspectedUnit) {
        this.inspectedUnit = inspectedUnit == null ? null : inspectedUnit.trim();
    }

    public String getInspectedUnitAddress() {
        return inspectedUnitAddress;
    }

    public void setInspectedUnitAddress(String inspectedUnitAddress) {
        this.inspectedUnitAddress = inspectedUnitAddress == null ? null : inspectedUnitAddress.trim();
    }

    public String getInspectedUser() {
        return inspectedUser;
    }

    public void setInspectedUser(String inspectedUser) {
        this.inspectedUser = inspectedUser == null ? null : inspectedUser.trim();
    }

    public String getInspectedUserPhone() {
        return inspectedUserPhone;
    }

    public void setInspectedUserPhone(String inspectedUserPhone) {
        this.inspectedUserPhone = inspectedUserPhone == null ? null : inspectedUserPhone.trim();
    }

    public String getInspectedUserEmail() {
        return inspectedUserEmail;
    }

    public void setInspectedUserEmail(String inspectedUserEmail) {
        this.inspectedUserEmail = inspectedUserEmail == null ? null : inspectedUserEmail.trim();
    }

    public String getSampleAddress() {
        return sampleAddress;
    }

    public void setSampleAddress(String sampleAddress) {
        this.sampleAddress = sampleAddress == null ? null : sampleAddress.trim();
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getSampleLink() {
        return sampleLink;
    }

    public void setSampleLink(String sampleLink) {
        this.sampleLink = sampleLink == null ? null : sampleLink.trim();
    }

    public String getSampleTrademark() {
        return sampleTrademark;
    }

    public void setSampleTrademark(String sampleTrademark) {
        this.sampleTrademark = sampleTrademark == null ? null : sampleTrademark.trim();
    }



    public String getSampleCirculate() {
        return sampleCirculate;
    }

    public void setSampleCirculate(String sampleCirculate) {
        this.sampleCirculate = sampleCirculate == null ? null : sampleCirculate.trim();
    }

    public Date getSampleCirculateDate() {
        return sampleCirculateDate;
    }

    public void setSampleCirculateDate(Date sampleCirculateDate) {
        this.sampleCirculateDate = sampleCirculateDate;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getSampleWay() {
        return sampleWay;
    }

    public void setSampleWay(String sampleWay) {
        this.sampleWay = sampleWay == null ? null : sampleWay.trim();
    }

    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel == null ? null : specificationModel.trim();
    }

    public String getExecuteStandard() {
        return executeStandard;
    }

    public void setExecuteStandard(String executeStandard) {
        this.executeStandard = executeStandard == null ? null : executeStandard.trim();
    }

    public String getSampleNames() {
        return sampleNames;
    }

    public void setSampleNames(String sampleNames) {
        this.sampleNames = sampleNames == null ? null : sampleNames.trim();
    }

    public String getProcessingTechnology() {
        return processingTechnology;
    }

    public void setProcessingTechnology(String processingTechnology) {
        this.processingTechnology = processingTechnology == null ? null : processingTechnology.trim();
    }

    public String getClosedStatus() {
        return closedStatus;
    }

    public void setClosedStatus(String closedStatus) {
        this.closedStatus = closedStatus == null ? null : closedStatus.trim();
    }

  

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus == null ? null : sampleStatus.trim();
    }

   

    public String getSaveWay() {
        return saveWay;
    }

    public void setSaveWay(String saveWay) {
        this.saveWay = saveWay == null ? null : saveWay.trim();
    }

    public String getProductionUnit() {
        return productionUnit;
    }

    public void setProductionUnit(String productionUnit) {
        this.productionUnit = productionUnit == null ? null : productionUnit.trim();
    }

    public String getProductionAddress() {
        return productionAddress;
    }

    public void setProductionAddress(String productionAddress) {
        this.productionAddress = productionAddress == null ? null : productionAddress.trim();
    }

    public String getProductionUser() {
        return productionUser;
    }

    public void setProductionUser(String productionUser) {
        this.productionUser = productionUser == null ? null : productionUser.trim();
    }

    public String getProductionPhone() {
        return productionPhone;
    }

    public void setProductionPhone(String productionPhone) {
        this.productionPhone = productionPhone == null ? null : productionPhone.trim();
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost == null ? null : cost.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDataRemarks() {
        return dataRemarks;
    }

    public void setDataRemarks(String dataRemarks) {
        this.dataRemarks = dataRemarks == null ? null : dataRemarks.trim();
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson == null ? null : responsiblePerson.trim();
    }

    public String getSampleHolder() {
        return sampleHolder;
    }

    public void setSampleHolder(String sampleHolder) {
        this.sampleHolder = sampleHolder == null ? null : sampleHolder.trim();
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser == null ? null : receiveUser.trim();
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getArrangeFinishDate() {
        return arrangeFinishDate;
    }

    public void setArrangeFinishDate(Date arrangeFinishDate) {
        this.arrangeFinishDate = arrangeFinishDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId == null ? null : protocolId.trim();
    }

    public String getSubpackage() {
        return subpackage;
    }

    public void setSubpackage(String subpackage) {
        this.subpackage = subpackage == null ? null : subpackage.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getDetermine() {
        return determine;
    }

    public void setDetermine(String determine) {
        this.determine = determine == null ? null : determine.trim();
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser == null ? null : approvalUser.trim();
    }

    public String getExamineUser() {
        return examineUser;
    }

    public void setExamineUser(String examineUser) {
        this.examineUser = examineUser == null ? null : examineUser.trim();
    }

    public String getDrawUser() {
        return drawUser;
    }

    public void setDrawUser(String drawUser) {
        this.drawUser = drawUser == null ? null : drawUser.trim();
    }

    public String getPrincipalInspector() {
        return principalInspector;
    }

    public void setPrincipalInspector(String principalInspector) {
        this.principalInspector = principalInspector == null ? null : principalInspector.trim();
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

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }
    
   

    public String getFoodDalei() {
        return foodDalei;
    }

    public void setFoodDalei(String foodDalei) {
        this.foodDalei = foodDalei;
    }

    public String getFoodCiyalei() {
        return foodCiyalei;
    }

    public void setFoodCiyalei(String foodCiyalei) {
        this.foodCiyalei = foodCiyalei;
    }

    public String getFoodXilei() {
        return foodXilei;
    }

    public void setFoodXilei(String foodXilei) {
        this.foodXilei = foodXilei;
    }

    public Date getQianfaDate() {
        return qianfaDate;
    }

    public void setQianfaDate(Date qianfaDate) {
        this.qianfaDate = qianfaDate;
    }

    public String getSampleLeixing() {
        return sampleLeixing;
    }

    public void setSampleLeixing(String sampleLeixing) {
        this.sampleLeixing = sampleLeixing;
    }

    public Integer getZhibaoqi() {
        return zhibaoqi;
    }

    public void setZhibaoqi(Integer zhibaoqi) {
        this.zhibaoqi = zhibaoqi;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getReportProcessId() {
        return reportProcessId;
    }

    public void setReportProcessId(String reportProcessId) {
        this.reportProcessId = reportProcessId;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFoodYalei() {
        return foodYalei;
    }

    public void setFoodYalei(String foodYalei) {
        this.foodYalei = foodYalei;
    }

    public String getPackageClassify() {
        return packageClassify;
    }

    public void setPackageClassify(String packageClassify) {
        this.packageClassify = packageClassify;
    }



    public String getSampleUnit() {
        return sampleUnit;
    }

    public void setSampleUnit(String sampleUnit) {
        this.sampleUnit = sampleUnit;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getSampleBasenumber() {
        return sampleBasenumber;
    }

    public void setSampleBasenumber(String sampleBasenumber) {
        this.sampleBasenumber = sampleBasenumber;
    }

    public String getLiuyangCount() {
        return liuyangCount;
    }

    public void setLiuyangCount(String liuyangCount) {
        this.liuyangCount = liuyangCount;
    }



  

    
}