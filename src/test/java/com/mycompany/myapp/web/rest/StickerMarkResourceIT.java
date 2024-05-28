package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.StickerMarkAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.domain.StickerMark;
import com.mycompany.myapp.repository.StickerMarkRepository;
import com.mycompany.myapp.service.dto.StickerMarkDTO;
import com.mycompany.myapp.service.mapper.StickerMarkMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link StickerMarkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StickerMarkResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sticker-marks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StickerMarkRepository stickerMarkRepository;

    @Autowired
    private StickerMarkMapper stickerMarkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStickerMarkMockMvc;

    private StickerMark stickerMark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StickerMark createEntity(EntityManager em) {
        StickerMark stickerMark = new StickerMark().relType(DEFAULT_REL_TYPE);
        return stickerMark;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StickerMark createUpdatedEntity(EntityManager em) {
        StickerMark stickerMark = new StickerMark().relType(UPDATED_REL_TYPE);
        return stickerMark;
    }

    @BeforeEach
    public void initTest() {
        stickerMark = createEntity(em);
    }

    @Test
    @Transactional
    void createStickerMark() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);
        var returnedStickerMarkDTO = om.readValue(
            restStickerMarkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerMarkDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StickerMarkDTO.class
        );

        // Validate the StickerMark in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStickerMark = stickerMarkMapper.toEntity(returnedStickerMarkDTO);
        assertStickerMarkUpdatableFieldsEquals(returnedStickerMark, getPersistedStickerMark(returnedStickerMark));
    }

    @Test
    @Transactional
    void createStickerMarkWithExistingId() throws Exception {
        // Create the StickerMark with an existing ID
        stickerMark.setId(1L);
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStickerMarkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerMarkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStickerMarks() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get all the stickerMarkList
        restStickerMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stickerMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getStickerMark() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get the stickerMark
        restStickerMarkMockMvc
            .perform(get(ENTITY_API_URL_ID, stickerMark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stickerMark.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getStickerMarksByIdFiltering() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        Long id = stickerMark.getId();

        defaultStickerMarkFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStickerMarkFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStickerMarkFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStickerMarksByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get all the stickerMarkList where relType equals to
        defaultStickerMarkFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllStickerMarksByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get all the stickerMarkList where relType in
        defaultStickerMarkFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllStickerMarksByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get all the stickerMarkList where relType is not null
        defaultStickerMarkFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllStickerMarksByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get all the stickerMarkList where relType contains
        defaultStickerMarkFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllStickerMarksByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        // Get all the stickerMarkList where relType does not contain
        defaultStickerMarkFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllStickerMarksByStickerIsEqualToSomething() throws Exception {
        Sticker sticker;
        if (TestUtil.findAll(em, Sticker.class).isEmpty()) {
            stickerMarkRepository.saveAndFlush(stickerMark);
            sticker = StickerResourceIT.createEntity(em);
        } else {
            sticker = TestUtil.findAll(em, Sticker.class).get(0);
        }
        em.persist(sticker);
        em.flush();
        stickerMark.setSticker(sticker);
        stickerMarkRepository.saveAndFlush(stickerMark);
        Long stickerId = sticker.getId();
        // Get all the stickerMarkList where sticker equals to stickerId
        defaultStickerMarkShouldBeFound("stickerId.equals=" + stickerId);

        // Get all the stickerMarkList where sticker equals to (stickerId + 1)
        defaultStickerMarkShouldNotBeFound("stickerId.equals=" + (stickerId + 1));
    }

    @Test
    @Transactional
    void getAllStickerMarksByMarkIsEqualToSomething() throws Exception {
        Mark mark;
        if (TestUtil.findAll(em, Mark.class).isEmpty()) {
            stickerMarkRepository.saveAndFlush(stickerMark);
            mark = MarkResourceIT.createEntity(em);
        } else {
            mark = TestUtil.findAll(em, Mark.class).get(0);
        }
        em.persist(mark);
        em.flush();
        stickerMark.setMark(mark);
        stickerMarkRepository.saveAndFlush(stickerMark);
        Long markId = mark.getId();
        // Get all the stickerMarkList where mark equals to markId
        defaultStickerMarkShouldBeFound("markId.equals=" + markId);

        // Get all the stickerMarkList where mark equals to (markId + 1)
        defaultStickerMarkShouldNotBeFound("markId.equals=" + (markId + 1));
    }

    private void defaultStickerMarkFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStickerMarkShouldBeFound(shouldBeFound);
        defaultStickerMarkShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStickerMarkShouldBeFound(String filter) throws Exception {
        restStickerMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stickerMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restStickerMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStickerMarkShouldNotBeFound(String filter) throws Exception {
        restStickerMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStickerMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStickerMark() throws Exception {
        // Get the stickerMark
        restStickerMarkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStickerMark() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stickerMark
        StickerMark updatedStickerMark = stickerMarkRepository.findById(stickerMark.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStickerMark are not directly saved in db
        em.detach(updatedStickerMark);
        updatedStickerMark.relType(UPDATED_REL_TYPE);
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(updatedStickerMark);

        restStickerMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stickerMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stickerMarkDTO))
            )
            .andExpect(status().isOk());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStickerMarkToMatchAllProperties(updatedStickerMark);
    }

    @Test
    @Transactional
    void putNonExistingStickerMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stickerMark.setId(longCount.incrementAndGet());

        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStickerMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stickerMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stickerMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStickerMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stickerMark.setId(longCount.incrementAndGet());

        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stickerMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStickerMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stickerMark.setId(longCount.incrementAndGet());

        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMarkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStickerMarkWithPatch() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stickerMark using partial update
        StickerMark partialUpdatedStickerMark = new StickerMark();
        partialUpdatedStickerMark.setId(stickerMark.getId());

        partialUpdatedStickerMark.relType(UPDATED_REL_TYPE);

        restStickerMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStickerMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStickerMark))
            )
            .andExpect(status().isOk());

        // Validate the StickerMark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStickerMarkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStickerMark, stickerMark),
            getPersistedStickerMark(stickerMark)
        );
    }

    @Test
    @Transactional
    void fullUpdateStickerMarkWithPatch() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stickerMark using partial update
        StickerMark partialUpdatedStickerMark = new StickerMark();
        partialUpdatedStickerMark.setId(stickerMark.getId());

        partialUpdatedStickerMark.relType(UPDATED_REL_TYPE);

        restStickerMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStickerMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStickerMark))
            )
            .andExpect(status().isOk());

        // Validate the StickerMark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStickerMarkUpdatableFieldsEquals(partialUpdatedStickerMark, getPersistedStickerMark(partialUpdatedStickerMark));
    }

    @Test
    @Transactional
    void patchNonExistingStickerMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stickerMark.setId(longCount.incrementAndGet());

        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStickerMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stickerMarkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stickerMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStickerMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stickerMark.setId(longCount.incrementAndGet());

        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stickerMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStickerMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stickerMark.setId(longCount.incrementAndGet());

        // Create the StickerMark
        StickerMarkDTO stickerMarkDTO = stickerMarkMapper.toDto(stickerMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMarkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stickerMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StickerMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStickerMark() throws Exception {
        // Initialize the database
        stickerMarkRepository.saveAndFlush(stickerMark);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stickerMark
        restStickerMarkMockMvc
            .perform(delete(ENTITY_API_URL_ID, stickerMark.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stickerMarkRepository.count();
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

    protected StickerMark getPersistedStickerMark(StickerMark stickerMark) {
        return stickerMarkRepository.findById(stickerMark.getId()).orElseThrow();
    }

    protected void assertPersistedStickerMarkToMatchAllProperties(StickerMark expectedStickerMark) {
        assertStickerMarkAllPropertiesEquals(expectedStickerMark, getPersistedStickerMark(expectedStickerMark));
    }

    protected void assertPersistedStickerMarkToMatchUpdatableProperties(StickerMark expectedStickerMark) {
        assertStickerMarkAllUpdatablePropertiesEquals(expectedStickerMark, getPersistedStickerMark(expectedStickerMark));
    }
}
