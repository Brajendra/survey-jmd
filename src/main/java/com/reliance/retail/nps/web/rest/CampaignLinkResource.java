package com.reliance.retail.nps.web.rest;

import com.reliance.retail.nps.repository.CampaignLinkRepository;
import com.reliance.retail.nps.service.CampaignLinkService;
import com.reliance.retail.nps.service.dto.CampaignLinkDTO;
import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reliance.retail.nps.domain.CampaignLink}.
 */
@RestController
@RequestMapping("/api")
public class CampaignLinkResource {

    private final Logger log = LoggerFactory.getLogger(CampaignLinkResource.class);

    private static final String ENTITY_NAME = "campaignLink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignLinkService campaignLinkService;

    private final CampaignLinkRepository campaignLinkRepository;

    public CampaignLinkResource(CampaignLinkService campaignLinkService, CampaignLinkRepository campaignLinkRepository) {
        this.campaignLinkService = campaignLinkService;
        this.campaignLinkRepository = campaignLinkRepository;
    }

    /**
     * {@code POST  /campaign-links} : Create a new campaignLink.
     *
     * @param campaignLinkDTO the campaignLinkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignLinkDTO, or with status {@code 400 (Bad Request)} if the campaignLink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-links")
    public ResponseEntity<CampaignLinkDTO> createCampaignLink(@Valid @RequestBody CampaignLinkDTO campaignLinkDTO)
        throws URISyntaxException {
        log.debug("REST request to save CampaignLink : {}", campaignLinkDTO);
        if (campaignLinkDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignLink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignLinkDTO result = campaignLinkService.save(campaignLinkDTO);
        return ResponseEntity
            .created(new URI("/api/campaign-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-links/:id} : Updates an existing campaignLink.
     *
     * @param id the id of the campaignLinkDTO to save.
     * @param campaignLinkDTO the campaignLinkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignLinkDTO,
     * or with status {@code 400 (Bad Request)} if the campaignLinkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignLinkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-links/{id}")
    public ResponseEntity<CampaignLinkDTO> updateCampaignLink(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CampaignLinkDTO campaignLinkDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CampaignLink : {}, {}", id, campaignLinkDTO);
        if (campaignLinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campaignLinkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campaignLinkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CampaignLinkDTO result = campaignLinkService.update(campaignLinkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignLinkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /campaign-links/:id} : Partial updates given fields of an existing campaignLink, field will ignore if it is null
     *
     * @param id the id of the campaignLinkDTO to save.
     * @param campaignLinkDTO the campaignLinkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignLinkDTO,
     * or with status {@code 400 (Bad Request)} if the campaignLinkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the campaignLinkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the campaignLinkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/campaign-links/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CampaignLinkDTO> partialUpdateCampaignLink(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CampaignLinkDTO campaignLinkDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CampaignLink partially : {}, {}", id, campaignLinkDTO);
        if (campaignLinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campaignLinkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campaignLinkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CampaignLinkDTO> result = campaignLinkService.partialUpdate(campaignLinkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignLinkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /campaign-links} : get all the campaignLinks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignLinks in body.
     */
    @GetMapping("/campaign-links")
    public ResponseEntity<List<CampaignLinkDTO>> getAllCampaignLinks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CampaignLinks");
        Page<CampaignLinkDTO> page = campaignLinkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaign-links/:id} : get the "id" campaignLink.
     *
     * @param id the id of the campaignLinkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignLinkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-links/{id}")
    public ResponseEntity<CampaignLinkDTO> getCampaignLink(@PathVariable Long id) {
        log.debug("REST request to get CampaignLink : {}", id);
        Optional<CampaignLinkDTO> campaignLinkDTO = campaignLinkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignLinkDTO);
    }

    /**
     * {@code DELETE  /campaign-links/:id} : delete the "id" campaignLink.
     *
     * @param id the id of the campaignLinkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-links/{id}")
    public ResponseEntity<Void> deleteCampaignLink(@PathVariable Long id) {
        log.debug("REST request to delete CampaignLink : {}", id);
        campaignLinkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
