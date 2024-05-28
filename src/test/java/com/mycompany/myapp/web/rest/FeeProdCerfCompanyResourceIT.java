package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.FeeProdCerfCompanyAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.FeeProdCerfCompany;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.repository.FeeProdCerfCompanyRepository;
import com.mycompany.myapp.service.dto.FeeProdCerfCompanyDTO;
import com.mycompany.myapp.service.mapper.FeeProdCerfCompanyMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FeeProdCerfCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeeProdCerfCompanyResourceIT {

    private static final Long DEFAULT_FEE = 1L;
    private static final Long UPDATED_FEE = 2L;
    private static final Long SMALLER_FEE = 1L - 1L;

    private static final String DEFAULT_FEE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FEE_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FEE_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FEE_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FEE_DT = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/fee-prod-cerf-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FeeProdCerfCompanyRepository feeProdCerfCompanyRepository;

    @Autowired
    private FeeProdCerfCompanyMapper feeProdCerfCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeeProdCerfCompanyMockMvc;

    private FeeProdCerfCompany feeProdCerfCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeeProdCerfCompany createEntity(EntityManager em) {
        FeeProdCerfCompany feeProdCerfCompany = new FeeProdCerfCompany().fee(DEFAULT_FEE).feeType(DEFAULT_FEE_TYPE).feeDt(DEFAULT_FEE_DT);
        return feeProdCerfCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeeProdCerfCompany createUpdatedEntity(EntityManager em) {
        FeeProdCerfCompany feeProdCerfCompany = new FeeProdCerfCompany().fee(UPDATED_FEE).feeType(UPDATED_FEE_TYPE).feeDt(UPDATED_FEE_DT);
        return feeProdCerfCompany;
    }

    @BeforeEach
    public void initTest() {
        feeProdCerfCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);
        var returnedFeeProdCerfCompanyDTO = om.readValue(
            restFeeProdCerfCompanyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feeProdCerfCompanyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FeeProdCerfCompanyDTO.class
        );

        // Validate the FeeProdCerfCompany in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFeeProdCerfCompany = feeProdCerfCompanyMapper.toEntity(returnedFeeProdCerfCompanyDTO);
        assertFeeProdCerfCompanyUpdatableFieldsEquals(
            returnedFeeProdCerfCompany,
            getPersistedFeeProdCerfCompany(returnedFeeProdCerfCompany)
        );
    }

    @Test
    @Transactional
    void createFeeProdCerfCompanyWithExistingId() throws Exception {
        // Create the FeeProdCerfCompany with an existing ID
        feeProdCerfCompany.setId(1L);
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeeProdCerfCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feeProdCerfCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompanies() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList
        restFeeProdCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feeProdCerfCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.intValue())))
            .andExpect(jsonPath("$.[*].feeType").value(hasItem(DEFAULT_FEE_TYPE)))
            .andExpect(jsonPath("$.[*].feeDt").value(hasItem(DEFAULT_FEE_DT.toString())));
    }

    @Test
    @Transactional
    void getFeeProdCerfCompany() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get the feeProdCerfCompany
        restFeeProdCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, feeProdCerfCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feeProdCerfCompany.getId().intValue()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.intValue()))
            .andExpect(jsonPath("$.feeType").value(DEFAULT_FEE_TYPE))
            .andExpect(jsonPath("$.feeDt").value(DEFAULT_FEE_DT.toString()));
    }

    @Test
    @Transactional
    void getFeeProdCerfCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        Long id = feeProdCerfCompany.getId();

        defaultFeeProdCerfCompanyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFeeProdCerfCompanyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFeeProdCerfCompanyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee equals to
        defaultFeeProdCerfCompanyFiltering("fee.equals=" + DEFAULT_FEE, "fee.equals=" + UPDATED_FEE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsInShouldWork() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee in
        defaultFeeProdCerfCompanyFiltering("fee.in=" + DEFAULT_FEE + "," + UPDATED_FEE, "fee.in=" + UPDATED_FEE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee is not null
        defaultFeeProdCerfCompanyFiltering("fee.specified=true", "fee.specified=false");
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee is greater than or equal to
        defaultFeeProdCerfCompanyFiltering("fee.greaterThanOrEqual=" + DEFAULT_FEE, "fee.greaterThanOrEqual=" + UPDATED_FEE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee is less than or equal to
        defaultFeeProdCerfCompanyFiltering("fee.lessThanOrEqual=" + DEFAULT_FEE, "fee.lessThanOrEqual=" + SMALLER_FEE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee is less than
        defaultFeeProdCerfCompanyFiltering("fee.lessThan=" + UPDATED_FEE, "fee.lessThan=" + DEFAULT_FEE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where fee is greater than
        defaultFeeProdCerfCompanyFiltering("fee.greaterThan=" + SMALLER_FEE, "fee.greaterThan=" + DEFAULT_FEE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeType equals to
        defaultFeeProdCerfCompanyFiltering("feeType.equals=" + DEFAULT_FEE_TYPE, "feeType.equals=" + UPDATED_FEE_TYPE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeType in
        defaultFeeProdCerfCompanyFiltering("feeType.in=" + DEFAULT_FEE_TYPE + "," + UPDATED_FEE_TYPE, "feeType.in=" + UPDATED_FEE_TYPE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeType is not null
        defaultFeeProdCerfCompanyFiltering("feeType.specified=true", "feeType.specified=false");
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeTypeContainsSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeType contains
        defaultFeeProdCerfCompanyFiltering("feeType.contains=" + DEFAULT_FEE_TYPE, "feeType.contains=" + UPDATED_FEE_TYPE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeType does not contain
        defaultFeeProdCerfCompanyFiltering("feeType.doesNotContain=" + UPDATED_FEE_TYPE, "feeType.doesNotContain=" + DEFAULT_FEE_TYPE);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt equals to
        defaultFeeProdCerfCompanyFiltering("feeDt.equals=" + DEFAULT_FEE_DT, "feeDt.equals=" + UPDATED_FEE_DT);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsInShouldWork() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt in
        defaultFeeProdCerfCompanyFiltering("feeDt.in=" + DEFAULT_FEE_DT + "," + UPDATED_FEE_DT, "feeDt.in=" + UPDATED_FEE_DT);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt is not null
        defaultFeeProdCerfCompanyFiltering("feeDt.specified=true", "feeDt.specified=false");
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt is greater than or equal to
        defaultFeeProdCerfCompanyFiltering("feeDt.greaterThanOrEqual=" + DEFAULT_FEE_DT, "feeDt.greaterThanOrEqual=" + UPDATED_FEE_DT);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt is less than or equal to
        defaultFeeProdCerfCompanyFiltering("feeDt.lessThanOrEqual=" + DEFAULT_FEE_DT, "feeDt.lessThanOrEqual=" + SMALLER_FEE_DT);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsLessThanSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt is less than
        defaultFeeProdCerfCompanyFiltering("feeDt.lessThan=" + UPDATED_FEE_DT, "feeDt.lessThan=" + DEFAULT_FEE_DT);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByFeeDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        // Get all the feeProdCerfCompanyList where feeDt is greater than
        defaultFeeProdCerfCompanyFiltering("feeDt.greaterThan=" + SMALLER_FEE_DT, "feeDt.greaterThan=" + DEFAULT_FEE_DT);
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByProdIsEqualToSomething() throws Exception {
        Prod prod;
        if (TestUtil.findAll(em, Prod.class).isEmpty()) {
            feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);
            prod = ProdResourceIT.createEntity(em);
        } else {
            prod = TestUtil.findAll(em, Prod.class).get(0);
        }
        em.persist(prod);
        em.flush();
        feeProdCerfCompany.setProd(prod);
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);
        Long prodId = prod.getId();
        // Get all the feeProdCerfCompanyList where prod equals to prodId
        defaultFeeProdCerfCompanyShouldBeFound("prodId.equals=" + prodId);

        // Get all the feeProdCerfCompanyList where prod equals to (prodId + 1)
        defaultFeeProdCerfCompanyShouldNotBeFound("prodId.equals=" + (prodId + 1));
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByCerfIsEqualToSomething() throws Exception {
        Cerf cerf;
        if (TestUtil.findAll(em, Cerf.class).isEmpty()) {
            feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);
            cerf = CerfResourceIT.createEntity(em);
        } else {
            cerf = TestUtil.findAll(em, Cerf.class).get(0);
        }
        em.persist(cerf);
        em.flush();
        feeProdCerfCompany.setCerf(cerf);
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);
        Long cerfId = cerf.getId();
        // Get all the feeProdCerfCompanyList where cerf equals to cerfId
        defaultFeeProdCerfCompanyShouldBeFound("cerfId.equals=" + cerfId);

        // Get all the feeProdCerfCompanyList where cerf equals to (cerfId + 1)
        defaultFeeProdCerfCompanyShouldNotBeFound("cerfId.equals=" + (cerfId + 1));
    }

    @Test
    @Transactional
    void getAllFeeProdCerfCompaniesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        feeProdCerfCompany.setCompany(company);
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);
        Long companyId = company.getId();
        // Get all the feeProdCerfCompanyList where company equals to companyId
        defaultFeeProdCerfCompanyShouldBeFound("companyId.equals=" + companyId);

        // Get all the feeProdCerfCompanyList where company equals to (companyId + 1)
        defaultFeeProdCerfCompanyShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    private void defaultFeeProdCerfCompanyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFeeProdCerfCompanyShouldBeFound(shouldBeFound);
        defaultFeeProdCerfCompanyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeeProdCerfCompanyShouldBeFound(String filter) throws Exception {
        restFeeProdCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feeProdCerfCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.intValue())))
            .andExpect(jsonPath("$.[*].feeType").value(hasItem(DEFAULT_FEE_TYPE)))
            .andExpect(jsonPath("$.[*].feeDt").value(hasItem(DEFAULT_FEE_DT.toString())));

        // Check, that the count call also returns 1
        restFeeProdCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeeProdCerfCompanyShouldNotBeFound(String filter) throws Exception {
        restFeeProdCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeeProdCerfCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeeProdCerfCompany() throws Exception {
        // Get the feeProdCerfCompany
        restFeeProdCerfCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeeProdCerfCompany() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feeProdCerfCompany
        FeeProdCerfCompany updatedFeeProdCerfCompany = feeProdCerfCompanyRepository.findById(feeProdCerfCompany.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFeeProdCerfCompany are not directly saved in db
        em.detach(updatedFeeProdCerfCompany);
        updatedFeeProdCerfCompany.fee(UPDATED_FEE).feeType(UPDATED_FEE_TYPE).feeDt(UPDATED_FEE_DT);
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(updatedFeeProdCerfCompany);

        restFeeProdCerfCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feeProdCerfCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feeProdCerfCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFeeProdCerfCompanyToMatchAllProperties(updatedFeeProdCerfCompany);
    }

    @Test
    @Transactional
    void putNonExistingFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feeProdCerfCompany.setId(longCount.incrementAndGet());

        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeProdCerfCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feeProdCerfCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feeProdCerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feeProdCerfCompany.setId(longCount.incrementAndGet());

        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeProdCerfCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feeProdCerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feeProdCerfCompany.setId(longCount.incrementAndGet());

        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeProdCerfCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feeProdCerfCompanyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeeProdCerfCompanyWithPatch() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feeProdCerfCompany using partial update
        FeeProdCerfCompany partialUpdatedFeeProdCerfCompany = new FeeProdCerfCompany();
        partialUpdatedFeeProdCerfCompany.setId(feeProdCerfCompany.getId());

        partialUpdatedFeeProdCerfCompany.feeType(UPDATED_FEE_TYPE).feeDt(UPDATED_FEE_DT);

        restFeeProdCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeeProdCerfCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeeProdCerfCompany))
            )
            .andExpect(status().isOk());

        // Validate the FeeProdCerfCompany in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeeProdCerfCompanyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFeeProdCerfCompany, feeProdCerfCompany),
            getPersistedFeeProdCerfCompany(feeProdCerfCompany)
        );
    }

    @Test
    @Transactional
    void fullUpdateFeeProdCerfCompanyWithPatch() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feeProdCerfCompany using partial update
        FeeProdCerfCompany partialUpdatedFeeProdCerfCompany = new FeeProdCerfCompany();
        partialUpdatedFeeProdCerfCompany.setId(feeProdCerfCompany.getId());

        partialUpdatedFeeProdCerfCompany.fee(UPDATED_FEE).feeType(UPDATED_FEE_TYPE).feeDt(UPDATED_FEE_DT);

        restFeeProdCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeeProdCerfCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeeProdCerfCompany))
            )
            .andExpect(status().isOk());

        // Validate the FeeProdCerfCompany in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeeProdCerfCompanyUpdatableFieldsEquals(
            partialUpdatedFeeProdCerfCompany,
            getPersistedFeeProdCerfCompany(partialUpdatedFeeProdCerfCompany)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feeProdCerfCompany.setId(longCount.incrementAndGet());

        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeProdCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feeProdCerfCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feeProdCerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feeProdCerfCompany.setId(longCount.incrementAndGet());

        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeProdCerfCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feeProdCerfCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeeProdCerfCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feeProdCerfCompany.setId(longCount.incrementAndGet());

        // Create the FeeProdCerfCompany
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO = feeProdCerfCompanyMapper.toDto(feeProdCerfCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeProdCerfCompanyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(feeProdCerfCompanyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeeProdCerfCompany in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeeProdCerfCompany() throws Exception {
        // Initialize the database
        feeProdCerfCompanyRepository.saveAndFlush(feeProdCerfCompany);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the feeProdCerfCompany
        restFeeProdCerfCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, feeProdCerfCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return feeProdCerfCompanyRepository.count();
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

    protected FeeProdCerfCompany getPersistedFeeProdCerfCompany(FeeProdCerfCompany feeProdCerfCompany) {
        return feeProdCerfCompanyRepository.findById(feeProdCerfCompany.getId()).orElseThrow();
    }

    protected void assertPersistedFeeProdCerfCompanyToMatchAllProperties(FeeProdCerfCompany expectedFeeProdCerfCompany) {
        assertFeeProdCerfCompanyAllPropertiesEquals(expectedFeeProdCerfCompany, getPersistedFeeProdCerfCompany(expectedFeeProdCerfCompany));
    }

    protected void assertPersistedFeeProdCerfCompanyToMatchUpdatableProperties(FeeProdCerfCompany expectedFeeProdCerfCompany) {
        assertFeeProdCerfCompanyAllUpdatablePropertiesEquals(
            expectedFeeProdCerfCompany,
            getPersistedFeeProdCerfCompany(expectedFeeProdCerfCompany)
        );
    }
}
