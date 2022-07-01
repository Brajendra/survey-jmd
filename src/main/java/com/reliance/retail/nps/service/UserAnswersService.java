package com.reliance.retail.nps.service;

import com.reliance.retail.nps.service.dto.UserAnswersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.retail.nps.domain.UserAnswers}.
 */
public interface UserAnswersService {
    /**
     * Save a userAnswers.
     *
     * @param userAnswersDTO the entity to save.
     * @return the persisted entity.
     */
    UserAnswersDTO save(UserAnswersDTO userAnswersDTO);

    /**
     * Updates a userAnswers.
     *
     * @param userAnswersDTO the entity to update.
     * @return the persisted entity.
     */
    UserAnswersDTO update(UserAnswersDTO userAnswersDTO);

    /**
     * Partially updates a userAnswers.
     *
     * @param userAnswersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserAnswersDTO> partialUpdate(UserAnswersDTO userAnswersDTO);

    /**
     * Get all the userAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserAnswersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userAnswers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserAnswersDTO> findOne(Long id);

    /**
     * Delete the "id" userAnswers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
