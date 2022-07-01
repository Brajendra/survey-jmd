package com.reliance.retail.nps.service.mapper;

import com.reliance.retail.nps.domain.Campaign;
import com.reliance.retail.nps.domain.CampaignLink;
import com.reliance.retail.nps.service.dto.CampaignDTO;
import com.reliance.retail.nps.service.dto.CampaignLinkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignLink} and its DTO {@link CampaignLinkDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampaignLinkMapper extends EntityMapper<CampaignLinkDTO, CampaignLink> {
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "campaignId")
    CampaignLinkDTO toDto(CampaignLink s);

    @Named("campaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoCampaignId(Campaign campaign);
}
