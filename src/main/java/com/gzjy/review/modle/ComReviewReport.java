package com.gzjy.review.modle;

import java.util.Date;

public class ComReviewReport {
    private String reviewReportId;

    private String companyId;

    private String evaluateProductType;

    private String evaluateStage;

    private String evaluateSketch;

    private Date checkDate;

    private Date endDate;

    private String companyRepresent;

    private String reviewResult;

    private Double score;

    private String fastResult;

    private String fuheUser;

    private String pizhunUser;

    private String reportStatus;

    public String getReviewReportId() {
        return reviewReportId;
    }

    public void setReviewReportId(String reviewReportId) {
        this.reviewReportId = reviewReportId == null ? null : reviewReportId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getEvaluateProductType() {
        return evaluateProductType;
    }

    public void setEvaluateProductType(String evaluateProductType) {
        this.evaluateProductType = evaluateProductType == null ? null : evaluateProductType.trim();
    }

    public String getEvaluateStage() {
        return evaluateStage;
    }

    public void setEvaluateStage(String evaluateStage) {
        this.evaluateStage = evaluateStage == null ? null : evaluateStage.trim();
    }

    public String getEvaluateSketch() {
        return evaluateSketch;
    }

    public void setEvaluateSketch(String evaluateSketch) {
        this.evaluateSketch = evaluateSketch == null ? null : evaluateSketch.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCompanyRepresent() {
        return companyRepresent;
    }

    public void setCompanyRepresent(String companyRepresent) {
        this.companyRepresent = companyRepresent == null ? null : companyRepresent.trim();
    }

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult == null ? null : reviewResult.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFastResult() {
        return fastResult;
    }

    public void setFastResult(String fastResult) {
        this.fastResult = fastResult == null ? null : fastResult.trim();
    }

    public String getFuheUser() {
        return fuheUser;
    }

    public void setFuheUser(String fuheUser) {
        this.fuheUser = fuheUser == null ? null : fuheUser.trim();
    }

    public String getPizhunUser() {
        return pizhunUser;
    }

    public void setPizhunUser(String pizhunUser) {
        this.pizhunUser = pizhunUser == null ? null : pizhunUser.trim();
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus == null ? null : reportStatus.trim();
    }
}