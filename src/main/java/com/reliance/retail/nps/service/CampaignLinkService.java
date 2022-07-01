package com.reliance.retail.nps.service;

import com.reliance.retail.nps.service.dto.CampaignLinkDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.retail.nps.domain.CampaignLink}.
 */
public interface CampaignLinkService {
    /**
     * Save a campaignLink.
     *
     * @param campaignLinkDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignLinkDTO save(CampaignLinkDTO campaignLinkDTO);

    /**
     * Updates a campaignLink.
     *
     * @param campaignLinkDTO the entity to update.
     * @return the persisted entity.
     */
    CampaignLinkDTO update(CampaignLinkDTO campaignLinkDTO);

    /**
     * Partially updates a campaignLink.
     *
     * @param campaignLinkDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CampaignLinkDTO> partialUpdate(CampaignLinkDTO campaignLinkDTO);

    /**
     * Get all the campaignLinks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampaignLinkDTO> findAll(Pageable pageable);

    /**
     * Get the "id" campaignLink.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignLinkDTO> findOne(Long id);

    /**
     * Delete the "id" campaignLink.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
