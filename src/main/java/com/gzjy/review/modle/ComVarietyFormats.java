package com.gzjy.review.modle;

import java.util.Date;

public class ComVarietyFormats {
    private String id;

    private String comType;

    private String liebie;

    private String comVariety;

    private Double score;

    private Date updatedAt;

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

    public String getLiebie() {
        return liebie;
    }

    public void setLiebie(String liebie) {
        this.liebie = liebie == null ? null : liebie.trim();
    }

    public String getComVariety() {
        return comVariety;
    }

    public void setComVariety(String comVariety) {
        this.comVariety = comVariety == null ? null : comVariety.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}