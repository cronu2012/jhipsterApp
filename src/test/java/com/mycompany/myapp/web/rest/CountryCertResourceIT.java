package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CountryCertAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.CountryCert;
import com.mycompany.myapp.repository.CountryCertRepository;
import com.mycompany.myapp.service.dto.CountryCertDTO;
import com.mycompany.myapp.service.mapper.CountryCertMapper;
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
 * Integration tests for the {@link CountryCertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountryCertResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/country-certs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CountryCertRepository countryCertRepository;

    @Autowired
    private CountryCertMapper countryCertMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryCertMockMvc;

    private CountryCert countryCert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryCert createEntity(EntityManager em) {
        CountryCert countryCert = new CountryCert().relType(DEFAULT_REL_TYPE);
        return countryCert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryCert createUpdatedEntity(EntityManager em) {
        CountryCert countryCert = new CountryCert().relType(UPDATED_REL_TYPE);
        return countryCert;
    }

    @BeforeEach
    public void initTest() {
        countryCert = createEntity(em);
    }

    @Test
    @Transactional
    void createCountryCert() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);
        var returnedCountryCertDTO = om.readValue(
            restCountryCertMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryCertDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CountryCertDTO.class
        );

        // Validate the CountryCert in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCountryCert = countryCertMapper.toEntity(returnedCountryCertDTO);
        assertCountryCertUpdatableFieldsEquals(returnedCountryCert, getPersistedCountryCert(returnedCountryCert));
    }

    @Test
    @Transactional
    void createCountryCertWithExistingId() throws Exception {
        // Create the CountryCert with an existing ID
        countryCert.setId(1L);
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryCertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryCertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCountryCerts() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get all the countryCertList
        restCountryCertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryCert.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCountryCert() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get the countryCert
        restCountryCertMockMvc
            .perform(get(ENTITY_API_URL_ID, countryCert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countryCert.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCountryCertsByIdFiltering() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        Long id = countryCert.getId();

        defaultCountryCertFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCountryCertFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCountryCertFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountryCertsByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get all the countryCertList where relType equals to
        defaultCountryCertFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryCertsByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get all the countryCertList where relType in
        defaultCountryCertFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryCertsByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get all the countryCertList where relType is not null
        defaultCountryCertFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCountryCertsByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get all the countryCertList where relType contains
        defaultCountryCertFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryCertsByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        // Get all the countryCertList where relType does not contain
        defaultCountryCertFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryCertsByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            countryCertRepository.saveAndFlush(countryCert);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        countryCert.setCountry(country);
        countryCertRepository.saveAndFlush(countryCert);
        Long countryId = country.getId();
        // Get all the countryCertList where country equals to countryId
        defaultCountryCertShouldBeFound("countryId.equals=" + countryId);

        // Get all the countryCertList where country equals to (countryId + 1)
        defaultCountryCertShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllCountryCertsByCerfIsEqualToSomething() throws Exception {
        Cerf cerf;
        if (TestUtil.findAll(em, Cerf.class).isEmpty()) {
            countryCertRepository.saveAndFlush(countryCert);
            cerf = CerfResourceIT.createEntity(em);
        } else {
            cerf = TestUtil.findAll(em, Cerf.class).get(0);
        }
        em.persist(cerf);
        em.flush();
        countryCert.setCerf(cerf);
        countryCertRepository.saveAndFlush(countryCert);
        Long cerfId = cerf.getId();
        // Get all the countryCertList where cerf equals to cerfId
        defaultCountryCertShouldBeFound("cerfId.equals=" + cerfId);

        // Get all the countryCertList where cerf equals to (cerfId + 1)
        defaultCountryCertShouldNotBeFound("cerfId.equals=" + (cerfId + 1));
    }

    private void defaultCountryCertFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCountryCertShouldBeFound(shouldBeFound);
        defaultCountryCertShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryCertShouldBeFound(String filter) throws Exception {
        restCountryCertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryCert.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCountryCertMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryCertShouldNotBeFound(String filter) throws Exception {
        restCountryCertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryCertMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountryCert() throws Exception {
        // Get the countryCert
        restCountryCertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCountryCert() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryCert
        CountryCert updatedCountryCert = countryCertRepository.findById(countryCert.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCountryCert are not directly saved in db
        em.detach(updatedCountryCert);
        updatedCountryCert.relType(UPDATED_REL_TYPE);
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(updatedCountryCert);

        restCountryCertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryCertDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryCertDTO))
            )
            .andExpect(status().isOk());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCountryCertToMatchAllProperties(updatedCountryCert);
    }

    @Test
    @Transactional
    void putNonExistingCountryCert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryCert.setId(longCount.incrementAndGet());

        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryCertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryCertDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryCertDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountryCert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryCert.setId(longCount.incrementAndGet());

        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryCertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryCertDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountryCert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryCert.setId(longCount.incrementAndGet());

        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryCertMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryCertDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountryCertWithPatch() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryCert using partial update
        CountryCert partialUpdatedCountryCert = new CountryCert();
        partialUpdatedCountryCert.setId(countryCert.getId());

        restCountryCertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountryCert.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCountryCert))
            )
            .andExpect(status().isOk());

        // Validate the CountryCert in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCountryCertUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCountryCert, countryCert),
            getPersistedCountryCert(countryCert)
        );
    }

    @Test
    @Transactional
    void fullUpdateCountryCertWithPatch() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryCert using partial update
        CountryCert partialUpdatedCountryCert = new CountryCert();
        partialUpdatedCountryCert.setId(countryCert.getId());

        partialUpdatedCountryCert.relType(UPDATED_REL_TYPE);

        restCountryCertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountryCert.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCountryCert))
            )
            .andExpect(status().isOk());

        // Validate the CountryCert in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCountryCertUpdatableFieldsEquals(partialUpdatedCountryCert, getPersistedCountryCert(partialUpdatedCountryCert));
    }

    @Test
    @Transactional
    void patchNonExistingCountryCert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryCert.setId(longCount.incrementAndGet());

        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryCertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countryCertDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(countryCertDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountryCert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryCert.setId(longCount.incrementAndGet());

        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryCertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(countryCertDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountryCert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryCert.setId(longCount.incrementAndGet());

        // Create the CountryCert
        CountryCertDTO countryCertDTO = countryCertMapper.toDto(countryCert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryCertMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(countryCertDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountryCert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountryCert() throws Exception {
        // Initialize the database
        countryCertRepository.saveAndFlush(countryCert);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the countryCert
        restCountryCertMockMvc
            .perform(delete(ENTITY_API_URL_ID, countryCert.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return countryCertRepository.count();
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

    protected CountryCert getPersistedCountryCert(CountryCert countryCert) {
        return countryCertRepository.findById(countryCert.getId()).orElseThrow();
    }

    protected void assertPersistedCountryCertToMatchAllProperties(CountryCert expectedCountryCert) {
        assertCountryCertAllPropertiesEquals(expectedCountryCert, getPersistedCountryCert(expectedCountryCert));
    }

    protected void assertPersistedCountryCertToMatchUpdatableProperties(CountryCert expectedCountryCert) {
        assertCountryCertAllUpdatablePropertiesEquals(expectedCountryCert, getPersistedCountryCert(expectedCountryCert));
    }
}
