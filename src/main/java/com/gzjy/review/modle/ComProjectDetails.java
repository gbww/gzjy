package com.gzjy.review.modle;

public class ComProjectDetails {
    private String id;

    private String comType;

    private String projectName;

    private String numberRegulation;

    private String content;

    private Double standardScore;

    private String checkWay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType == null ? null : comType.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getNumberRegulation() {
        return numberRegulation;
    }

    public void setNumberRegulation(String numberRegulation) {
        this.numberRegulation = numberRegulation == null ? null : numberRegulation.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Double getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(Double standardScore) {
        this.standardScore = standardScore;
    }

    public String getCheckWay() {
        return checkWay;
    }

    public void setCheckWay(String checkWay) {
        this.checkWay = checkWay == null ? null : checkWay.trim();
    }
}