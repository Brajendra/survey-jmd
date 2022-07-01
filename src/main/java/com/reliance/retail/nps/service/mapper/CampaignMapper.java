package com.reliance.retail.nps.service.mapper;

import com.reliance.retail.nps.domain.Campaign;
import com.reliance.retail.nps.service.dto.CampaignDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {}
