package com.reliance.retail.nps.service.mapper;

import com.reliance.retail.nps.domain.Analytics;
import com.reliance.retail.nps.service.dto.AnalyticsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnalyticsMapper extends EntityMapper<AnalyticsDTO, Analytics>{
}
