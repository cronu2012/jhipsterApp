package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CountryMarkAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.CountryMark;
import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.repository.CountryMarkRepository;
import com.mycompany.myapp.service.dto.CountryMarkDTO;
import com.mycompany.myapp.service.mapper.CountryMarkMapper;
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
 * Integration tests for the {@link CountryMarkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountryMarkResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/country-marks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CountryMarkRepository countryMarkRepository;

    @Autowired
    private CountryMarkMapper countryMarkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryMarkMockMvc;

    private CountryMark countryMark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryMark createEntity(EntityManager em) {
        CountryMark countryMark = new CountryMark().relType(DEFAULT_REL_TYPE);
        return countryMark;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryMark createUpdatedEntity(EntityManager em) {
        CountryMark countryMark = new CountryMark().relType(UPDATED_REL_TYPE);
        return countryMark;
    }

    @BeforeEach
    public void initTest() {
        countryMark = createEntity(em);
    }

    @Test
    @Transactional
    void createCountryMark() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);
        var returnedCountryMarkDTO = om.readValue(
            restCountryMarkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryMarkDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CountryMarkDTO.class
        );

        // Validate the CountryMark in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCountryMark = countryMarkMapper.toEntity(returnedCountryMarkDTO);
        assertCountryMarkUpdatableFieldsEquals(returnedCountryMark, getPersistedCountryMark(returnedCountryMark));
    }

    @Test
    @Transactional
    void createCountryMarkWithExistingId() throws Exception {
        // Create the CountryMark with an existing ID
        countryMark.setId(1L);
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMarkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryMarkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCountryMarks() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get all the countryMarkList
        restCountryMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCountryMark() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get the countryMark
        restCountryMarkMockMvc
            .perform(get(ENTITY_API_URL_ID, countryMark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countryMark.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCountryMarksByIdFiltering() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        Long id = countryMark.getId();

        defaultCountryMarkFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCountryMarkFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCountryMarkFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountryMarksByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get all the countryMarkList where relType equals to
        defaultCountryMarkFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryMarksByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get all the countryMarkList where relType in
        defaultCountryMarkFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryMarksByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get all the countryMarkList where relType is not null
        defaultCountryMarkFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCountryMarksByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get all the countryMarkList where relType contains
        defaultCountryMarkFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryMarksByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        // Get all the countryMarkList where relType does not contain
        defaultCountryMarkFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCountryMarksByCountryIsEqualToSomething() throws Exception {
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            countryMarkRepository.saveAndFlush(countryMark);
            country = CountryResourceIT.createEntity(em);
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(country);
        em.flush();
        countryMark.setCountry(country);
        countryMarkRepository.saveAndFlush(countryMark);
        Long countryId = country.getId();
        // Get all the countryMarkList where country equals to countryId
        defaultCountryMarkShouldBeFound("countryId.equals=" + countryId);

        // Get all the countryMarkList where country equals to (countryId + 1)
        defaultCountryMarkShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllCountryMarksByMarkIsEqualToSomething() throws Exception {
        Mark mark;
        if (TestUtil.findAll(em, Mark.class).isEmpty()) {
            countryMarkRepository.saveAndFlush(countryMark);
            mark = MarkResourceIT.createEntity(em);
        } else {
            mark = TestUtil.findAll(em, Mark.class).get(0);
        }
        em.persist(mark);
        em.flush();
        countryMark.setMark(mark);
        countryMarkRepository.saveAndFlush(countryMark);
        Long markId = mark.getId();
        // Get all the countryMarkList where mark equals to markId
        defaultCountryMarkShouldBeFound("markId.equals=" + markId);

        // Get all the countryMarkList where mark equals to (markId + 1)
        defaultCountryMarkShouldNotBeFound("markId.equals=" + (markId + 1));
    }

    private void defaultCountryMarkFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCountryMarkShouldBeFound(shouldBeFound);
        defaultCountryMarkShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryMarkShouldBeFound(String filter) throws Exception {
        restCountryMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCountryMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryMarkShouldNotBeFound(String filter) throws Exception {
        restCountryMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountryMark() throws Exception {
        // Get the countryMark
        restCountryMarkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCountryMark() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryMark
        CountryMark updatedCountryMark = countryMarkRepository.findById(countryMark.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCountryMark are not directly saved in db
        em.detach(updatedCountryMark);
        updatedCountryMark.relType(UPDATED_REL_TYPE);
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(updatedCountryMark);

        restCountryMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryMarkDTO))
            )
            .andExpect(status().isOk());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCountryMarkToMatchAllProperties(updatedCountryMark);
    }

    @Test
    @Transactional
    void putNonExistingCountryMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryMark.setId(longCount.incrementAndGet());

        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countryMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountryMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryMark.setId(longCount.incrementAndGet());

        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(countryMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountryMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryMark.setId(longCount.incrementAndGet());

        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMarkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(countryMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountryMarkWithPatch() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryMark using partial update
        CountryMark partialUpdatedCountryMark = new CountryMark();
        partialUpdatedCountryMark.setId(countryMark.getId());

        partialUpdatedCountryMark.relType(UPDATED_REL_TYPE);

        restCountryMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountryMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCountryMark))
            )
            .andExpect(status().isOk());

        // Validate the CountryMark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCountryMarkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCountryMark, countryMark),
            getPersistedCountryMark(countryMark)
        );
    }

    @Test
    @Transactional
    void fullUpdateCountryMarkWithPatch() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the countryMark using partial update
        CountryMark partialUpdatedCountryMark = new CountryMark();
        partialUpdatedCountryMark.setId(countryMark.getId());

        partialUpdatedCountryMark.relType(UPDATED_REL_TYPE);

        restCountryMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountryMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCountryMark))
            )
            .andExpect(status().isOk());

        // Validate the CountryMark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCountryMarkUpdatableFieldsEquals(partialUpdatedCountryMark, getPersistedCountryMark(partialUpdatedCountryMark));
    }

    @Test
    @Transactional
    void patchNonExistingCountryMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryMark.setId(longCount.incrementAndGet());

        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countryMarkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(countryMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountryMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryMark.setId(longCount.incrementAndGet());

        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(countryMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountryMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        countryMark.setId(longCount.incrementAndGet());

        // Create the CountryMark
        CountryMarkDTO countryMarkDTO = countryMarkMapper.toDto(countryMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountryMarkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(countryMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountryMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountryMark() throws Exception {
        // Initialize the database
        countryMarkRepository.saveAndFlush(countryMark);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the countryMark
        restCountryMarkMockMvc
            .perform(delete(ENTITY_API_URL_ID, countryMark.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return countryMarkRepository.count();
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

    protected CountryMark getPersistedCountryMark(CountryMark countryMark) {
        return countryMarkRepository.findById(countryMark.getId()).orElseThrow();
    }

    protected void assertPersistedCountryMarkToMatchAllProperties(CountryMark expectedCountryMark) {
        assertCountryMarkAllPropertiesEquals(expectedCountryMark, getPersistedCountryMark(expectedCountryMark));
    }

    protected void assertPersistedCountryMarkToMatchUpdatableProperties(CountryMark expectedCountryMark) {
        assertCountryMarkAllUpdatablePropertiesEquals(expectedCountryMark, getPersistedCountryMark(expectedCountryMark));
    }
}
