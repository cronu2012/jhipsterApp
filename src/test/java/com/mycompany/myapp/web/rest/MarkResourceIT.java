package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.MarkAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.repository.MarkRepository;
import com.mycompany.myapp.service.dto.MarkDTO;
import com.mycompany.myapp.service.mapper.MarkMapper;
import jakarta.persistence.EntityManager;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MarkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarkResourceIT {

    private static final String DEFAULT_MARK_NO = "AAAAAAAAAA";
    private static final String UPDATED_MARK_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CH_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/marks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private MarkMapper markMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarkMockMvc;

    private Mark mark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mark createEntity(EntityManager em) {
        Mark mark = new Mark()
            .markNo(DEFAULT_MARK_NO)
            .enName(DEFAULT_EN_NAME)
            .chName(DEFAULT_CH_NAME)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return mark;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mark createUpdatedEntity(EntityManager em) {
        Mark mark = new Mark()
            .markNo(UPDATED_MARK_NO)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return mark;
    }

    @BeforeEach
    public void initTest() {
        mark = createEntity(em);
    }

    @Test
    @Transactional
    void createMark() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);
        var returnedMarkDTO = om.readValue(
            restMarkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(markDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MarkDTO.class
        );

        // Validate the Mark in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMark = markMapper.toEntity(returnedMarkDTO);
        assertMarkUpdatableFieldsEquals(returnedMark, getPersistedMark(returnedMark));
    }

    @Test
    @Transactional
    void createMarkWithExistingId() throws Exception {
        // Create the Mark with an existing ID
        mark.setId(1L);
        MarkDTO markDTO = markMapper.toDto(mark);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(markDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMarks() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList
        restMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mark.getId().intValue())))
            .andExpect(jsonPath("$.[*].markNo").value(hasItem(DEFAULT_MARK_NO)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)))
            .andExpect(jsonPath("$.[*].chName").value(hasItem(DEFAULT_CH_NAME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    void getMark() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get the mark
        restMarkMockMvc
            .perform(get(ENTITY_API_URL_ID, mark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mark.getId().intValue()))
            .andExpect(jsonPath("$.markNo").value(DEFAULT_MARK_NO))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME))
            .andExpect(jsonPath("$.chName").value(DEFAULT_CH_NAME))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64.getEncoder().encodeToString(DEFAULT_IMG)));
    }

    @Test
    @Transactional
    void getMarksByIdFiltering() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        Long id = mark.getId();

        defaultMarkFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMarkFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMarkFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMarksByMarkNoIsEqualToSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where markNo equals to
        defaultMarkFiltering("markNo.equals=" + DEFAULT_MARK_NO, "markNo.equals=" + UPDATED_MARK_NO);
    }

    @Test
    @Transactional
    void getAllMarksByMarkNoIsInShouldWork() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where markNo in
        defaultMarkFiltering("markNo.in=" + DEFAULT_MARK_NO + "," + UPDATED_MARK_NO, "markNo.in=" + UPDATED_MARK_NO);
    }

    @Test
    @Transactional
    void getAllMarksByMarkNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where markNo is not null
        defaultMarkFiltering("markNo.specified=true", "markNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMarksByMarkNoContainsSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where markNo contains
        defaultMarkFiltering("markNo.contains=" + DEFAULT_MARK_NO, "markNo.contains=" + UPDATED_MARK_NO);
    }

    @Test
    @Transactional
    void getAllMarksByMarkNoNotContainsSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where markNo does not contain
        defaultMarkFiltering("markNo.doesNotContain=" + UPDATED_MARK_NO, "markNo.doesNotContain=" + DEFAULT_MARK_NO);
    }

    @Test
    @Transactional
    void getAllMarksByEnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where enName equals to
        defaultMarkFiltering("enName.equals=" + DEFAULT_EN_NAME, "enName.equals=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByEnNameIsInShouldWork() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where enName in
        defaultMarkFiltering("enName.in=" + DEFAULT_EN_NAME + "," + UPDATED_EN_NAME, "enName.in=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByEnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where enName is not null
        defaultMarkFiltering("enName.specified=true", "enName.specified=false");
    }

    @Test
    @Transactional
    void getAllMarksByEnNameContainsSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where enName contains
        defaultMarkFiltering("enName.contains=" + DEFAULT_EN_NAME, "enName.contains=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByEnNameNotContainsSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where enName does not contain
        defaultMarkFiltering("enName.doesNotContain=" + UPDATED_EN_NAME, "enName.doesNotContain=" + DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where chName equals to
        defaultMarkFiltering("chName.equals=" + DEFAULT_CH_NAME, "chName.equals=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByChNameIsInShouldWork() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where chName in
        defaultMarkFiltering("chName.in=" + DEFAULT_CH_NAME + "," + UPDATED_CH_NAME, "chName.in=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where chName is not null
        defaultMarkFiltering("chName.specified=true", "chName.specified=false");
    }

    @Test
    @Transactional
    void getAllMarksByChNameContainsSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where chName contains
        defaultMarkFiltering("chName.contains=" + DEFAULT_CH_NAME, "chName.contains=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllMarksByChNameNotContainsSomething() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        // Get all the markList where chName does not contain
        defaultMarkFiltering("chName.doesNotContain=" + UPDATED_CH_NAME, "chName.doesNotContain=" + DEFAULT_CH_NAME);
    }

    private void defaultMarkFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMarkShouldBeFound(shouldBeFound);
        defaultMarkShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMarkShouldBeFound(String filter) throws Exception {
        restMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mark.getId().intValue())))
            .andExpect(jsonPath("$.[*].markNo").value(hasItem(DEFAULT_MARK_NO)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)))
            .andExpect(jsonPath("$.[*].chName").value(hasItem(DEFAULT_CH_NAME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMG))));

        // Check, that the count call also returns 1
        restMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMarkShouldNotBeFound(String filter) throws Exception {
        restMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMark() throws Exception {
        // Get the mark
        restMarkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMark() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mark
        Mark updatedMark = markRepository.findById(mark.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMark are not directly saved in db
        em.detach(updatedMark);
        updatedMark
            .markNo(UPDATED_MARK_NO)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        MarkDTO markDTO = markMapper.toDto(updatedMark);

        restMarkMockMvc
            .perform(put(ENTITY_API_URL_ID, markDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(markDTO)))
            .andExpect(status().isOk());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMarkToMatchAllProperties(updatedMark);
    }

    @Test
    @Transactional
    void putNonExistingMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mark.setId(longCount.incrementAndGet());

        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarkMockMvc
            .perform(put(ENTITY_API_URL_ID, markDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(markDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mark.setId(longCount.incrementAndGet());

        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(markDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mark.setId(longCount.incrementAndGet());

        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(markDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarkWithPatch() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mark using partial update
        Mark partialUpdatedMark = new Mark();
        partialUpdatedMark.setId(mark.getId());

        partialUpdatedMark.enName(UPDATED_EN_NAME).chName(UPDATED_CH_NAME);

        restMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMark))
            )
            .andExpect(status().isOk());

        // Validate the Mark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMarkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMark, mark), getPersistedMark(mark));
    }

    @Test
    @Transactional
    void fullUpdateMarkWithPatch() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mark using partial update
        Mark partialUpdatedMark = new Mark();
        partialUpdatedMark.setId(mark.getId());

        partialUpdatedMark
            .markNo(UPDATED_MARK_NO)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMark))
            )
            .andExpect(status().isOk());

        // Validate the Mark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMarkUpdatableFieldsEquals(partialUpdatedMark, getPersistedMark(partialUpdatedMark));
    }

    @Test
    @Transactional
    void patchNonExistingMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mark.setId(longCount.incrementAndGet());

        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, markDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(markDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mark.setId(longCount.incrementAndGet());

        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(markDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mark.setId(longCount.incrementAndGet());

        // Create the Mark
        MarkDTO markDTO = markMapper.toDto(mark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(markDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMark() throws Exception {
        // Initialize the database
        markRepository.saveAndFlush(mark);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mark
        restMarkMockMvc
            .perform(delete(ENTITY_API_URL_ID, mark.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return markRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Mark getPersistedMark(Mark mark) {
        return markRepository.findById(mark.getId()).orElseThrow();
    }

    protected void assertPersistedMarkToMatchAllProperties(Mark expectedMark) {
        assertMarkAllPropertiesEquals(expectedMark, getPersistedMark(expectedMark));
    }

    protected void assertPersistedMarkToMatchUpdatableProperties(Mark expectedMark) {
        assertMarkAllUpdatablePropertiesEquals(expectedMark, getPersistedMark(expectedMark));
    }
}
