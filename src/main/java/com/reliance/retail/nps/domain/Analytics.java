package com.reliance.retail.nps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "analytics", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Analytics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "event")
    private String event;

    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "event_hit_time")
    private LocalDate eventHitTime;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
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
    public int hashCode() {
        return Objects.hash(id, code, event, campaignId, eventHitTime, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Analytics{" +
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

