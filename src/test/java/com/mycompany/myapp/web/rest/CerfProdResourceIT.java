package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CerfProdAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfProd;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.repository.CerfProdRepository;
import com.mycompany.myapp.service.dto.CerfProdDTO;
import com.mycompany.myapp.service.mapper.CerfProdMapper;
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
 * Integration tests for the {@link CerfProdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CerfProdResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cerf-prods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CerfProdRepository cerfProdRepository;

    @Autowired
    private CerfProdMapper cerfProdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCerfProdMockMvc;

    private CerfProd cerfProd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfProd createEntity(EntityManager em) {
        CerfProd cerfProd = new CerfProd().relType(DEFAULT_REL_TYPE);
        return cerfProd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfProd createUpdatedEntity(EntityManager em) {
        CerfProd cerfProd = new CerfProd().relType(UPDATED_REL_TYPE);
        return cerfProd;
    }

    @BeforeEach
    public void initTest() {
        cerfProd = createEntity(em);
    }

    @Test
    @Transactional
    void createCerfProd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);
        var returnedCerfProdDTO = om.readValue(
            restCerfProdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfProdDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CerfProdDTO.class
        );

        // Validate the CerfProd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCerfProd = cerfProdMapper.toEntity(returnedCerfProdDTO);
        assertCerfProdUpdatableFieldsEquals(returnedCerfProd, getPersistedCerfProd(returnedCerfProd));
    }

    @Test
    @Transactional
    void createCerfProdWithExistingId() throws Exception {
        // Create the CerfProd with an existing ID
        cerfProd.setId(1L);
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCerfProdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfProdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCerfProds() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get all the cerfProdList
        restCerfProdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfProd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCerfProd() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get the cerfProd
        restCerfProdMockMvc
            .perform(get(ENTITY_API_URL_ID, cerfProd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cerfProd.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCerfProdsByIdFiltering() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        Long id = cerfProd.getId();

        defaultCerfProdFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCerfProdFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCerfProdFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCerfProdsByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get all the cerfProdList where relType equals to
        defaultCerfProdFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfProdsByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get all the cerfProdList where relType in
        defaultCerfProdFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfProdsByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get all the cerfProdList where relType is not null
        defaultCerfProdFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfProdsByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get all the cerfProdList where relType contains
        defaultCerfProdFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfProdsByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        // Get all the cerfProdList where relType does not contain
        defaultCerfProdFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfProdsByCerfIsEqualToSomething() throws Exception {
        Cerf cerf;
        if (TestUtil.findAll(em, Cerf.class).isEmpty()) {
            cerfProdRepository.saveAndFlush(cerfProd);
            cerf = CerfResourceIT.createEntity(em);
        } else {
            cerf = TestUtil.findAll(em, Cerf.class).get(0);
        }
        em.persist(cerf);
        em.flush();
        cerfProd.setCerf(cerf);
        cerfProdRepository.saveAndFlush(cerfProd);
        Long cerfId = cerf.getId();
        // Get all the cerfProdList where cerf equals to cerfId
        defaultCerfProdShouldBeFound("cerfId.equals=" + cerfId);

        // Get all the cerfProdList where cerf equals to (cerfId + 1)
        defaultCerfProdShouldNotBeFound("cerfId.equals=" + (cerfId + 1));
    }

    @Test
    @Transactional
    void getAllCerfProdsByProdIsEqualToSomething() throws Exception {
        Prod prod;
        if (TestUtil.findAll(em, Prod.class).isEmpty()) {
            cerfProdRepository.saveAndFlush(cerfProd);
            prod = ProdResourceIT.createEntity(em);
        } else {
            prod = TestUtil.findAll(em, Prod.class).get(0);
        }
        em.persist(prod);
        em.flush();
        cerfProd.setProd(prod);
        cerfProdRepository.saveAndFlush(cerfProd);
        Long prodId = prod.getId();
        // Get all the cerfProdList where prod equals to prodId
        defaultCerfProdShouldBeFound("prodId.equals=" + prodId);

        // Get all the cerfProdList where prod equals to (prodId + 1)
        defaultCerfProdShouldNotBeFound("prodId.equals=" + (prodId + 1));
    }

    private void defaultCerfProdFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCerfProdShouldBeFound(shouldBeFound);
        defaultCerfProdShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCerfProdShouldBeFound(String filter) throws Exception {
        restCerfProdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfProd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCerfProdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCerfProdShouldNotBeFound(String filter) throws Exception {
        restCerfProdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCerfProdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCerfProd() throws Exception {
        // Get the cerfProd
        restCerfProdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCerfProd() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfProd
        CerfProd updatedCerfProd = cerfProdRepository.findById(cerfProd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCerfProd are not directly saved in db
        em.detach(updatedCerfProd);
        updatedCerfProd.relType(UPDATED_REL_TYPE);
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(updatedCerfProd);

        restCerfProdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfProdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfProdDTO))
            )
            .andExpect(status().isOk());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCerfProdToMatchAllProperties(updatedCerfProd);
    }

    @Test
    @Transactional
    void putNonExistingCerfProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfProd.setId(longCount.incrementAndGet());

        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfProdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfProdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfProdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCerfProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfProd.setId(longCount.incrementAndGet());

        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfProdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfProdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCerfProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfProd.setId(longCount.incrementAndGet());

        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfProdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfProdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCerfProdWithPatch() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfProd using partial update
        CerfProd partialUpdatedCerfProd = new CerfProd();
        partialUpdatedCerfProd.setId(cerfProd.getId());

        partialUpdatedCerfProd.relType(UPDATED_REL_TYPE);

        restCerfProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfProd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfProd))
            )
            .andExpect(status().isOk());

        // Validate the CerfProd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfProdUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCerfProd, cerfProd), getPersistedCerfProd(cerfProd));
    }

    @Test
    @Transactional
    void fullUpdateCerfProdWithPatch() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfProd using partial update
        CerfProd partialUpdatedCerfProd = new CerfProd();
        partialUpdatedCerfProd.setId(cerfProd.getId());

        partialUpdatedCerfProd.relType(UPDATED_REL_TYPE);

        restCerfProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfProd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfProd))
            )
            .andExpect(status().isOk());

        // Validate the CerfProd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfProdUpdatableFieldsEquals(partialUpdatedCerfProd, getPersistedCerfProd(partialUpdatedCerfProd));
    }

    @Test
    @Transactional
    void patchNonExistingCerfProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfProd.setId(longCount.incrementAndGet());

        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cerfProdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfProdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCerfProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfProd.setId(longCount.incrementAndGet());

        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfProdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCerfProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfProd.setId(longCount.incrementAndGet());

        // Create the CerfProd
        CerfProdDTO cerfProdDTO = cerfProdMapper.toDto(cerfProd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfProdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cerfProdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfProd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCerfProd() throws Exception {
        // Initialize the database
        cerfProdRepository.saveAndFlush(cerfProd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cerfProd
        restCerfProdMockMvc
            .perform(delete(ENTITY_API_URL_ID, cerfProd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cerfProdRepository.count();
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

    protected CerfProd getPersistedCerfProd(CerfProd cerfProd) {
        return cerfProdRepository.findById(cerfProd.getId()).orElseThrow();
    }

    protected void assertPersistedCerfProdToMatchAllProperties(CerfProd expectedCerfProd) {
        assertCerfProdAllPropertiesEquals(expectedCerfProd, getPersistedCerfProd(expectedCerfProd));
    }

    protected void assertPersistedCerfProdToMatchUpdatableProperties(CerfProd expectedCerfProd) {
        assertCerfProdAllUpdatablePropertiesEquals(expectedCerfProd, getPersistedCerfProd(expectedCerfProd));
    }
}
