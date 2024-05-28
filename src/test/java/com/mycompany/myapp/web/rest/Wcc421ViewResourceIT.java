package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.Wcc421ViewAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Wcc421View;
import com.mycompany.myapp.repository.Wcc421ViewRepository;
import com.mycompany.myapp.service.mapper.Wcc421ViewMapper;
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
 * Integration tests for the {@link Wcc421ViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Wcc421ViewResourceIT {

    private static final Long DEFAULT_CERF_ID = 1L;
    private static final Long UPDATED_CERF_ID = 2L;
    private static final Long SMALLER_CERF_ID = 1L - 1L;

    private static final String DEFAULT_COUNTRY_CH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CERF_NO = "AAAAAAAAAA";
    private static final String UPDATED_CERF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CERF_VER = "AAAAAAAAAA";
    private static final String UPDATED_CERF_VER = "BBBBBBBBBB";

    private static final String DEFAULT_CERF_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CERF_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wcc-421-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private Wcc421ViewRepository wcc421ViewRepository;

    @Autowired
    private Wcc421ViewMapper wcc421ViewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWcc421ViewMockMvc;

    private Wcc421View wcc421View;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wcc421View createEntity(EntityManager em) {
        Wcc421View wcc421View = new Wcc421View()
            .cerfId(DEFAULT_CERF_ID)
            .countryChName(DEFAULT_COUNTRY_CH_NAME)
            .cerfNo(DEFAULT_CERF_NO)
            .cerfVer(DEFAULT_CERF_VER)
            .cerfStatus(DEFAULT_CERF_STATUS)
            .companyChName(DEFAULT_COMPANY_CH_NAME)
            .relType(DEFAULT_REL_TYPE);
        return wcc421View;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wcc421View createUpdatedEntity(EntityManager em) {
        Wcc421View wcc421View = new Wcc421View()
            .cerfId(UPDATED_CERF_ID)
            .countryChName(UPDATED_COUNTRY_CH_NAME)
            .cerfNo(UPDATED_CERF_NO)
            .cerfVer(UPDATED_CERF_VER)
            .cerfStatus(UPDATED_CERF_STATUS)
            .companyChName(UPDATED_COMPANY_CH_NAME)
            .relType(UPDATED_REL_TYPE);
        return wcc421View;
    }

    @BeforeEach
    public void initTest() {
        wcc421View = createEntity(em);
    }

    @Test
    @Transactional
    void getAllWcc421Views() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList
        restWcc421ViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wcc421View.getId().intValue())))
            .andExpect(jsonPath("$.[*].cerfId").value(hasItem(DEFAULT_CERF_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryChName").value(hasItem(DEFAULT_COUNTRY_CH_NAME)))
            .andExpect(jsonPath("$.[*].cerfNo").value(hasItem(DEFAULT_CERF_NO)))
            .andExpect(jsonPath("$.[*].cerfVer").value(hasItem(DEFAULT_CERF_VER)))
            .andExpect(jsonPath("$.[*].cerfStatus").value(hasItem(DEFAULT_CERF_STATUS)))
            .andExpect(jsonPath("$.[*].companyChName").value(hasItem(DEFAULT_COMPANY_CH_NAME)))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getWcc421View() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get the wcc421View
        restWcc421ViewMockMvc
            .perform(get(ENTITY_API_URL_ID, wcc421View.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wcc421View.getId().intValue()))
            .andExpect(jsonPath("$.cerfId").value(DEFAULT_CERF_ID.intValue()))
            .andExpect(jsonPath("$.countryChName").value(DEFAULT_COUNTRY_CH_NAME))
            .andExpect(jsonPath("$.cerfNo").value(DEFAULT_CERF_NO))
            .andExpect(jsonPath("$.cerfVer").value(DEFAULT_CERF_VER))
            .andExpect(jsonPath("$.cerfStatus").value(DEFAULT_CERF_STATUS))
            .andExpect(jsonPath("$.companyChName").value(DEFAULT_COMPANY_CH_NAME))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getWcc421ViewsByIdFiltering() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        Long id = wcc421View.getId();

        defaultWcc421ViewFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWcc421ViewFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWcc421ViewFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId equals to
        defaultWcc421ViewFiltering("cerfId.equals=" + DEFAULT_CERF_ID, "cerfId.equals=" + UPDATED_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId in
        defaultWcc421ViewFiltering("cerfId.in=" + DEFAULT_CERF_ID + "," + UPDATED_CERF_ID, "cerfId.in=" + UPDATED_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId is not null
        defaultWcc421ViewFiltering("cerfId.specified=true", "cerfId.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId is greater than or equal to
        defaultWcc421ViewFiltering("cerfId.greaterThanOrEqual=" + DEFAULT_CERF_ID, "cerfId.greaterThanOrEqual=" + UPDATED_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId is less than or equal to
        defaultWcc421ViewFiltering("cerfId.lessThanOrEqual=" + DEFAULT_CERF_ID, "cerfId.lessThanOrEqual=" + SMALLER_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsLessThanSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId is less than
        defaultWcc421ViewFiltering("cerfId.lessThan=" + UPDATED_CERF_ID, "cerfId.lessThan=" + DEFAULT_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfId is greater than
        defaultWcc421ViewFiltering("cerfId.greaterThan=" + SMALLER_CERF_ID, "cerfId.greaterThan=" + DEFAULT_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCountryChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where countryChName equals to
        defaultWcc421ViewFiltering("countryChName.equals=" + DEFAULT_COUNTRY_CH_NAME, "countryChName.equals=" + UPDATED_COUNTRY_CH_NAME);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCountryChNameIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where countryChName in
        defaultWcc421ViewFiltering(
            "countryChName.in=" + DEFAULT_COUNTRY_CH_NAME + "," + UPDATED_COUNTRY_CH_NAME,
            "countryChName.in=" + UPDATED_COUNTRY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCountryChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where countryChName is not null
        defaultWcc421ViewFiltering("countryChName.specified=true", "countryChName.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCountryChNameContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where countryChName contains
        defaultWcc421ViewFiltering(
            "countryChName.contains=" + DEFAULT_COUNTRY_CH_NAME,
            "countryChName.contains=" + UPDATED_COUNTRY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCountryChNameNotContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where countryChName does not contain
        defaultWcc421ViewFiltering(
            "countryChName.doesNotContain=" + UPDATED_COUNTRY_CH_NAME,
            "countryChName.doesNotContain=" + DEFAULT_COUNTRY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfNoIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfNo equals to
        defaultWcc421ViewFiltering("cerfNo.equals=" + DEFAULT_CERF_NO, "cerfNo.equals=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfNoIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfNo in
        defaultWcc421ViewFiltering("cerfNo.in=" + DEFAULT_CERF_NO + "," + UPDATED_CERF_NO, "cerfNo.in=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfNo is not null
        defaultWcc421ViewFiltering("cerfNo.specified=true", "cerfNo.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfNoContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfNo contains
        defaultWcc421ViewFiltering("cerfNo.contains=" + DEFAULT_CERF_NO, "cerfNo.contains=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfNoNotContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfNo does not contain
        defaultWcc421ViewFiltering("cerfNo.doesNotContain=" + UPDATED_CERF_NO, "cerfNo.doesNotContain=" + DEFAULT_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfVerIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfVer equals to
        defaultWcc421ViewFiltering("cerfVer.equals=" + DEFAULT_CERF_VER, "cerfVer.equals=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfVerIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfVer in
        defaultWcc421ViewFiltering("cerfVer.in=" + DEFAULT_CERF_VER + "," + UPDATED_CERF_VER, "cerfVer.in=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfVerIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfVer is not null
        defaultWcc421ViewFiltering("cerfVer.specified=true", "cerfVer.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfVerContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfVer contains
        defaultWcc421ViewFiltering("cerfVer.contains=" + DEFAULT_CERF_VER, "cerfVer.contains=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfVerNotContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfVer does not contain
        defaultWcc421ViewFiltering("cerfVer.doesNotContain=" + UPDATED_CERF_VER, "cerfVer.doesNotContain=" + DEFAULT_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfStatus equals to
        defaultWcc421ViewFiltering("cerfStatus.equals=" + DEFAULT_CERF_STATUS, "cerfStatus.equals=" + UPDATED_CERF_STATUS);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfStatusIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfStatus in
        defaultWcc421ViewFiltering(
            "cerfStatus.in=" + DEFAULT_CERF_STATUS + "," + UPDATED_CERF_STATUS,
            "cerfStatus.in=" + UPDATED_CERF_STATUS
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfStatus is not null
        defaultWcc421ViewFiltering("cerfStatus.specified=true", "cerfStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfStatusContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfStatus contains
        defaultWcc421ViewFiltering("cerfStatus.contains=" + DEFAULT_CERF_STATUS, "cerfStatus.contains=" + UPDATED_CERF_STATUS);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCerfStatusNotContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where cerfStatus does not contain
        defaultWcc421ViewFiltering("cerfStatus.doesNotContain=" + UPDATED_CERF_STATUS, "cerfStatus.doesNotContain=" + DEFAULT_CERF_STATUS);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCompanyChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where companyChName equals to
        defaultWcc421ViewFiltering("companyChName.equals=" + DEFAULT_COMPANY_CH_NAME, "companyChName.equals=" + UPDATED_COMPANY_CH_NAME);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCompanyChNameIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where companyChName in
        defaultWcc421ViewFiltering(
            "companyChName.in=" + DEFAULT_COMPANY_CH_NAME + "," + UPDATED_COMPANY_CH_NAME,
            "companyChName.in=" + UPDATED_COMPANY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCompanyChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where companyChName is not null
        defaultWcc421ViewFiltering("companyChName.specified=true", "companyChName.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCompanyChNameContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where companyChName contains
        defaultWcc421ViewFiltering(
            "companyChName.contains=" + DEFAULT_COMPANY_CH_NAME,
            "companyChName.contains=" + UPDATED_COMPANY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByCompanyChNameNotContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where companyChName does not contain
        defaultWcc421ViewFiltering(
            "companyChName.doesNotContain=" + UPDATED_COMPANY_CH_NAME,
            "companyChName.doesNotContain=" + DEFAULT_COMPANY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where relType equals to
        defaultWcc421ViewFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where relType in
        defaultWcc421ViewFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where relType is not null
        defaultWcc421ViewFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where relType contains
        defaultWcc421ViewFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllWcc421ViewsByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        wcc421ViewRepository.saveAndFlush(wcc421View);

        // Get all the wcc421ViewList where relType does not contain
        defaultWcc421ViewFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    private void defaultWcc421ViewFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultWcc421ViewShouldBeFound(shouldBeFound);
        defaultWcc421ViewShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWcc421ViewShouldBeFound(String filter) throws Exception {
        restWcc421ViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wcc421View.getId().intValue())))
            .andExpect(jsonPath("$.[*].cerfId").value(hasItem(DEFAULT_CERF_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryChName").value(hasItem(DEFAULT_COUNTRY_CH_NAME)))
            .andExpect(jsonPath("$.[*].cerfNo").value(hasItem(DEFAULT_CERF_NO)))
            .andExpect(jsonPath("$.[*].cerfVer").value(hasItem(DEFAULT_CERF_VER)))
            .andExpect(jsonPath("$.[*].cerfStatus").value(hasItem(DEFAULT_CERF_STATUS)))
            .andExpect(jsonPath("$.[*].companyChName").value(hasItem(DEFAULT_COMPANY_CH_NAME)))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restWcc421ViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWcc421ViewShouldNotBeFound(String filter) throws Exception {
        restWcc421ViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWcc421ViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWcc421View() throws Exception {
        // Get the wcc421View
        restWcc421ViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    protected long getRepositoryCount() {
        return wcc421ViewRepository.count();
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

    protected Wcc421View getPersistedWcc421View(Wcc421View wcc421View) {
        return wcc421ViewRepository.findById(wcc421View.getId()).orElseThrow();
    }

    protected void assertPersistedWcc421ViewToMatchAllProperties(Wcc421View expectedWcc421View) {
        assertWcc421ViewAllPropertiesEquals(expectedWcc421View, getPersistedWcc421View(expectedWcc421View));
    }

    protected void assertPersistedWcc421ViewToMatchUpdatableProperties(Wcc421View expectedWcc421View) {
        assertWcc421ViewAllUpdatablePropertiesEquals(expectedWcc421View, getPersistedWcc421View(expectedWcc421View));
    }
}
