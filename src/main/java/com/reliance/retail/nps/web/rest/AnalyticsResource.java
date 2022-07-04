package com.reliance.retail.nps.web.rest;

import com.reliance.retail.nps.repository.AnalyticsRepository;
import com.reliance.retail.nps.repository.CampaignLinkRepository;
import com.reliance.retail.nps.service.AnalyticsService;
import com.reliance.retail.nps.service.dto.AnalyticsChatDataDTO;
import com.reliance.retail.nps.service.dto.AnalyticsDTO;
import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api")
public class AnalyticsResource {

    private final Logger log = LoggerFactory.getLogger(AnalyticsResource.class);

    private static final String ENTITY_NAME = "analytics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyticsService analyticsService;

    private final AnalyticsRepository analyticsRepository;

    private final CampaignLinkRepository campaignLinkRepository;

    public AnalyticsResource(AnalyticsService analyticsService, AnalyticsRepository analyticsRepository, CampaignLinkRepository campaignLinkRepository){
        this.analyticsRepository = analyticsRepository;
        this.analyticsService = analyticsService;
        this.campaignLinkRepository = campaignLinkRepository;
    }

//    public AnalyticsResource(AnalyticsService analyticsService, AnalyticsRepository analyticsRepository){
//        this.analyticsRepository = analyticsRepository;
//        this.analyticsService = analyticsService;
//    }

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
    @GetMapping("/analytics")
    public ResponseEntity<List<AnalyticsDTO>> getAllAnalytics(@org.springdoc.api.annotations.ParameterObject Pageable pageable){
        log.debug("REST request to get a page of Analytics");
        Page<AnalyticsDTO> page = analyticsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/analyticsData")
    public ResponseEntity<List<AnalyticsChatDataDTO>> getAnalyticsData(){
        log.debug("REST request to get a page of Analytics Data");
        List<AnalyticsDTO>  analyticsDTOS  = analyticsService.findAll();
        Map<Long, AnalyticsChatDataDTO> map = new HashMap<>();
        for(AnalyticsDTO dto : analyticsDTOS){
            AnalyticsChatDataDTO analyticsChatDataDTO = map.getOrDefault(dto.getCampaignId(), new AnalyticsChatDataDTO());
            if(analyticsChatDataDTO.getAttempted()==null)
                analyticsChatDataDTO.setAttempted(0L);
            if(analyticsChatDataDTO.getSubmitted()==null)
               analyticsChatDataDTO.setSubmitted(0L);

            if(nonNull(dto.getEvent())){
                if(dto.getEvent().trim().equalsIgnoreCase("CAMPAIGN_ATTEMPTED")){
                    Long attempted = analyticsChatDataDTO.getAttempted();
                    attempted++;
                    analyticsChatDataDTO.setAttempted(attempted);
                }else{
                    Long submitted = analyticsChatDataDTO.getSubmitted();
                    submitted++;
                    analyticsChatDataDTO.setSubmitted(submitted);
                }
            }
            analyticsChatDataDTO.setCampaignId(dto.getCampaignId());
            map.put(dto.getCampaignId(),analyticsChatDataDTO);
        }

//        Optional<List<Map<Long, Long>>> occurrenceMapOptional = campaignLinkRepository.findCountByOccurrence();
//        List<Map<Long, Long>> occurrenceMap = occurrenceMapOptional.get();

        Optional<List<Object>> find = campaignLinkRepository.findCountByOccurrence1();
        List<Object> objects = find.get();
        for(Object obj : objects){
            Object[] o = (Object[]) obj;
            String name =  (String)o[0];
            Long id = ((BigInteger)o[1]).longValue();
            Long sum = ((BigDecimal)o[2]).longValue();
            AnalyticsChatDataDTO analyticsChatDataDTO = map.getOrDefault(id, new AnalyticsChatDataDTO());
            if(sum==null){
                sum = 0L;
            }
            analyticsChatDataDTO.setPublished(sum);
            analyticsChatDataDTO.setTitle(name);
            map.put(id,analyticsChatDataDTO);
        }


        return ResponseEntity.ok().body(map.values().stream().collect(Collectors.toList()));
    }


}
