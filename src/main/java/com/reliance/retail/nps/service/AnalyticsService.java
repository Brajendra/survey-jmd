package com.reliance.retail.nps.service;

import com.reliance.retail.nps.service.dto.AnalyticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnalyticsService {
    AnalyticsDTO save(AnalyticsDTO campaignDTO);

    Page<AnalyticsDTO> findAll(Pageable pageable);

    List<AnalyticsDTO> findAll();

}
