package com.reliance.retail.nps.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.retail.nps.IntegrationTest;
import com.reliance.retail.nps.domain.CampaignLink;
import com.reliance.retail.nps.repository.CampaignLinkRepository;
import com.reliance.retail.nps.service.dto.CampaignLinkDTO;
import com.reliance.retail.nps.service.mapper.CampaignLinkMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CampaignLinkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CampaignLinkResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTEMPT_QUESTION_COUNT = 1;
    private static final Integer UPDATED_ATTEMPT_QUESTION_COUNT = 2;

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_INFO = "AAAAAAAAAA";
    private static final String UPDATED_USER_INFO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/campaign-links";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CampaignLinkRepository campaignLinkRepository;

    @Autowired
    private CampaignLinkMapper campaignLinkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampaignLinkMockMvc;

    private CampaignLink campaignLink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignLink createEntity(EntityManager em) {
        CampaignLink campaignLink = new CampaignLink()
            .code(DEFAULT_CODE)
            .attemptQuestionCount(DEFAULT_ATTEMPT_QUESTION_COUNT)
            .eventType(DEFAULT_EVENT_TYPE)
            .eventId(DEFAULT_EVENT_ID)
            .userInfo(DEFAULT_USER_INFO)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return campaignLink;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignLink createUpdatedEntity(EntityManager em) {
        CampaignLink campaignLink = new CampaignLink()
            .code(UPDATED_CODE)
            .attemptQuestionCount(UPDATED_ATTEMPT_QUESTION_COUNT)
            .eventType(UPDATED_EVENT_TYPE)
            .eventId(UPDATED_EVENT_ID)
            .userInfo(UPDATED_USER_INFO)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return campaignLink;
    }

    @BeforeEach
    public void initTest() {
        campaignLink = createEntity(em);
    }

    @Test
    @Transactional
    void createCampaignLink() throws Exception {
        int databaseSizeBeforeCreate = campaignLinkRepository.findAll().size();
        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);
        restCampaignLinkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignLink testCampaignLink = campaignLinkList.get(campaignLinkList.size() - 1);
        assertThat(testCampaignLink.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCampaignLink.getAttemptQuestionCount()).isEqualTo(DEFAULT_ATTEMPT_QUESTION_COUNT);
        assertThat(testCampaignLink.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testCampaignLink.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testCampaignLink.getUserInfo()).isEqualTo(DEFAULT_USER_INFO);
        assertThat(testCampaignLink.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCampaignLink.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCampaignLinkWithExistingId() throws Exception {
        // Create the CampaignLink with an existing ID
        campaignLink.setId(1L);
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        int databaseSizeBeforeCreate = campaignLinkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignLinkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignLinkRepository.findAll().size();
        // set the field null
        campaignLink.setCode(null);

        // Create the CampaignLink, which fails.
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        restCampaignLinkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isBadRequest());

        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCampaignLinks() throws Exception {
        // Initialize the database
        campaignLinkRepository.saveAndFlush(campaignLink);

        // Get all the campaignLinkList
        restCampaignLinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignLink.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].attemptQuestionCount").value(hasItem(DEFAULT_ATTEMPT_QUESTION_COUNT)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID)))
            .andExpect(jsonPath("$.[*].userInfo").value(hasItem(DEFAULT_USER_INFO)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCampaignLink() throws Exception {
        // Initialize the database
        campaignLinkRepository.saveAndFlush(campaignLink);

        // Get the campaignLink
        restCampaignLinkMockMvc
            .perform(get(ENTITY_API_URL_ID, campaignLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campaignLink.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.attemptQuestionCount").value(DEFAULT_ATTEMPT_QUESTION_COUNT))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID))
            .andExpect(jsonPath("$.userInfo").value(DEFAULT_USER_INFO))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCampaignLink() throws Exception {
        // Get the campaignLink
        restCampaignLinkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCampaignLink() throws Exception {
        // Initialize the database
        campaignLinkRepository.saveAndFlush(campaignLink);

        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();

        // Update the campaignLink
        CampaignLink updatedCampaignLink = campaignLinkRepository.findById(campaignLink.getId()).get();
        // Disconnect from session so that the updates on updatedCampaignLink are not directly saved in db
        em.detach(updatedCampaignLink);
        updatedCampaignLink
            .code(UPDATED_CODE)
            .attemptQuestionCount(UPDATED_ATTEMPT_QUESTION_COUNT)
            .eventType(UPDATED_EVENT_TYPE)
            .eventId(UPDATED_EVENT_ID)
            .userInfo(UPDATED_USER_INFO)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(updatedCampaignLink);

        restCampaignLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campaignLinkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isOk());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
        CampaignLink testCampaignLink = campaignLinkList.get(campaignLinkList.size() - 1);
        assertThat(testCampaignLink.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCampaignLink.getAttemptQuestionCount()).isEqualTo(UPDATED_ATTEMPT_QUESTION_COUNT);
        assertThat(testCampaignLink.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testCampaignLink.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testCampaignLink.getUserInfo()).isEqualTo(UPDATED_USER_INFO);
        assertThat(testCampaignLink.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCampaignLink.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCampaignLink() throws Exception {
        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();
        campaignLink.setId(count.incrementAndGet());

        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campaignLinkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCampaignLink() throws Exception {
        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();
        campaignLink.setId(count.incrementAndGet());

        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampaignLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCampaignLink() throws Exception {
        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();
        campaignLink.setId(count.incrementAndGet());

        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampaignLinkMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCampaignLinkWithPatch() throws Exception {
        // Initialize the database
        campaignLinkRepository.saveAndFlush(campaignLink);

        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();

        // Update the campaignLink using partial update
        CampaignLink partialUpdatedCampaignLink = new CampaignLink();
        partialUpdatedCampaignLink.setId(campaignLink.getId());

        partialUpdatedCampaignLink
            .attemptQuestionCount(UPDATED_ATTEMPT_QUESTION_COUNT)
            .eventType(UPDATED_EVENT_TYPE)
            .userInfo(UPDATED_USER_INFO)
            .updatedAt(UPDATED_UPDATED_AT);

        restCampaignLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampaignLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampaignLink))
            )
            .andExpect(status().isOk());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
        CampaignLink testCampaignLink = campaignLinkList.get(campaignLinkList.size() - 1);
        assertThat(testCampaignLink.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCampaignLink.getAttemptQuestionCount()).isEqualTo(UPDATED_ATTEMPT_QUESTION_COUNT);
        assertThat(testCampaignLink.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testCampaignLink.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testCampaignLink.getUserInfo()).isEqualTo(UPDATED_USER_INFO);
        assertThat(testCampaignLink.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCampaignLink.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCampaignLinkWithPatch() throws Exception {
        // Initialize the database
        campaignLinkRepository.saveAndFlush(campaignLink);

        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();

        // Update the campaignLink using partial update
        CampaignLink partialUpdatedCampaignLink = new CampaignLink();
        partialUpdatedCampaignLink.setId(campaignLink.getId());

        partialUpdatedCampaignLink
            .code(UPDATED_CODE)
            .attemptQuestionCount(UPDATED_ATTEMPT_QUESTION_COUNT)
            .eventType(UPDATED_EVENT_TYPE)
            .eventId(UPDATED_EVENT_ID)
            .userInfo(UPDATED_USER_INFO)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCampaignLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampaignLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampaignLink))
            )
            .andExpect(status().isOk());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
        CampaignLink testCampaignLink = campaignLinkList.get(campaignLinkList.size() - 1);
        assertThat(testCampaignLink.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCampaignLink.getAttemptQuestionCount()).isEqualTo(UPDATED_ATTEMPT_QUESTION_COUNT);
        assertThat(testCampaignLink.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testCampaignLink.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testCampaignLink.getUserInfo()).isEqualTo(UPDATED_USER_INFO);
        assertThat(testCampaignLink.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCampaignLink.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCampaignLink() throws Exception {
        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();
        campaignLink.setId(count.incrementAndGet());

        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, campaignLinkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCampaignLink() throws Exception {
        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();
        campaignLink.setId(count.incrementAndGet());

        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampaignLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCampaignLink() throws Exception {
        int databaseSizeBeforeUpdate = campaignLinkRepository.findAll().size();
        campaignLink.setId(count.incrementAndGet());

        // Create the CampaignLink
        CampaignLinkDTO campaignLinkDTO = campaignLinkMapper.toDto(campaignLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampaignLinkMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campaignLinkDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CampaignLink in the database
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCampaignLink() throws Exception {
        // Initialize the database
        campaignLinkRepository.saveAndFlush(campaignLink);

        int databaseSizeBeforeDelete = campaignLinkRepository.findAll().size();

        // Delete the campaignLink
        restCampaignLinkMockMvc
            .perform(delete(ENTITY_API_URL_ID, campaignLink.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CampaignLink> campaignLinkList = campaignLinkRepository.findAll();
        assertThat(campaignLinkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
