package com.reliance.retail.nps.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reliance.retail.nps.domain.enumeration.ActorType;
import com.reliance.retail.nps.domain.enumeration.ChannelType;
import com.reliance.retail.nps.domain.enumeration.EventType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor")
    private ActorType actor;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private ChannelType channel;

    @OneToMany(mappedBy = "campaign")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "campaign" }, allowSetters = true)
    private Set<CampaignLink> campaignLinks = new HashSet<>();

    @OneToMany(mappedBy = "campaign")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "answers", "campaign" }, allowSetters = true)
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Campaign id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Campaign name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Campaign isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Campaign createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Campaign startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Campaign endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Campaign createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Campaign updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ActorType getActor() {
        return this.actor;
    }

    public Campaign actor(ActorType actor) {
        this.setActor(actor);
        return this;
    }

    public void setActor(ActorType actor) {
        this.actor = actor;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public Campaign eventType(EventType eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public ChannelType getChannel() {
        return this.channel;
    }

    public Campaign channel(ChannelType channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public Set<CampaignLink> getCampaignLinks() {
        return this.campaignLinks;
    }

    public void setCampaignLinks(Set<CampaignLink> campaignLinks) {
        if (this.campaignLinks != null) {
            this.campaignLinks.forEach(i -> i.setCampaign(null));
        }
        if (campaignLinks != null) {
            campaignLinks.forEach(i -> i.setCampaign(this));
        }
        this.campaignLinks = campaignLinks;
    }

    public Campaign campaignLinks(Set<CampaignLink> campaignLinks) {
        this.setCampaignLinks(campaignLinks);
        return this;
    }

    public Campaign addCampaignLink(CampaignLink campaignLink) {
        this.campaignLinks.add(campaignLink);
        campaignLink.setCampaign(this);
        return this;
    }

    public Campaign removeCampaignLink(CampaignLink campaignLink) {
        this.campaignLinks.remove(campaignLink);
        campaignLink.setCampaign(null);
        return this;
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<Question> questions) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setCampaign(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setCampaign(this));
        }
        this.questions = questions;
    }

    public Campaign questions(Set<Question> questions) {
        this.setQuestions(questions);
        return this;
    }

    public Campaign addQuestion(Question question) {
        this.questions.add(question);
        question.setCampaign(this);
        return this;
    }

    public Campaign removeQuestion(Question question) {
        this.questions.remove(question);
        question.setCampaign(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        return id != null && id.equals(((Campaign) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", actor='" + getActor() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
