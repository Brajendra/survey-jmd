package com.reliance.retail.nps.web.rest;

import com.reliance.retail.nps.repository.AnalyticsRepository;
import com.reliance.retail.nps.service.AnalyticsService;
import com.reliance.retail.nps.service.dto.AnalyticsDTO;
import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class AnalyticsResource {

    private final Logger log = LoggerFactory.getLogger(AnalyticsResource.class);

    private static final String ENTITY_NAME = "analytics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyticsService analyticsService;

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsResource(AnalyticsService analyticsService, AnalyticsRepository analyticsRepository){
        this.analyticsRepository = analyticsRepository;
        this.analyticsService = analyticsService;
    }

    @PostMapping("/analytics")
    public ResponseEntity<AnalyticsDTO> createAnalytics(@Valid @RequestBody AnalyticsDTO analyticsDTO) throws URISyntaxException {
        log.debug("REST request to save Campaign : {}", analyticsDTO);
        if (analyticsDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalyticsDTO result =  analyticsService.save(analyticsDTO);
        return ResponseEntity
            .created(new URI("/api/analytics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}
