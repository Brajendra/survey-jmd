package com.reliance.retail.nps.web.rest;

import com.reliance.retail.nps.repository.UserAnswersRepository;
import com.reliance.retail.nps.service.UserAnswersService;
import com.reliance.retail.nps.service.dto.UserAnswersDTO;
import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.reliance.retail.nps.domain.UserAnswers}.
 */
@RestController
@RequestMapping("/api")
public class UserAnswersResource {

    private final Logger log = LoggerFactory.getLogger(UserAnswersResource.class);

    private static final String ENTITY_NAME = "userAnswers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAnswersService userAnswersService;

    private final UserAnswersRepository userAnswersRepository;

    public UserAnswersResource(UserAnswersService userAnswersService, UserAnswersRepository userAnswersRepository) {
        this.userAnswersService = userAnswersService;
        this.userAnswersRepository = userAnswersRepository;
    }

    /**
     * {@code POST  /user-answers} : Create a new userAnswers.
     *
     * @param userAnswersDTO the userAnswersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAnswersDTO, or with status {@code 400 (Bad Request)} if the userAnswers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-answers")
    public ResponseEntity<UserAnswersDTO> createUserAnswers(@RequestBody UserAnswersDTO userAnswersDTO) throws URISyntaxException {
        log.debug("REST request to save UserAnswers : {}", userAnswersDTO);
        if (userAnswersDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAnswers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAnswersDTO result = userAnswersService.save(userAnswersDTO);
        return ResponseEntity
            .created(new URI("/api/user-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-answers/:id} : Updates an existing userAnswers.
     *
     * @param id the id of the userAnswersDTO to save.
     * @param userAnswersDTO the userAnswersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAnswersDTO,
     * or with status {@code 400 (Bad Request)} if the userAnswersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAnswersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-answers/{id}")
    public ResponseEntity<UserAnswersDTO> updateUserAnswers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserAnswersDTO userAnswersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserAnswers : {}, {}", id, userAnswersDTO);
        if (userAnswersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAnswersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAnswersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAnswersDTO result = userAnswersService.update(userAnswersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAnswersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-answers/:id} : Partial updates given fields of an existing userAnswers, field will ignore if it is null
     *
     * @param id the id of the userAnswersDTO to save.
     * @param userAnswersDTO the userAnswersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAnswersDTO,
     * or with status {@code 400 (Bad Request)} if the userAnswersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAnswersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAnswersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-answers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAnswersDTO> partialUpdateUserAnswers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserAnswersDTO userAnswersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAnswers partially : {}, {}", id, userAnswersDTO);
        if (userAnswersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAnswersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAnswersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAnswersDTO> result = userAnswersService.partialUpdate(userAnswersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAnswersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-answers} : get all the userAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAnswers in body.
     */
    @GetMapping("/user-answers")
    public ResponseEntity<List<UserAnswersDTO>> getAllUserAnswers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserAnswers");
        Page<UserAnswersDTO> page = userAnswersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-answers/:id} : get the "id" userAnswers.
     *
     * @param id the id of the userAnswersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAnswersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-answers/{id}")
    public ResponseEntity<UserAnswersDTO> getUserAnswers(@PathVariable Long id) {
        log.debug("REST request to get UserAnswers : {}", id);
        Optional<UserAnswersDTO> userAnswersDTO = userAnswersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAnswersDTO);
    }

    /**
     * {@code DELETE  /user-answers/:id} : delete the "id" userAnswers.
     *
     * @param id the id of the userAnswersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-answers/{id}")
    public ResponseEntity<Void> deleteUserAnswers(@PathVariable Long id) {
        log.debug("REST request to delete UserAnswers : {}", id);
        userAnswersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
