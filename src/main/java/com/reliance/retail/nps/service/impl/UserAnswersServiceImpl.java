package com.reliance.retail.nps.service.impl;

import com.reliance.retail.nps.domain.UserAnswers;
import com.reliance.retail.nps.repository.UserAnswersRepository;
import com.reliance.retail.nps.service.UserAnswersService;
import com.reliance.retail.nps.service.dto.UserAnswersDTO;
import com.reliance.retail.nps.service.mapper.UserAnswersMapper;
import java.util.Optional;
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

    private final UserAnswersMapper userAnswersMapper;

    public UserAnswersServiceImpl(UserAnswersRepository userAnswersRepository, UserAnswersMapper userAnswersMapper) {
        this.userAnswersRepository = userAnswersRepository;
        this.userAnswersMapper = userAnswersMapper;
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
}
