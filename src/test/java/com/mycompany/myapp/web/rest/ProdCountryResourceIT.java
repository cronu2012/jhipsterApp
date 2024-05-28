package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ProdCountryAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.domain.ProdCountry;
import com.mycompany.myapp.repository.ProdCountryRepository;
import com.mycompany.myapp.service.dto.ProdCountryDTO;
import com.mycompany.myapp.service.mapper.ProdCountryMapper;
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
 * Integration tests for the {@link ProdCountryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdCountryResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prod-countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProdCountryRepository prodCountryRepository;

    @Autowired
    private ProdCountryMapper prodCountryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdCountryMockMvc;

    private ProdCountry prodCountry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdCountry createEntity(EntityManager em) {
        ProdCountry prodCountry = new ProdCountry().relType(DEFAULT_REL_TYPE);
        return prodCountry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdCountry createUpdatedEntity(EntityManager em) {
        ProdCountry prodCountry = new ProdCountry().relType(UPDATED_REL_TYPE);
        return prodCountry;
    }

    @BeforeEach
    public void initTest() {
        prodCountry = createEntity(em);
    }

    @Test
    @Transactional
    void createProdCountry() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);
        var returnedProdCountryDTO = om.readValue(
            restProdCountryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodCountryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProdCountryDTO.class
        );

        // Validate the ProdCountry in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProdCountry = prodCountryMapper.toEntity(returnedProdCountryDTO);
        assertProdCountryUpdatableFieldsEquals(returnedProdCountry, getPersistedProdCountry(returnedProdCountry));
    }

    @Test
    @Transactional
    void createProdCountryWithExistingId() throws Exception {
        // Create the ProdCountry with an existing ID
        prodCountry.setId(1L);
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProdCountries() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get all the prodCountryList
        restProdCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getProdCountry() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get the prodCountry
        restProdCountryMockMvc
            .perform(get(ENTITY_API_URL_ID, prodCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodCountry.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getProdCountriesByIdFiltering() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        Long id = prodCountry.getId();

        defaultProdCountryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProdCountryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProdCountryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProdCountriesByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get all the prodCountryList where relType equals to
        defaultProdCountryFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdCountriesByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get all the prodCountryList where relType in
        defaultProdCountryFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdCountriesByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get all the prodCountryList where relType is not null
        defaultProdCountryFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllProdCountriesByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get all the prodCountryList where relType contains
        defaultProdCountryFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdCountriesByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        // Get all the prodCountryList where relType does not contain
        defaultProdCountryFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllProdCountriesByProdIsEqualToSomething() throws Exception {
        Prod prod;
        if (TestUtil.findAll(em, Prod.class).isEmpty()) {
            prodCountryRepository.saveAndFlush(prodCountry);
            prod = ProdResourceIT.createEntity(em);
        } else {
            prod = TestUtil.findAll(em, Prod.class).get(0);
        }
        em.persist(prod);
        em.flush();
        prodCountry.setProd(prod);
        prodCountryRepository.saveAndFlush(prodCountry);
        Long prodId = prod.getId();
        // Get all the prodCountryList where prod equals to prodId
        defaultProdCountryShouldBeFound("prodId.equals=" + prodId);

        // Get all the prodCountryList where prod equals to (prodId + 1)
        defaultProdCountryShouldNotBeFound("prodId.equals=" + (prodId + 1));
    }

    @Test
    @Transactional
    void getAllProdCountriesByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            prodCountryRepository.saveAndFlush(prodCountry);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        prodCountry.setCountry(country);
        prodCountryRepository.saveAndFlush(prodCountry);
        Long countryId = country.getId();
        // Get all the prodCountryList where country equals to countryId
        defaultProdCountryShouldBeFound("countryId.equals=" + countryId);

        // Get all the prodCountryList where country equals to (countryId + 1)
        defaultProdCountryShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    private void defaultProdCountryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProdCountryShouldBeFound(shouldBeFound);
        defaultProdCountryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdCountryShouldBeFound(String filter) throws Exception {
        restProdCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restProdCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdCountryShouldNotBeFound(String filter) throws Exception {
        restProdCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdCountryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProdCountry() throws Exception {
        // Get the prodCountry
        restProdCountryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProdCountry() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodCountry
        ProdCountry updatedProdCountry = prodCountryRepository.findById(prodCountry.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProdCountry are not directly saved in db
        em.detach(updatedProdCountry);
        updatedProdCountry.relType(UPDATED_REL_TYPE);
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(updatedProdCountry);

        restProdCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodCountryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodCountryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProdCountryToMatchAllProperties(updatedProdCountry);
    }

    @Test
    @Transactional
    void putNonExistingProdCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodCountry.setId(longCount.incrementAndGet());

        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodCountryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProdCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodCountry.setId(longCount.incrementAndGet());

        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProdCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodCountry.setId(longCount.incrementAndGet());

        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdCountryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodCountryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdCountryWithPatch() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodCountry using partial update
        ProdCountry partialUpdatedProdCountry = new ProdCountry();
        partialUpdatedProdCountry.setId(prodCountry.getId());

        restProdCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProdCountry))
            )
            .andExpect(status().isOk());

        // Validate the ProdCountry in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdCountryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProdCountry, prodCountry),
            getPersistedProdCountry(prodCountry)
        );
    }

    @Test
    @Transactional
    void fullUpdateProdCountryWithPatch() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prodCountry using partial update
        ProdCountry partialUpdatedProdCountry = new ProdCountry();
        partialUpdatedProdCountry.setId(prodCountry.getId());

        partialUpdatedProdCountry.relType(UPDATED_REL_TYPE);

        restProdCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProdCountry))
            )
            .andExpect(status().isOk());

        // Validate the ProdCountry in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdCountryUpdatableFieldsEquals(partialUpdatedProdCountry, getPersistedProdCountry(partialUpdatedProdCountry));
    }

    @Test
    @Transactional
    void patchNonExistingProdCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodCountry.setId(longCount.incrementAndGet());

        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prodCountryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProdCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodCountry.setId(longCount.incrementAndGet());

        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProdCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prodCountry.setId(longCount.incrementAndGet());

        // Create the ProdCountry
        ProdCountryDTO prodCountryDTO = prodCountryMapper.toDto(prodCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdCountryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prodCountryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProdCountry() throws Exception {
        // Initialize the database
        prodCountryRepository.saveAndFlush(prodCountry);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prodCountry
        restProdCountryMockMvc
            .perform(delete(ENTITY_API_URL_ID, prodCountry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prodCountryRepository.count();
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

    protected ProdCountry getPersistedProdCountry(ProdCountry prodCountry) {
        return prodCountryRepository.findById(prodCountry.getId()).orElseThrow();
    }

    protected void assertPersistedProdCountryToMatchAllProperties(ProdCountry expectedProdCountry) {
        assertProdCountryAllPropertiesEquals(expectedProdCountry, getPersistedProdCountry(expectedProdCountry));
    }

    protected void assertPersistedProdCountryToMatchUpdatableProperties(ProdCountry expectedProdCountry) {
        assertProdCountryAllUpdatablePropertiesEquals(expectedProdCountry, getPersistedProdCountry(expectedProdCountry));
    }
}
