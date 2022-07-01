package com.reliance.retail.nps.service.impl;

import com.reliance.retail.nps.domain.CampaignLink;
import com.reliance.retail.nps.domain.UserAnswers;
import com.reliance.retail.nps.repository.CampaignLinkRepository;
import com.reliance.retail.nps.repository.UserAnswersRepository;
import com.reliance.retail.nps.service.UserAnswersService;
import com.reliance.retail.nps.service.dto.UserAnswersDTO;
import com.reliance.retail.nps.service.dto.UserCampaignResponseDetailsDTO;
import com.reliance.retail.nps.service.mapper.UserAnswersMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserAnswers}.
 */
@Service
@Transactional
public class UserAnswersServiceImpl implements UserAnswersService {

    private final Logger log = LoggerFactory.getLogger(UserAnswersServiceImpl.class);

    private final UserAnswersRepository userAnswersRepository;

    private static final String ENTITY_NAME = "userResponse";

    private final UserAnswersMapper userAnswersMapper;

    private final CampaignLinkRepository campaignLinkRepository;

    public UserAnswersServiceImpl(UserAnswersRepository userAnswersRepository, UserAnswersMapper userAnswersMapper, CampaignLinkRepository campaignLinkRepository) {
        this.userAnswersRepository = userAnswersRepository;
        this.userAnswersMapper = userAnswersMapper;
        this.campaignLinkRepository = campaignLinkRepository;
    }

    @Override
    public UserAnswersDTO save(UserAnswersDTO userAnswersDTO) {
        log.debug("Request to save UserAnswers : {}", userAnswersDTO);
        UserAnswers userAnswers = userAnswersMapper.toEntity(userAnswersDTO);
        userAnswers = userAnswersRepository.save(userAnswers);
        return userAnswersMapper.toDto(userAnswers);
    }

    @Override
    public UserAnswersDTO update(UserAnswersDTO userAnswersDTO) {
        log.debug("Request to save UserAnswers : {}", userAnswersDTO);
        UserAnswers userAnswers = userAnswersMapper.toEntity(userAnswersDTO);
        userAnswers = userAnswersRepository.save(userAnswers);
        return userAnswersMapper.toDto(userAnswers);
    }

    @Override
    public Optional<UserAnswersDTO> partialUpdate(UserAnswersDTO userAnswersDTO) {
        log.debug("Request to partially update UserAnswers : {}", userAnswersDTO);

        return userAnswersRepository
            .findById(userAnswersDTO.getId())
            .map(existingUserAnswers -> {
                userAnswersMapper.partialUpdate(existingUserAnswers, userAnswersDTO);

                return existingUserAnswers;
            })
            .map(userAnswersRepository::save)
            .map(userAnswersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAnswers");
        return userAnswersRepository.findAll(pageable).map(userAnswersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAnswersDTO> findOne(Long id) {
        log.debug("Request to get UserAnswers : {}", id);
        return userAnswersRepository.findById(id).map(userAnswersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAnswers : {}", id);
        userAnswersRepository.deleteById(id);
    }


    @Override
    @Transactional
    public boolean saveResponse(UserCampaignResponseDetailsDTO responseDetails) {

        validateRequestData(responseDetails);

        responseDetails.getCampaignLink().setAttemptQuestionCount(responseDetails.getAttemptQuestionCount());

        final CampaignLink savedCampaignLink = campaignLinkRepository.save(responseDetails.getCampaignLink());
        if (responseDetails.getUserAnswers() != null && !responseDetails.getUserAnswers().isEmpty()) {
            List<UserAnswers> userAnswers = responseDetails.getUserAnswers().stream().map(userAnswersDTO -> {
                userAnswersDTO.setCampaignLinkId(savedCampaignLink.getId());
                return userAnswersMapper.toEntity(userAnswersDTO);
            }).collect(Collectors.toList());
            userAnswersRepository.saveAll(userAnswers);
        }
        return true;
    }


    private void validateRequestData(UserCampaignResponseDetailsDTO responseDetails) {

        if(StringUtils.isEmpty(responseDetails.getCode() )) {
            throw new BadRequestAlertException("Campaign Code Required", ENTITY_NAME, "CampaignNUll");
        }
        CampaignLink campaignLink =  campaignLinkRepository.findByCode(responseDetails.getCode()).get();
        if(campaignLink == null) {
            throw new BadRequestAlertException("Campaign Code is not valid", ENTITY_NAME, "CampaignNotyValid");
        }
        responseDetails.setCampaignLink(campaignLink);

        boolean exist =  userAnswersRepository.existsByCampaignLinkId(campaignLink.getId()).get();
        if(exist) {
            throw new BadRequestAlertException("Response already saved", ENTITY_NAME, "ResponseSaved");
        }
    }
}
