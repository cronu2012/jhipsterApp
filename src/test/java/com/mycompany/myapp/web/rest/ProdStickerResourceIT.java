package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ProdStickerAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.domain.ProdSticker;
import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.repository.ProdStickerRepository;
import com.mycompany.myapp.service.dto.ProdStickerDTO;
import com.mycompany.myapp.service.mapper.ProdStickerMapper;
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
 * Integration tests for the {@link ProdStickerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdStickerResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prod-stickers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProdStickerRepository prodStickerRepository;

    @Autowired
    private ProdStickerMapper prodStickerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdStickerMockMvc;

    private ProdSticker prodSticker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdSticker createEntity(EntityManager em) {
        ProdSticker prodSticker = new ProdSticker().relType(DEFAULT_REL_TYPE);
        return prodSticker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdSticker createUpdatedEntity(EntityManager em) {
        ProdSticker prodSticker = new ProdSticker().relType(UPDATED_REL_TYPE);
        return prodSticker;
    }

    @BeforeEach
    public void initTest() {
        prodSticker = createEntity(em);
    }

    @Test
    @Transactional
    void createProdSticker() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);
        var returnedProdStickerDTO = om.readValue(
            restProdStickerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStickerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProdStickerDTO.class
        );

        // Validate the ProdSticker in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProdSticker = prodStickerMapper.toEntity(returnedProdStickerDTO);
        assertProdStickerUpdatableFieldsEquals(returnedProdSticker, getPersistedProdSticker(returnedProdSticker));
    }

    @Test
    @Transactional
    void createProdStickerWithExistingId() throws Exception {
        // Create the ProdSticker with an existing ID
        prodSticker.setId(1L);
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdStickerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStickerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProdStickers() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get all the prodStickerList
        restProdStickerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodSticker.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getProdSticker() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get the prodSticker
        restProdStickerMockMvc
            .perform(get(ENTITY_API_URL_ID, prodSticker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodSticker.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getProdStickersByIdFiltering() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        Long id = prodSticker.getId();

        defaultProdStickerFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProdStickerFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProdStickerFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProdStickersByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get all the prodStickerList where relType equals to
        defaultProdStickerFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStickersByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get all the prodStickerList where relType in
        defaultProdStickerFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStickersByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get all the prodStickerList where relType is not null
        defaultProdStickerFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllProdStickersByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get all the prodStickerList where relType contains
        defaultProdStickerFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStickersByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        // Get all the prodStickerList where relType does not contain
        defaultProdStickerFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStickersByProdIsEqualToSomething() throws Exception {
        Prod prod;
        if (TestUtil.findAll(em, Prod.class).isEmpty()) {
            prodStickerRepository.saveAndFlush(prodSticker);
            prod = ProdResourceIT.createEntity(em);
        } else {
            prod = TestUtil.findAll(em, Prod.class).get(0);
        }
        em.persist(prod);
        em.flush();
        prodSticker.setProd(prod);
        prodStickerRepository.saveAndFlush(prodSticker);
        Long prodId = prod.getId();
        // Get all the prodStickerList where prod equals to prodId
        defaultProdStickerShouldBeFound("prodId.equals=" + prodId);

        // Get all the prodStickerList where prod equals to (prodId + 1)
        defaultProdStickerShouldNotBeFound("prodId.equals=" + (prodId + 1));
    }

    @Test
    @Transactional
    void getAllProdStickersByStickerIsEqualToSomething() throws Exception {
        Sticker sticker;
        if (TestUtil.findAll(em, Sticker.class).isEmpty()) {
            prodStickerRepository.saveAndFlush(prodSticker);
            sticker = StickerResourceIT.createEntity(em);
        } else {
            sticker = TestUtil.findAll(em, Sticker.class).get(0);
        }
        em.persist(sticker);
        em.flush();
        prodSticker.setSticker(sticker);
        prodStickerRepository.saveAndFlush(prodSticker);
        Long stickerId = sticker.getId();
        // Get all the prodStickerList where sticker equals to stickerId
        defaultProdStickerShouldBeFound("stickerId.equals=" + stickerId);

        // Get all the prodStickerList where sticker equals to (stickerId + 1)
        defaultProdStickerShouldNotBeFound("stickerId.equals=" + (stickerId + 1));
    }

    private void defaultProdStickerFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProdStickerShouldBeFound(shouldBeFound);
        defaultProdStickerShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdStickerShouldBeFound(String filter) throws Exception {
        restProdStickerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodSticker.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restProdStickerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdStickerShouldNotBeFound(String filter) throws Exception {
        restProdStickerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdStickerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProdSticker() throws Exception {
        // Get the prodSticker
        restProdStickerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProdSticker() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodSticker
        ProdSticker updatedProdSticker = prodStickerRepository.findById(prodSticker.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProdSticker are not directly saved in db
        em.detach(updatedProdSticker);
        updatedProdSticker.relType(UPDATED_REL_TYPE);
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(updatedProdSticker);

        restProdStickerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodStickerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodStickerDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProdStickerToMatchAllProperties(updatedProdSticker);
    }

    @Test
    @Transactional
    void putNonExistingProdSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodSticker.setId(longCount.incrementAndGet());

        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdStickerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodStickerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodStickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProdSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodSticker.setId(longCount.incrementAndGet());

        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStickerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodStickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProdSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodSticker.setId(longCount.incrementAndGet());

        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStickerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStickerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdStickerWithPatch() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodSticker using partial update
        ProdSticker partialUpdatedProdSticker = new ProdSticker();
        partialUpdatedProdSticker.setId(prodSticker.getId());

        restProdStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdSticker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProdSticker))
            )
            .andExpect(status().isOk());

        // Validate the ProdSticker in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdStickerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProdSticker, prodSticker),
            getPersistedProdSticker(prodSticker)
        );
    }

    @Test
    @Transactional
    void fullUpdateProdStickerWithPatch() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodSticker using partial update
        ProdSticker partialUpdatedProdSticker = new ProdSticker();
        partialUpdatedProdSticker.setId(prodSticker.getId());

        partialUpdatedProdSticker.relType(UPDATED_REL_TYPE);

        restProdStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdSticker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProdSticker))
            )
            .andExpect(status().isOk());

        // Validate the ProdSticker in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdStickerUpdatableFieldsEquals(partialUpdatedProdSticker, getPersistedProdSticker(partialUpdatedProdSticker));
    }

    @Test
    @Transactional
    void patchNonExistingProdSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodSticker.setId(longCount.incrementAndGet());

        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prodStickerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodStickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProdSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodSticker.setId(longCount.incrementAndGet());

        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStickerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodStickerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProdSticker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodSticker.setId(longCount.incrementAndGet());

        // Create the ProdSticker
        ProdStickerDTO prodStickerDTO = prodStickerMapper.toDto(prodSticker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStickerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prodStickerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdSticker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProdSticker() throws Exception {
        // Initialize the database
        prodStickerRepository.saveAndFlush(prodSticker);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prodSticker
        restProdStickerMockMvc
            .perform(delete(ENTITY_API_URL_ID, prodSticker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prodStickerRepository.count();
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

    protected ProdSticker getPersistedProdSticker(ProdSticker prodSticker) {
        return prodStickerRepository.findById(prodSticker.getId()).orElseThrow();
    }

    protected void assertPersistedProdStickerToMatchAllProperties(ProdSticker expectedProdSticker) {
        assertProdStickerAllPropertiesEquals(expectedProdSticker, getPersistedProdSticker(expectedProdSticker));
    }

    protected void assertPersistedProdStickerToMatchUpdatableProperties(ProdSticker expectedProdSticker) {
        assertProdStickerAllUpdatablePropertiesEquals(expectedProdSticker, getPersistedProdSticker(expectedProdSticker));
    }
}
