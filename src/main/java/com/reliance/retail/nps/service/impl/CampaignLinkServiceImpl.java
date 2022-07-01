package com.reliance.retail.nps.service.impl;

import com.reliance.retail.nps.domain.CampaignLink;
import com.reliance.retail.nps.repository.CampaignLinkRepository;
import com.reliance.retail.nps.service.CampaignLinkService;
import com.reliance.retail.nps.service.dto.CampaignLinkDTO;
import com.reliance.retail.nps.service.mapper.CampaignLinkMapper;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CampaignLink}.
 */
@Service
@Transactional
public class CampaignLinkServiceImpl implements CampaignLinkService {

    private final Logger log = LoggerFactory.getLogger(CampaignLinkServiceImpl.class);

    private final CampaignLinkRepository campaignLinkRepository;

    private final CampaignLinkMapper campaignLinkMapper;

    public CampaignLinkServiceImpl(CampaignLinkRepository campaignLinkRepository, CampaignLinkMapper campaignLinkMapper) {
        this.campaignLinkRepository = campaignLinkRepository;
        this.campaignLinkMapper = campaignLinkMapper;
    }

    @Override
    public CampaignLinkDTO save(CampaignLinkDTO campaignLinkDTO) {
        log.debug("Request to save CampaignLink : {}", campaignLinkDTO);
        CampaignLink campaignLink = campaignLinkMapper.toEntity(campaignLinkDTO);
        campaignLink.setCode(getUniqueCode());
        campaignLink = campaignLinkRepository.save(campaignLink);
        return campaignLinkMapper.toDto(campaignLink);
    }

    @Override
    public CampaignLinkDTO update(CampaignLinkDTO campaignLinkDTO) {
        log.debug("Request to save CampaignLink : {}", campaignLinkDTO);
        CampaignLink campaignLink = campaignLinkMapper.toEntity(campaignLinkDTO);
        campaignLink = campaignLinkRepository.save(campaignLink);
        return campaignLinkMapper.toDto(campaignLink);
    }

    @Override
    public Optional<CampaignLinkDTO> partialUpdate(CampaignLinkDTO campaignLinkDTO) {
        log.debug("Request to partially update CampaignLink : {}", campaignLinkDTO);

        return campaignLinkRepository
            .findById(campaignLinkDTO.getId())
            .map(existingCampaignLink -> {
                campaignLinkMapper.partialUpdate(existingCampaignLink, campaignLinkDTO);

                return existingCampaignLink;
            })
            .map(campaignLinkRepository::save)
            .map(campaignLinkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignLinkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignLinks");
        return campaignLinkRepository.findAll(pageable).map(campaignLinkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignLinkDTO> findOne(Long id) {
        log.debug("Request to get CampaignLink : {}", id);
        return campaignLinkRepository.findById(id).map(campaignLinkMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignLink : {}", id);
        campaignLinkRepository.deleteById(id);
    }

    private String getUniqueCode() {
        String code = getRandomString().toUpperCase();
        return campaignLinkRepository.existsByCode(code)
            .map(exist -> {
                if(exist){
                    return getUniqueCode();
                } else {
                    return code;
                }
            }).get();
    }



    private String getRandomString() {
        return UUID.randomUUID().toString().subSequence(0, 8).toString();
    }
}
