package com.reliance.retail.nps.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.retail.nps.IntegrationTest;
import com.reliance.retail.nps.domain.UserAnswers;
import com.reliance.retail.nps.repository.UserAnswersRepository;
import com.reliance.retail.nps.service.dto.UserAnswersDTO;
import com.reliance.retail.nps.service.mapper.UserAnswersMapper;
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
 * Integration tests for the {@link UserAnswersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAnswersResourceIT {

    private static final Long DEFAULT_CAMPAIGN_LINK_ID = 1L;
    private static final Long UPDATED_CAMPAIGN_LINK_ID = 2L;

    private static final String DEFAULT_ANSWERS = "AAAAAAAAAA";
    private static final String UPDATED_ANSWERS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/user-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAnswersRepository userAnswersRepository;

    @Autowired
    private UserAnswersMapper userAnswersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAnswersMockMvc;

    private UserAnswers userAnswers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAnswers createEntity(EntityManager em) {
        UserAnswers userAnswers = new UserAnswers()
            .campaignLinkId(DEFAULT_CAMPAIGN_LINK_ID)
            .answers(DEFAULT_ANSWERS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return userAnswers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAnswers createUpdatedEntity(EntityManager em) {
        UserAnswers userAnswers = new UserAnswers()
            .campaignLinkId(UPDATED_CAMPAIGN_LINK_ID)
            .answers(UPDATED_ANSWERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return userAnswers;
    }

    @BeforeEach
    public void initTest() {
        userAnswers = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAnswers() throws Exception {
        int databaseSizeBeforeCreate = userAnswersRepository.findAll().size();
        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);
        restUserAnswersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeCreate + 1);
        UserAnswers testUserAnswers = userAnswersList.get(userAnswersList.size() - 1);
        assertThat(testUserAnswers.getCampaignLinkId()).isEqualTo(DEFAULT_CAMPAIGN_LINK_ID);
        assertThat(testUserAnswers.getAnswers()).isEqualTo(DEFAULT_ANSWERS);
        assertThat(testUserAnswers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAnswers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createUserAnswersWithExistingId() throws Exception {
        // Create the UserAnswers with an existing ID
        userAnswers.setId(1L);
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        int databaseSizeBeforeCreate = userAnswersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAnswersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserAnswers() throws Exception {
        // Initialize the database
        userAnswersRepository.saveAndFlush(userAnswers);

        // Get all the userAnswersList
        restUserAnswersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAnswers.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignLinkId").value(hasItem(DEFAULT_CAMPAIGN_LINK_ID.intValue())))
            .andExpect(jsonPath("$.[*].answers").value(hasItem(DEFAULT_ANSWERS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getUserAnswers() throws Exception {
        // Initialize the database
        userAnswersRepository.saveAndFlush(userAnswers);

        // Get the userAnswers
        restUserAnswersMockMvc
            .perform(get(ENTITY_API_URL_ID, userAnswers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAnswers.getId().intValue()))
            .andExpect(jsonPath("$.campaignLinkId").value(DEFAULT_CAMPAIGN_LINK_ID.intValue()))
            .andExpect(jsonPath("$.answers").value(DEFAULT_ANSWERS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserAnswers() throws Exception {
        // Get the userAnswers
        restUserAnswersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserAnswers() throws Exception {
        // Initialize the database
        userAnswersRepository.saveAndFlush(userAnswers);

        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();

        // Update the userAnswers
        UserAnswers updatedUserAnswers = userAnswersRepository.findById(userAnswers.getId()).get();
        // Disconnect from session so that the updates on updatedUserAnswers are not directly saved in db
        em.detach(updatedUserAnswers);
        updatedUserAnswers
            .campaignLinkId(UPDATED_CAMPAIGN_LINK_ID)
            .answers(UPDATED_ANSWERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(updatedUserAnswers);

        restUserAnswersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAnswersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
        UserAnswers testUserAnswers = userAnswersList.get(userAnswersList.size() - 1);
        assertThat(testUserAnswers.getCampaignLinkId()).isEqualTo(UPDATED_CAMPAIGN_LINK_ID);
        assertThat(testUserAnswers.getAnswers()).isEqualTo(UPDATED_ANSWERS);
        assertThat(testUserAnswers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAnswers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingUserAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();
        userAnswers.setId(count.incrementAndGet());

        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAnswersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAnswersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();
        userAnswers.setId(count.incrementAndGet());

        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAnswersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();
        userAnswers.setId(count.incrementAndGet());

        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAnswersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAnswersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAnswersWithPatch() throws Exception {
        // Initialize the database
        userAnswersRepository.saveAndFlush(userAnswers);

        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();

        // Update the userAnswers using partial update
        UserAnswers partialUpdatedUserAnswers = new UserAnswers();
        partialUpdatedUserAnswers.setId(userAnswers.getId());

        partialUpdatedUserAnswers.campaignLinkId(UPDATED_CAMPAIGN_LINK_ID).updatedAt(UPDATED_UPDATED_AT);

        restUserAnswersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAnswers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAnswers))
            )
            .andExpect(status().isOk());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
        UserAnswers testUserAnswers = userAnswersList.get(userAnswersList.size() - 1);
        assertThat(testUserAnswers.getCampaignLinkId()).isEqualTo(UPDATED_CAMPAIGN_LINK_ID);
        assertThat(testUserAnswers.getAnswers()).isEqualTo(DEFAULT_ANSWERS);
        assertThat(testUserAnswers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAnswers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateUserAnswersWithPatch() throws Exception {
        // Initialize the database
        userAnswersRepository.saveAndFlush(userAnswers);

        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();

        // Update the userAnswers using partial update
        UserAnswers partialUpdatedUserAnswers = new UserAnswers();
        partialUpdatedUserAnswers.setId(userAnswers.getId());

        partialUpdatedUserAnswers
            .campaignLinkId(UPDATED_CAMPAIGN_LINK_ID)
            .answers(UPDATED_ANSWERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restUserAnswersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAnswers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAnswers))
            )
            .andExpect(status().isOk());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
        UserAnswers testUserAnswers = userAnswersList.get(userAnswersList.size() - 1);
        assertThat(testUserAnswers.getCampaignLinkId()).isEqualTo(UPDATED_CAMPAIGN_LINK_ID);
        assertThat(testUserAnswers.getAnswers()).isEqualTo(UPDATED_ANSWERS);
        assertThat(testUserAnswers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAnswers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingUserAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();
        userAnswers.setId(count.incrementAndGet());

        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAnswersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAnswersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();
        userAnswers.setId(count.incrementAndGet());

        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAnswersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAnswers() throws Exception {
        int databaseSizeBeforeUpdate = userAnswersRepository.findAll().size();
        userAnswers.setId(count.incrementAndGet());

        // Create the UserAnswers
        UserAnswersDTO userAnswersDTO = userAnswersMapper.toDto(userAnswers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAnswersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userAnswersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAnswers in the database
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAnswers() throws Exception {
        // Initialize the database
        userAnswersRepository.saveAndFlush(userAnswers);

        int databaseSizeBeforeDelete = userAnswersRepository.findAll().size();

        // Delete the userAnswers
        restUserAnswersMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAnswers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        assertThat(userAnswersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
