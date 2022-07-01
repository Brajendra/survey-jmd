package com.reliance.retail.nps.repository;

import com.reliance.retail.nps.domain.UserAnswers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserAnswers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAnswersRepository extends JpaRepository<UserAnswers, Long> {}
