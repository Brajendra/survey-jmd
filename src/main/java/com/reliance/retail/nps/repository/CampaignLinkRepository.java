package com.reliance.retail.nps.repository;

import com.reliance.retail.nps.domain.CampaignLink;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Spring Data SQL repository for the CampaignLink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignLinkRepository extends JpaRepository<CampaignLink, Long> {

    Optional<CampaignLink> findByCode(final String code);
    Optional<Boolean> existsByCode(final String code);

//    @Query(value = "SELECT campaign_id, count(campaign_id) FROM public.campaign_link GROUP by campaign_id" , nativeQuery = true)

//    Optional<List<Map<Long,Long>>>findCountByOccurrence();
    @Query(value = "SELECT campaign.name, campaign.id, count(campaign_link.campaign_id) FROM campaign JOIN campaign_link ON campaign.id=campaign_link.campaign_id GROUP BY campaign.id" , nativeQuery = true)

    Optional<List<Object>>findCountByOccurrence1();
}
