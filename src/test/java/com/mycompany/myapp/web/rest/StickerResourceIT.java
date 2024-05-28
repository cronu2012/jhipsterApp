package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.StickerAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.repository.StickerRepository;
import com.mycompany.myapp.service.dto.StickerDTO;
import com.mycompany.myapp.service.mapper.StickerMapper;
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
 * Integration tests for the {@link StickerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StickerResourceIT {

    private static final String DEFAULT_STICKER_NO = "AAAAAAAAAA";
    private static final String UPDATED_STICKER_NO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/stickers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StickerRepository stickerRepository;

    @Autowired
    private StickerMapper stickerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStickerMockMvc;

    private Sticker sticker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sticker createEntity(EntityManager em) {
        Sticker sticker = new Sticker().stickerNo(DEFAULT_STICKER_NO).img(DEFAULT_IMG).imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return sticker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sticker createUpdatedEntity(EntityManager em) {
        Sticker sticker = new Sticker().stickerNo(UPDATED_STICKER_NO).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return sticker;
    }

    @BeforeEach
    public void initTest() {
        sticker = createEntity(em);
    }

    @Test
    @Transactional
    void createSticker() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);
        var returnedStickerDTO = om.readValue(
            restStickerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StickerDTO.class
        );

        // Validate the Sticker in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSticker = stickerMapper.toEntity(returnedStickerDTO);
        assertStickerUpdatableFieldsEquals(returnedSticker, getPersistedSticker(returnedSticker));
    }

    @Test
    @Transactional
    void createStickerWithExistingId() throws Exception {
        // Create the Sticker with an existing ID
        sticker.setId(1L);
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStickerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStickers() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get all the stickerList
        restStickerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sticker.getId().intValue())))
            .andExpect(jsonPath("$.[*].stickerNo").value(hasItem(DEFAULT_STICKER_NO)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    void getSticker() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get the sticker
        restStickerMockMvc
            .perform(get(ENTITY_API_URL_ID, sticker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sticker.getId().intValue()))
            .andExpect(jsonPath("$.stickerNo").value(DEFAULT_STICKER_NO))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64.getEncoder().encodeToString(DEFAULT_IMG)));
    }

    @Test
    @Transactional
    void getStickersByIdFiltering() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        Long id = sticker.getId();

        defaultStickerFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStickerFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStickerFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStickersByStickerNoIsEqualToSomething() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get all the stickerList where stickerNo equals to
        defaultStickerFiltering("stickerNo.equals=" + DEFAULT_STICKER_NO, "stickerNo.equals=" + UPDATED_STICKER_NO);
    }

    @Test
    @Transactional
    void getAllStickersByStickerNoIsInShouldWork() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get all the stickerList where stickerNo in
        defaultStickerFiltering("stickerNo.in=" + DEFAULT_STICKER_NO + "," + UPDATED_STICKER_NO, "stickerNo.in=" + UPDATED_STICKER_NO);
    }

    @Test
    @Transactional
    void getAllStickersByStickerNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get all the stickerList where stickerNo is not null
        defaultStickerFiltering("stickerNo.specified=true", "stickerNo.specified=false");
    }

    @Test
    @Transactional
    void getAllStickersByStickerNoContainsSomething() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get all the stickerList where stickerNo contains
        defaultStickerFiltering("stickerNo.contains=" + DEFAULT_STICKER_NO, "stickerNo.contains=" + UPDATED_STICKER_NO);
    }

    @Test
    @Transactional
    void getAllStickersByStickerNoNotContainsSomething() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        // Get all the stickerList where stickerNo does not contain
        defaultStickerFiltering("stickerNo.doesNotContain=" + UPDATED_STICKER_NO, "stickerNo.doesNotContain=" + DEFAULT_STICKER_NO);
    }

    private void defaultStickerFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStickerShouldBeFound(shouldBeFound);
        defaultStickerShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStickerShouldBeFound(String filter) throws Exception {
        restStickerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sticker.getId().intValue())))
            .andExpect(jsonPath("$.[*].stickerNo").value(hasItem(DEFAULT_STICKER_NO)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMG))));

        // Check, that the count call also returns 1
        restStickerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStickerShouldNotBeFound(String filter) throws Exception {
        restStickerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStickerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSticker() throws Exception {
        // Get the sticker
        restStickerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSticker() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sticker
        Sticker updatedSticker = stickerRepository.findById(sticker.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSticker are not directly saved in db
        em.detach(updatedSticker);
        updatedSticker.stickerNo(UPDATED_STICKER_NO).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);
        StickerDTO stickerDTO = stickerMapper.toDto(updatedSticker);

        restStickerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stickerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStickerToMatchAllProperties(updatedSticker);
    }

    @Test
    @Transactional
    void putNonExistingSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sticker.setId(longCount.incrementAndGet());

        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStickerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stickerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sticker.setId(longCount.incrementAndGet());

        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sticker.setId(longCount.incrementAndGet());

        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stickerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStickerWithPatch() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sticker using partial update
        Sticker partialUpdatedSticker = new Sticker();
        partialUpdatedSticker.setId(sticker.getId());

        partialUpdatedSticker.stickerNo(UPDATED_STICKER_NO).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSticker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSticker))
            )
            .andExpect(status().isOk());

        // Validate the Sticker in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStickerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSticker, sticker), getPersistedSticker(sticker));
    }

    @Test
    @Transactional
    void fullUpdateStickerWithPatch() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sticker using partial update
        Sticker partialUpdatedSticker = new Sticker();
        partialUpdatedSticker.setId(sticker.getId());

        partialUpdatedSticker.stickerNo(UPDATED_STICKER_NO).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSticker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSticker))
            )
            .andExpect(status().isOk());

        // Validate the Sticker in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStickerUpdatableFieldsEquals(partialUpdatedSticker, getPersistedSticker(partialUpdatedSticker));
    }

    @Test
    @Transactional
    void patchNonExistingSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sticker.setId(longCount.incrementAndGet());

        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stickerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sticker.setId(longCount.incrementAndGet());

        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sticker.setId(longCount.incrementAndGet());

        // Create the Sticker
        StickerDTO stickerDTO = stickerMapper.toDto(sticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStickerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stickerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSticker() throws Exception {
        // Initialize the database
        stickerRepository.saveAndFlush(sticker);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sticker
        restStickerMockMvc
            .perform(delete(ENTITY_API_URL_ID, sticker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stickerRepository.count();
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

    protected Sticker getPersistedSticker(Sticker sticker) {
        return stickerRepository.findById(sticker.getId()).orElseThrow();
    }

    protected void assertPersistedStickerToMatchAllProperties(Sticker expectedSticker) {
        assertStickerAllPropertiesEquals(expectedSticker, getPersistedSticker(expectedSticker));
    }

    protected void assertPersistedStickerToMatchUpdatableProperties(Sticker expectedSticker) {
        assertStickerAllUpdatablePropertiesEquals(expectedSticker, getPersistedSticker(expectedSticker));
    }
}
