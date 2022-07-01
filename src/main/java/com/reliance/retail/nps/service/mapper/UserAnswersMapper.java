package com.reliance.retail.nps.service.mapper;

import com.reliance.retail.nps.domain.UserAnswers;
import com.reliance.retail.nps.service.dto.UserAnswersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAnswers} and its DTO {@link UserAnswersDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserAnswersMapper extends EntityMapper<UserAnswersDTO, UserAnswers> {}
