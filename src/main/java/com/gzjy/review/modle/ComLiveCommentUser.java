package com.gzjy.review.modle;

public class ComLiveCommentUser {
    private String id;

    private String name;

    private String position;

    private String telephone;

    private String firstMeeting;

    private String liveMeeting;

    private String lastMeeting;

    private String reviewReportId;

    private String identity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getFirstMeeting() {
        return firstMeeting;
    }

    public void setFirstMeeting(String firstMeeting) {
        this.firstMeeting = firstMeeting == null ? null : firstMeeting.trim();
    }

    public String getLiveMeeting() {
        return liveMeeting;
    }

    public void setLiveMeeting(String liveMeeting) {
        this.liveMeeting = liveMeeting == null ? null : liveMeeting.trim();
    }

    public String getLastMeeting() {
        return lastMeeting;
    }

    public void setLastMeeting(String lastMeeting) {
        this.lastMeeting = lastMeeting == null ? null : lastMeeting.trim();
    }

    public String getReviewReportId() {
        return reviewReportId;
    }

    public void setReviewReportId(String reviewReportId) {
        this.reviewReportId = reviewReportId == null ? null : reviewReportId.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }
}