package com.reliance.retail.nps.service.impl;

import com.reliance.retail.nps.domain.Campaign;
import com.reliance.retail.nps.repository.CampaignLinkRepository;
import com.reliance.retail.nps.repository.CampaignRepository;
import com.reliance.retail.nps.service.CampaignService;
import com.reliance.retail.nps.service.QuestionService;
import com.reliance.retail.nps.service.dto.CampaignDTO;
import com.reliance.retail.nps.service.dto.CampaignDetailDTO;
import com.reliance.retail.nps.service.mapper.CampaignMapper;
import java.util.Optional;

import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Campaign}.
 */
@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private final CampaignRepository campaignRepository;

    private final CampaignMapper campaignMapper;
    private final QuestionService questionService;
    private final CampaignLinkRepository campaignLinkRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMapper campaignMapper, QuestionService questionService, CampaignLinkRepository campaignLinkRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
        this.questionService  = questionService;
        this.campaignLinkRepository = campaignLinkRepository;
    }

    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        campaign = campaignRepository.save(campaign);
        return campaignMapper.toDto(campaign);
    }

    @Override
    public CampaignDTO update(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        campaign = campaignRepository.save(campaign);
        return campaignMapper.toDto(campaign);
    }

    @Override
    public Optional<CampaignDTO> partialUpdate(CampaignDTO campaignDTO) {
        log.debug("Request to partially update Campaign : {}", campaignDTO);

        return campaignRepository
            .findById(campaignDTO.getId())
            .map(existingCampaign -> {
                campaignMapper.partialUpdate(existingCampaign, campaignDTO);

                return existingCampaign;
            })
            .map(campaignRepository::save)
            .map(campaignMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll(pageable).map(campaignMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignDTO> findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return campaignRepository.findById(id).map(campaignMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.deleteById(id);
    }

    @Override
    public Optional<CampaignDetailDTO> findOneByCode(String code) {
        log.debug("Request to get findOneByCode: {}", code);

        return campaignLinkRepository.findByCode(code)
            .flatMap(campaignLink -> {
                if(campaignLink != null) {
                    return   campaignRepository
                        .findById(campaignLink.getCampaign().getId())
                        .flatMap(campaign -> questionService.findQuestionByCampaignId(campaign.getId())
                            .map(questions -> {
                                CampaignDetailDTO campaignDetails = new CampaignDetailDTO();
                                campaignDetails.setCampaign(campaignMapper.toDto(campaign));
                                campaignDetails.setQuestions(questions);
                                return campaignDetails;
                            }));
                } else {
                    throw new BadRequestAlertException("Code is Invalid", "CodeInvalid", "COdeInvalid");
                }
            });

        //   return Optional.empty();
    }
}
