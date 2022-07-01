package com.reliance.retail.nps.repository;

import com.reliance.retail.nps.domain.UserAnswers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the UserAnswers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAnswersRepository extends JpaRepository<UserAnswers, Long> {

    Optional<Boolean> existsByCampaignLinkId(final Long campaignLinkId);
}
