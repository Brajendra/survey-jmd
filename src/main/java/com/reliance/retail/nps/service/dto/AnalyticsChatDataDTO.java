package com.reliance.retail.nps.service.dto;

import java.io.Serializable;

public class AnalyticsChatDataDTO implements Serializable {

    Long campaignId;

    Long attempted;

    Long submitted;

    Long published;

    String title;

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getAttempted() {
        return attempted;
    }

    public void setAttempted(Long attempted) {
        this.attempted = attempted;
    }

    public Long getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Long submitted) {
        this.submitted = submitted;
    }

    public Long getPublished() {
        return published;
    }

    public void setPublished(Long published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AnalyticsChatDataDTO{" +
            "campaignId=" + campaignId +
            ", attempted=" + attempted +
            ", submitted=" + submitted +
            ", published=" + published +
            ", title='" + title + '\'' +
            '}';
    }
}
