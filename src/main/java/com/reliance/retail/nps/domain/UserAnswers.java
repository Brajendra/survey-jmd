package com.reliance.retail.nps.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserAnswers.
 */
@Entity
@Table(name = "user_answers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAnswers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "campaign_link_id")
    private Long campaignLinkId;

    @Column(name = "answers")
    private String answers;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserAnswers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignLinkId() {
        return this.campaignLinkId;
    }

    public UserAnswers campaignLinkId(Long campaignLinkId) {
        this.setCampaignLinkId(campaignLinkId);
        return this;
    }

    public void setCampaignLinkId(Long campaignLinkId) {
        this.campaignLinkId = campaignLinkId;
    }

    public String getAnswers() {
        return this.answers;
    }

    public UserAnswers answers(String answers) {
        this.setAnswers(answers);
        return this;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public UserAnswers createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public UserAnswers updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAnswers)) {
            return false;
        }
        return id != null && id.equals(((UserAnswers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAnswers{" +
            "id=" + getId() +
            ", campaignLinkId=" + getCampaignLinkId() +
            ", answers='" + getAnswers() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
