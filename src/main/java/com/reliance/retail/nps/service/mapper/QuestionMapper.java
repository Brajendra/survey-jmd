package com.reliance.retail.nps.service.mapper;

import com.reliance.retail.nps.domain.Campaign;
import com.reliance.retail.nps.domain.Question;
import com.reliance.retail.nps.service.dto.CampaignDTO;
import com.reliance.retail.nps.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "campaignId")
    QuestionDTO toDto(Question s);

    @Named("campaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoCampaignId(Campaign campaign);
}
