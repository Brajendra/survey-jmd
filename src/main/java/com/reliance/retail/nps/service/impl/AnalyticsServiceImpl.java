package com.reliance.retail.nps.service.impl;

import com.reliance.retail.nps.domain.Analytics;
import com.reliance.retail.nps.repository.AnalyticsRepository;
import com.reliance.retail.nps.service.AnalyticsService;
import com.reliance.retail.nps.service.dto.AnalyticsDTO;
import com.reliance.retail.nps.service.mapper.AnalyticsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnalyticsServiceImpl implements AnalyticsService {

    private final Logger log = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

    private final AnalyticsMapper analyticsMapper;

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsServiceImpl(AnalyticsMapper analyticsMapper, AnalyticsRepository analyticsRepository){
        this.analyticsMapper = analyticsMapper;
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public AnalyticsDTO save(AnalyticsDTO analyticsDTO) {
        log.debug("Request to save Analytics : {}", analyticsDTO);
        Analytics analytics = analyticsMapper.toEntity(analyticsDTO);
        analytics = analyticsRepository.save(analytics);
        return analyticsMapper.toDto(analytics);
    }
}
