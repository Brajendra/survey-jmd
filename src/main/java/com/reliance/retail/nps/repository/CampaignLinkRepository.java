package com.reliance.retail.nps.repository;

import com.reliance.retail.nps.domain.CampaignLink;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CampaignLink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignLinkRepository extends JpaRepository<CampaignLink, Long> {}
