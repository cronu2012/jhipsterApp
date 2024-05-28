package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ProdStdAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.domain.ProdStd;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.repository.ProdStdRepository;
import com.mycompany.myapp.service.dto.ProdStdDTO;
import com.mycompany.myapp.service.mapper.ProdStdMapper;
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
 * Integration tests for the {@link ProdStdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdStdResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prod-stds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProdStdRepository prodStdRepository;

    @Autowired
    private ProdStdMapper prodStdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdStdMockMvc;

    private ProdStd prodStd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdStd createEntity(EntityManager em) {
        ProdStd prodStd = new ProdStd().relType(DEFAULT_REL_TYPE);
        return prodStd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdStd createUpdatedEntity(EntityManager em) {
        ProdStd prodStd = new ProdStd().relType(UPDATED_REL_TYPE);
        return prodStd;
    }

    @BeforeEach
    public void initTest() {
        prodStd = createEntity(em);
    }

    @Test
    @Transactional
    void createProdStd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);
        var returnedProdStdDTO = om.readValue(
            restProdStdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStdDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProdStdDTO.class
        );

        // Validate the ProdStd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProdStd = prodStdMapper.toEntity(returnedProdStdDTO);
        assertProdStdUpdatableFieldsEquals(returnedProdStd, getPersistedProdStd(returnedProdStd));
    }

    @Test
    @Transactional
    void createProdStdWithExistingId() throws Exception {
        // Create the ProdStd with an existing ID
        prodStd.setId(1L);
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdStdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProdStds() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get all the prodStdList
        restProdStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodStd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getProdStd() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get the prodStd
        restProdStdMockMvc
            .perform(get(ENTITY_API_URL_ID, prodStd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodStd.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getProdStdsByIdFiltering() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        Long id = prodStd.getId();

        defaultProdStdFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProdStdFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProdStdFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProdStdsByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get all the prodStdList where relType equals to
        defaultProdStdFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStdsByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get all the prodStdList where relType in
        defaultProdStdFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStdsByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get all the prodStdList where relType is not null
        defaultProdStdFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllProdStdsByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get all the prodStdList where relType contains
        defaultProdStdFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStdsByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        // Get all the prodStdList where relType does not contain
        defaultProdStdFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdStdsByProdIsEqualToSomething() throws Exception {
        Prod prod;
        if (TestUtil.findAll(em, Prod.class).isEmpty()) {
            prodStdRepository.saveAndFlush(prodStd);
            prod = ProdResourceIT.createEntity(em);
        } else {
            prod = TestUtil.findAll(em, Prod.class).get(0);
        }
        em.persist(prod);
        em.flush();
        prodStd.setProd(prod);
        prodStdRepository.saveAndFlush(prodStd);
        Long prodId = prod.getId();
        // Get all the prodStdList where prod equals to prodId
        defaultProdStdShouldBeFound("prodId.equals=" + prodId);

        // Get all the prodStdList where prod equals to (prodId + 1)
        defaultProdStdShouldNotBeFound("prodId.equals=" + (prodId + 1));
    }

    @Test
    @Transactional
    void getAllProdStdsByStdIsEqualToSomething() throws Exception {
        Std std;
        if (TestUtil.findAll(em, Std.class).isEmpty()) {
            prodStdRepository.saveAndFlush(prodStd);
            std = StdResourceIT.createEntity(em);
        } else {
            std = TestUtil.findAll(em, Std.class).get(0);
        }
        em.persist(std);
        em.flush();
        prodStd.setStd(std);
        prodStdRepository.saveAndFlush(prodStd);
        Long stdId = std.getId();
        // Get all the prodStdList where std equals to stdId
        defaultProdStdShouldBeFound("stdId.equals=" + stdId);

        // Get all the prodStdList where std equals to (stdId + 1)
        defaultProdStdShouldNotBeFound("stdId.equals=" + (stdId + 1));
    }

    private void defaultProdStdFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProdStdShouldBeFound(shouldBeFound);
        defaultProdStdShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdStdShouldBeFound(String filter) throws Exception {
        restProdStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodStd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restProdStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdStdShouldNotBeFound(String filter) throws Exception {
        restProdStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProdStd() throws Exception {
        // Get the prodStd
        restProdStdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProdStd() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodStd
        ProdStd updatedProdStd = prodStdRepository.findById(prodStd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProdStd are not directly saved in db
        em.detach(updatedProdStd);
        updatedProdStd.relType(UPDATED_REL_TYPE);
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(updatedProdStd);

        restProdStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodStdDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStdDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProdStdToMatchAllProperties(updatedProdStd);
    }

    @Test
    @Transactional
    void putNonExistingProdStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodStd.setId(longCount.incrementAndGet());

        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodStdDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProdStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodStd.setId(longCount.incrementAndGet());

        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProdStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodStd.setId(longCount.incrementAndGet());

        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodStdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdStdWithPatch() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodStd using partial update
        ProdStd partialUpdatedProdStd = new ProdStd();
        partialUpdatedProdStd.setId(prodStd.getId());

        partialUpdatedProdStd.relType(UPDATED_REL_TYPE);

        restProdStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProdStd))
            )
            .andExpect(status().isOk());

        // Validate the ProdStd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdStdUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProdStd, prodStd), getPersistedProdStd(prodStd));
    }

    @Test
    @Transactional
    void fullUpdateProdStdWithPatch() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodStd using partial update
        ProdStd partialUpdatedProdStd = new ProdStd();
        partialUpdatedProdStd.setId(prodStd.getId());

        partialUpdatedProdStd.relType(UPDATED_REL_TYPE);

        restProdStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProdStd))
            )
            .andExpect(status().isOk());

        // Validate the ProdStd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdStdUpdatableFieldsEquals(partialUpdatedProdStd, getPersistedProdStd(partialUpdatedProdStd));
    }

    @Test
    @Transactional
    void patchNonExistingProdStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodStd.setId(longCount.incrementAndGet());

        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prodStdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProdStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodStd.setId(longCount.incrementAndGet());

        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProdStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodStd.setId(longCount.incrementAndGet());

        // Create the ProdStd
        ProdStdDTO prodStdDTO = prodStdMapper.toDto(prodStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdStdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prodStdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProdStd() throws Exception {
        // Initialize the database
        prodStdRepository.saveAndFlush(prodStd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prodStd
        restProdStdMockMvc
            .perform(delete(ENTITY_API_URL_ID, prodStd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prodStdRepository.count();
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

    protected ProdStd getPersistedProdStd(ProdStd prodStd) {
        return prodStdRepository.findById(prodStd.getId()).orElseThrow();
    }

    protected void assertPersistedProdStdToMatchAllProperties(ProdStd expectedProdStd) {
        assertProdStdAllPropertiesEquals(expectedProdStd, getPersistedProdStd(expectedProdStd));
    }

    protected void assertPersistedProdStdToMatchUpdatableProperties(ProdStd expectedProdStd) {
        assertProdStdAllUpdatablePropertiesEquals(expectedProdStd, getPersistedProdStd(expectedProdStd));
    }
}
