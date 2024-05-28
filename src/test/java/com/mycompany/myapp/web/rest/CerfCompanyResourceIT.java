package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CerfCompanyAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfCompany;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.repository.CerfCompanyRepository;
import com.mycompany.myapp.service.dto.CerfCompanyDTO;
import com.mycompany.myapp.service.mapper.CerfCompanyMapper;
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
 * Integration tests for the {@link CerfCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CerfCompanyResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cerf-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CerfCompanyRepository cerfCompanyRepository;

    @Autowired
    private CerfCompanyMapper cerfCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCerfCompanyMockMvc;

    private CerfCompany cerfCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfCompany createEntity(EntityManager em) {
        CerfCompany cerfCompany = new CerfCompany().relType(DEFAULT_REL_TYPE);
        return cerfCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfCompany createUpdatedEntity(EntityManager em) {
        CerfCompany cerfCompany = new CerfCompany().relType(UPDATED_REL_TYPE);
        return cerfCompany;
    }

    @BeforeEach
    public void initTest() {
        cerfCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createCerfCompany() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);
        var returnedCerfCompanyDTO = om.readValue(
            restCerfCompanyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfCompanyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CerfCompanyDTO.class
        );

        // Validate the CerfCompany in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCerfCompany = cerfCompanyMapper.toEntity(returnedCerfCompanyDTO);
        assertCerfCompanyUpdatableFieldsEquals(returnedCerfCompany, getPersistedCerfCompany(returnedCerfCompany));
    }

    @Test
    @Transactional
    void createCerfCompanyWithExistingId() throws Exception {
        // Create the CerfCompany with an existing ID
        cerfCompany.setId(1L);
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCerfCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCerfCompanies() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get all the cerfCompanyList
        restCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCerfCompany() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get the cerfCompany
        restCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, cerfCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cerfCompany.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCerfCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        Long id = cerfCompany.getId();

        defaultCerfCompanyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCerfCompanyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCerfCompanyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get all the cerfCompanyList where relType equals to
        defaultCerfCompanyFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get all the cerfCompanyList where relType in
        defaultCerfCompanyFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get all the cerfCompanyList where relType is not null
        defaultCerfCompanyFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get all the cerfCompanyList where relType contains
        defaultCerfCompanyFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        // Get all the cerfCompanyList where relType does not contain
        defaultCerfCompanyFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByCerfIsEqualToSomething() throws Exception {
        Cerf cerf;
        if (TestUtil.findAll(em, Cerf.class).isEmpty()) {
            cerfCompanyRepository.saveAndFlush(cerfCompany);
            cerf = CerfResourceIT.createEntity(em);
        } else {
            cerf = TestUtil.findAll(em, Cerf.class).get(0);
        }
        em.persist(cerf);
        em.flush();
        cerfCompany.setCerf(cerf);
        cerfCompanyRepository.saveAndFlush(cerfCompany);
        Long cerfId = cerf.getId();
        // Get all the cerfCompanyList where cerf equals to cerfId
        defaultCerfCompanyShouldBeFound("cerfId.equals=" + cerfId);

        // Get all the cerfCompanyList where cerf equals to (cerfId + 1)
        defaultCerfCompanyShouldNotBeFound("cerfId.equals=" + (cerfId + 1));
    }

    @Test
    @Transactional
    void getAllCerfCompaniesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            cerfCompanyRepository.saveAndFlush(cerfCompany);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        cerfCompany.setCompany(company);
        cerfCompanyRepository.saveAndFlush(cerfCompany);
        Long companyId = company.getId();
        // Get all the cerfCompanyList where company equals to companyId
        defaultCerfCompanyShouldBeFound("companyId.equals=" + companyId);

        // Get all the cerfCompanyList where company equals to (companyId + 1)
        defaultCerfCompanyShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    private void defaultCerfCompanyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCerfCompanyShouldBeFound(shouldBeFound);
        defaultCerfCompanyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCerfCompanyShouldBeFound(String filter) throws Exception {
        restCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCerfCompanyShouldNotBeFound(String filter) throws Exception {
        restCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCerfCompany() throws Exception {
        // Get the cerfCompany
        restCerfCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCerfCompany() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfCompany
        CerfCompany updatedCerfCompany = cerfCompanyRepository.findById(cerfCompany.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCerfCompany are not directly saved in db
        em.detach(updatedCerfCompany);
        updatedCerfCompany.relType(UPDATED_REL_TYPE);
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(updatedCerfCompany);

        restCerfCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCerfCompanyToMatchAllProperties(updatedCerfCompany);
    }

    @Test
    @Transactional
    void putNonExistingCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfCompany.setId(longCount.incrementAndGet());

        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfCompany.setId(longCount.incrementAndGet());

        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfCompany.setId(longCount.incrementAndGet());

        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfCompanyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCerfCompanyWithPatch() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfCompany using partial update
        CerfCompany partialUpdatedCerfCompany = new CerfCompany();
        partialUpdatedCerfCompany.setId(cerfCompany.getId());

        partialUpdatedCerfCompany.relType(UPDATED_REL_TYPE);

        restCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfCompany))
            )
            .andExpect(status().isOk());

        // Validate the CerfCompany in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfCompanyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCerfCompany, cerfCompany),
            getPersistedCerfCompany(cerfCompany)
        );
    }

    @Test
    @Transactional
    void fullUpdateCerfCompanyWithPatch() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfCompany using partial update
        CerfCompany partialUpdatedCerfCompany = new CerfCompany();
        partialUpdatedCerfCompany.setId(cerfCompany.getId());

        partialUpdatedCerfCompany.relType(UPDATED_REL_TYPE);

        restCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfCompany))
            )
            .andExpect(status().isOk());

        // Validate the CerfCompany in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfCompanyUpdatableFieldsEquals(partialUpdatedCerfCompany, getPersistedCerfCompany(partialUpdatedCerfCompany));
    }

    @Test
    @Transactional
    void patchNonExistingCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfCompany.setId(longCount.incrementAndGet());

        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cerfCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfCompany.setId(longCount.incrementAndGet());

        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfCompany.setId(longCount.incrementAndGet());

        // Create the CerfCompany
        CerfCompanyDTO cerfCompanyDTO = cerfCompanyMapper.toDto(cerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfCompanyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cerfCompanyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCerfCompany() throws Exception {
        // Initialize the database
        cerfCompanyRepository.saveAndFlush(cerfCompany);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cerfCompany
        restCerfCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, cerfCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cerfCompanyRepository.count();
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

    protected CerfCompany getPersistedCerfCompany(CerfCompany cerfCompany) {
        return cerfCompanyRepository.findById(cerfCompany.getId()).orElseThrow();
    }

    protected void assertPersistedCerfCompanyToMatchAllProperties(CerfCompany expectedCerfCompany) {
        assertCerfCompanyAllPropertiesEquals(expectedCerfCompany, getPersistedCerfCompany(expectedCerfCompany));
    }

    protected void assertPersistedCerfCompanyToMatchUpdatableProperties(CerfCompany expectedCerfCompany) {
        assertCerfCompanyAllUpdatablePropertiesEquals(expectedCerfCompany, getPersistedCerfCompany(expectedCerfCompany));
    }
}
