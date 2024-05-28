package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CountryStdAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.CountryStd;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.repository.CountryStdRepository;
import com.mycompany.myapp.service.dto.CountryStdDTO;
import com.mycompany.myapp.service.mapper.CountryStdMapper;
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
 * Integration tests for the {@link CountryStdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountryStdResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/country-stds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CountryStdRepository countryStdRepository;

    @Autowired
    private CountryStdMapper countryStdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryStdMockMvc;

    private CountryStd countryStd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryStd createEntity(EntityManager em) {
        CountryStd countryStd = new CountryStd().relType(DEFAULT_REL_TYPE);
        return countryStd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryStd createUpdatedEntity(EntityManager em) {
        CountryStd countryStd = new CountryStd().relType(UPDATED_REL_TYPE);
        return countryStd;
    }

    @BeforeEach
    public void initTest() {
        countryStd = createEntity(em);
    }

    @Test
    @Transactional
    void createCountryStd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);
        var returnedCountryStdDTO = om.readValue(
            restCountryStdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryStdDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CountryStdDTO.class
        );

        // Validate the CountryStd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCountryStd = countryStdMapper.toEntity(returnedCountryStdDTO);
        assertCountryStdUpdatableFieldsEquals(returnedCountryStd, getPersistedCountryStd(returnedCountryStd));
    }

    @Test
    @Transactional
    void createCountryStdWithExistingId() throws Exception {
        // Create the CountryStd with an existing ID
        countryStd.setId(1L);
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryStdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryStdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCountryStds() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get all the countryStdList
        restCountryStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryStd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCountryStd() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get the countryStd
        restCountryStdMockMvc
            .perform(get(ENTITY_API_URL_ID, countryStd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countryStd.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCountryStdsByIdFiltering() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        Long id = countryStd.getId();

        defaultCountryStdFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCountryStdFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCountryStdFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountryStdsByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get all the countryStdList where relType equals to
        defaultCountryStdFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryStdsByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get all the countryStdList where relType in
        defaultCountryStdFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryStdsByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get all the countryStdList where relType is not null
        defaultCountryStdFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCountryStdsByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get all the countryStdList where relType contains
        defaultCountryStdFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryStdsByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        // Get all the countryStdList where relType does not contain
        defaultCountryStdFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryStdsByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            countryStdRepository.saveAndFlush(countryStd);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        countryStd.setCountry(country);
        countryStdRepository.saveAndFlush(countryStd);
        Long countryId = country.getId();
        // Get all the countryStdList where country equals to countryId
        defaultCountryStdShouldBeFound("countryId.equals=" + countryId);

        // Get all the countryStdList where country equals to (countryId + 1)
        defaultCountryStdShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllCountryStdsByStdIsEqualToSomething() throws Exception {
        Std std;
        if (TestUtil.findAll(em, Std.class).isEmpty()) {
            countryStdRepository.saveAndFlush(countryStd);
            std = StdResourceIT.createEntity(em);
        } else {
            std = TestUtil.findAll(em, Std.class).get(0);
        }
        em.persist(std);
        em.flush();
        countryStd.setStd(std);
        countryStdRepository.saveAndFlush(countryStd);
        Long stdId = std.getId();
        // Get all the countryStdList where std equals to stdId
        defaultCountryStdShouldBeFound("stdId.equals=" + stdId);

        // Get all the countryStdList where std equals to (stdId + 1)
        defaultCountryStdShouldNotBeFound("stdId.equals=" + (stdId + 1));
    }

    private void defaultCountryStdFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCountryStdShouldBeFound(shouldBeFound);
        defaultCountryStdShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryStdShouldBeFound(String filter) throws Exception {
        restCountryStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryStd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCountryStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryStdShouldNotBeFound(String filter) throws Exception {
        restCountryStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountryStd() throws Exception {
        // Get the countryStd
        restCountryStdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCountryStd() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryStd
        CountryStd updatedCountryStd = countryStdRepository.findById(countryStd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCountryStd are not directly saved in db
        em.detach(updatedCountryStd);
        updatedCountryStd.relType(UPDATED_REL_TYPE);
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(updatedCountryStd);

        restCountryStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryStdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryStdDTO))
            )
            .andExpect(status().isOk());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCountryStdToMatchAllProperties(updatedCountryStd);
    }

    @Test
    @Transactional
    void putNonExistingCountryStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryStd.setId(longCount.incrementAndGet());

        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryStdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountryStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryStd.setId(longCount.incrementAndGet());

        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountryStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryStd.setId(longCount.incrementAndGet());

        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryStdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryStdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountryStdWithPatch() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryStd using partial update
        CountryStd partialUpdatedCountryStd = new CountryStd();
        partialUpdatedCountryStd.setId(countryStd.getId());

        partialUpdatedCountryStd.relType(UPDATED_REL_TYPE);

        restCountryStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountryStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCountryStd))
            )
            .andExpect(status().isOk());

        // Validate the CountryStd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCountryStdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCountryStd, countryStd),
            getPersistedCountryStd(countryStd)
        );
    }

    @Test
    @Transactional
    void fullUpdateCountryStdWithPatch() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryStd using partial update
        CountryStd partialUpdatedCountryStd = new CountryStd();
        partialUpdatedCountryStd.setId(countryStd.getId());

        partialUpdatedCountryStd.relType(UPDATED_REL_TYPE);

        restCountryStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountryStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCountryStd))
            )
            .andExpect(status().isOk());

        // Validate the CountryStd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCountryStdUpdatableFieldsEquals(partialUpdatedCountryStd, getPersistedCountryStd(partialUpdatedCountryStd));
    }

    @Test
    @Transactional
    void patchNonExistingCountryStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryStd.setId(longCount.incrementAndGet());

        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countryStdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(countryStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountryStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryStd.setId(longCount.incrementAndGet());

        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(countryStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountryStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryStd.setId(longCount.incrementAndGet());

        // Create the CountryStd
        CountryStdDTO countryStdDTO = countryStdMapper.toDto(countryStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryStdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(countryStdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountryStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountryStd() throws Exception {
        // Initialize the database
        countryStdRepository.saveAndFlush(countryStd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the countryStd
        restCountryStdMockMvc
            .perform(delete(ENTITY_API_URL_ID, countryStd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return countryStdRepository.count();
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

    protected CountryStd getPersistedCountryStd(CountryStd countryStd) {
        return countryStdRepository.findById(countryStd.getId()).orElseThrow();
    }

    protected void assertPersistedCountryStdToMatchAllProperties(CountryStd expectedCountryStd) {
        assertCountryStdAllPropertiesEquals(expectedCountryStd, getPersistedCountryStd(expectedCountryStd));
    }

    protected void assertPersistedCountryStdToMatchUpdatableProperties(CountryStd expectedCountryStd) {
        assertCountryStdAllUpdatablePropertiesEquals(expectedCountryStd, getPersistedCountryStd(expectedCountryStd));
    }
}
