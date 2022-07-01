package com.reliance.retail.nps.repository;

import com.reliance.retail.nps.domain.Question;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  /*  @Query(value = "select * from question q Left JOIN answer ON q.id = answer.question_id where q.campaign_id = :campaignId", nativeQuery = true)
    Optional<List<Question>> getByCampaignId(@Param("campaignId") final Long campaignId);*/
    Optional<List<Question>> findByCampaignId(final Long campaignId);
}
