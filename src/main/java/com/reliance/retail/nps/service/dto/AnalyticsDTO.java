package com.reliance.retail.nps.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class AnalyticsDTO implements Serializable {

    private Long id;
    @NotNull
    private String code;

    private String event;

    private Long campaignId;

    private LocalDate eventHitTime;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public LocalDate getEventHitTime() {
        return eventHitTime;
    }

    public void setEventHitTime(LocalDate eventHitTime) {
        this.eventHitTime = eventHitTime;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AnalyticsDTO{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", event='" + event + '\'' +
            ", campaignId='" + campaignId + '\'' +
            ", eventHitTime=" + eventHitTime +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
