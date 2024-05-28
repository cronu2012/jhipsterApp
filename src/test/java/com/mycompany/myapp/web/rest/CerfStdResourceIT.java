package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CerfStdAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfStd;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.repository.CerfStdRepository;
import com.mycompany.myapp.service.dto.CerfStdDTO;
import com.mycompany.myapp.service.mapper.CerfStdMapper;
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
 * Integration tests for the {@link CerfStdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CerfStdResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cerf-stds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CerfStdRepository cerfStdRepository;

    @Autowired
    private CerfStdMapper cerfStdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCerfStdMockMvc;

    private CerfStd cerfStd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfStd createEntity(EntityManager em) {
        CerfStd cerfStd = new CerfStd().relType(DEFAULT_REL_TYPE);
        return cerfStd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfStd createUpdatedEntity(EntityManager em) {
        CerfStd cerfStd = new CerfStd().relType(UPDATED_REL_TYPE);
        return cerfStd;
    }

    @BeforeEach
    public void initTest() {
        cerfStd = createEntity(em);
    }

    @Test
    @Transactional
    void createCerfStd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);
        var returnedCerfStdDTO = om.readValue(
            restCerfStdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfStdDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CerfStdDTO.class
        );

        // Validate the CerfStd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCerfStd = cerfStdMapper.toEntity(returnedCerfStdDTO);
        assertCerfStdUpdatableFieldsEquals(returnedCerfStd, getPersistedCerfStd(returnedCerfStd));
    }

    @Test
    @Transactional
    void createCerfStdWithExistingId() throws Exception {
        // Create the CerfStd with an existing ID
        cerfStd.setId(1L);
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCerfStdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfStdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCerfStds() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get all the cerfStdList
        restCerfStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfStd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCerfStd() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get the cerfStd
        restCerfStdMockMvc
            .perform(get(ENTITY_API_URL_ID, cerfStd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cerfStd.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCerfStdsByIdFiltering() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        Long id = cerfStd.getId();

        defaultCerfStdFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCerfStdFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCerfStdFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCerfStdsByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get all the cerfStdList where relType equals to
        defaultCerfStdFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfStdsByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get all the cerfStdList where relType in
        defaultCerfStdFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfStdsByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get all the cerfStdList where relType is not null
        defaultCerfStdFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfStdsByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get all the cerfStdList where relType contains
        defaultCerfStdFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfStdsByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        // Get all the cerfStdList where relType does not contain
        defaultCerfStdFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfStdsByCerfIsEqualToSomething() throws Exception {
        Cerf cerf;
        if (TestUtil.findAll(em, Cerf.class).isEmpty()) {
            cerfStdRepository.saveAndFlush(cerfStd);
            cerf = CerfResourceIT.createEntity(em);
        } else {
            cerf = TestUtil.findAll(em, Cerf.class).get(0);
        }
        em.persist(cerf);
        em.flush();
        cerfStd.setCerf(cerf);
        cerfStdRepository.saveAndFlush(cerfStd);
        Long cerfId = cerf.getId();
        // Get all the cerfStdList where cerf equals to cerfId
        defaultCerfStdShouldBeFound("cerfId.equals=" + cerfId);

        // Get all the cerfStdList where cerf equals to (cerfId + 1)
        defaultCerfStdShouldNotBeFound("cerfId.equals=" + (cerfId + 1));
    }

    @Test
    @Transactional
    void getAllCerfStdsByStdIsEqualToSomething() throws Exception {
        Std std;
        if (TestUtil.findAll(em, Std.class).isEmpty()) {
            cerfStdRepository.saveAndFlush(cerfStd);
            std = StdResourceIT.createEntity(em);
        } else {
            std = TestUtil.findAll(em, Std.class).get(0);
        }
        em.persist(std);
        em.flush();
        cerfStd.setStd(std);
        cerfStdRepository.saveAndFlush(cerfStd);
        Long stdId = std.getId();
        // Get all the cerfStdList where std equals to stdId
        defaultCerfStdShouldBeFound("stdId.equals=" + stdId);

        // Get all the cerfStdList where std equals to (stdId + 1)
        defaultCerfStdShouldNotBeFound("stdId.equals=" + (stdId + 1));
    }

    private void defaultCerfStdFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCerfStdShouldBeFound(shouldBeFound);
        defaultCerfStdShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCerfStdShouldBeFound(String filter) throws Exception {
        restCerfStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfStd.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCerfStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCerfStdShouldNotBeFound(String filter) throws Exception {
        restCerfStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCerfStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCerfStd() throws Exception {
        // Get the cerfStd
        restCerfStdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCerfStd() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfStd
        CerfStd updatedCerfStd = cerfStdRepository.findById(cerfStd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCerfStd are not directly saved in db
        em.detach(updatedCerfStd);
        updatedCerfStd.relType(UPDATED_REL_TYPE);
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(updatedCerfStd);

        restCerfStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfStdDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfStdDTO))
            )
            .andExpect(status().isOk());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCerfStdToMatchAllProperties(updatedCerfStd);
    }

    @Test
    @Transactional
    void putNonExistingCerfStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfStd.setId(longCount.incrementAndGet());

        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfStdDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCerfStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfStd.setId(longCount.incrementAndGet());

        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCerfStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfStd.setId(longCount.incrementAndGet());

        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfStdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfStdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCerfStdWithPatch() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfStd using partial update
        CerfStd partialUpdatedCerfStd = new CerfStd();
        partialUpdatedCerfStd.setId(cerfStd.getId());

        partialUpdatedCerfStd.relType(UPDATED_REL_TYPE);

        restCerfStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfStd))
            )
            .andExpect(status().isOk());

        // Validate the CerfStd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfStdUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCerfStd, cerfStd), getPersistedCerfStd(cerfStd));
    }

    @Test
    @Transactional
    void fullUpdateCerfStdWithPatch() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfStd using partial update
        CerfStd partialUpdatedCerfStd = new CerfStd();
        partialUpdatedCerfStd.setId(cerfStd.getId());

        partialUpdatedCerfStd.relType(UPDATED_REL_TYPE);

        restCerfStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfStd))
            )
            .andExpect(status().isOk());

        // Validate the CerfStd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfStdUpdatableFieldsEquals(partialUpdatedCerfStd, getPersistedCerfStd(partialUpdatedCerfStd));
    }

    @Test
    @Transactional
    void patchNonExistingCerfStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfStd.setId(longCount.incrementAndGet());

        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cerfStdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCerfStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfStd.setId(longCount.incrementAndGet());

        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfStdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCerfStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfStd.setId(longCount.incrementAndGet());

        // Create the CerfStd
        CerfStdDTO cerfStdDTO = cerfStdMapper.toDto(cerfStd);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfStdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cerfStdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfStd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCerfStd() throws Exception {
        // Initialize the database
        cerfStdRepository.saveAndFlush(cerfStd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cerfStd
        restCerfStdMockMvc
            .perform(delete(ENTITY_API_URL_ID, cerfStd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cerfStdRepository.count();
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

    protected CerfStd getPersistedCerfStd(CerfStd cerfStd) {
        return cerfStdRepository.findById(cerfStd.getId()).orElseThrow();
    }

    protected void assertPersistedCerfStdToMatchAllProperties(CerfStd expectedCerfStd) {
        assertCerfStdAllPropertiesEquals(expectedCerfStd, getPersistedCerfStd(expectedCerfStd));
    }

    protected void assertPersistedCerfStdToMatchUpdatableProperties(CerfStd expectedCerfStd) {
        assertCerfStdAllUpdatablePropertiesEquals(expectedCerfStd, getPersistedCerfStd(expectedCerfStd));
    }
}
