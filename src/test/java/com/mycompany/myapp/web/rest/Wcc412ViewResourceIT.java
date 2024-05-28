package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.Wcc412ViewAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Wcc412View;
import com.mycompany.myapp.repository.Wcc412ViewRepository;
import com.mycompany.myapp.service.mapper.Wcc412ViewMapper;
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
 * Integration tests for the {@link Wcc412ViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Wcc412ViewResourceIT {

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

    private static final Long DEFAULT_COUNTRY_ID = 1L;
    private static final Long UPDATED_COUNTRY_ID = 2L;
    private static final Long SMALLER_COUNTRY_ID = 1L - 1L;

    private static final String DEFAULT_PROD_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PROD_CH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROD_CH_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wcc-412-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private Wcc412ViewRepository wcc412ViewRepository;

    @Autowired
    private Wcc412ViewMapper wcc412ViewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWcc412ViewMockMvc;

    private Wcc412View wcc412View;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wcc412View createEntity(EntityManager em) {
        Wcc412View wcc412View = new Wcc412View()
            .cerfId(DEFAULT_CERF_ID)
            .countryChName(DEFAULT_COUNTRY_CH_NAME)
            .cerfNo(DEFAULT_CERF_NO)
            .cerfVer(DEFAULT_CERF_VER)
            .cerfStatus(DEFAULT_CERF_STATUS)
            .countryId(DEFAULT_COUNTRY_ID)
            .prodNo(DEFAULT_PROD_NO)
            .prodChName(DEFAULT_PROD_CH_NAME);
        return wcc412View;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wcc412View createUpdatedEntity(EntityManager em) {
        Wcc412View wcc412View = new Wcc412View()
            .cerfId(UPDATED_CERF_ID)
            .countryChName(UPDATED_COUNTRY_CH_NAME)
            .cerfNo(UPDATED_CERF_NO)
            .cerfVer(UPDATED_CERF_VER)
            .cerfStatus(UPDATED_CERF_STATUS)
            .countryId(UPDATED_COUNTRY_ID)
            .prodNo(UPDATED_PROD_NO)
            .prodChName(UPDATED_PROD_CH_NAME);
        return wcc412View;
    }

    @BeforeEach
    public void initTest() {
        wcc412View = createEntity(em);
    }

    @Test
    @Transactional
    void getAllWcc412Views() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList
        restWcc412ViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wcc412View.getId().intValue())))
            .andExpect(jsonPath("$.[*].cerfId").value(hasItem(DEFAULT_CERF_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryChName").value(hasItem(DEFAULT_COUNTRY_CH_NAME)))
            .andExpect(jsonPath("$.[*].cerfNo").value(hasItem(DEFAULT_CERF_NO)))
            .andExpect(jsonPath("$.[*].cerfVer").value(hasItem(DEFAULT_CERF_VER)))
            .andExpect(jsonPath("$.[*].cerfStatus").value(hasItem(DEFAULT_CERF_STATUS)))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].prodNo").value(hasItem(DEFAULT_PROD_NO)))
            .andExpect(jsonPath("$.[*].prodChName").value(hasItem(DEFAULT_PROD_CH_NAME)));
    }

    @Test
    @Transactional
    void getWcc412View() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get the wcc412View
        restWcc412ViewMockMvc
            .perform(get(ENTITY_API_URL_ID, wcc412View.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wcc412View.getId().intValue()))
            .andExpect(jsonPath("$.cerfId").value(DEFAULT_CERF_ID.intValue()))
            .andExpect(jsonPath("$.countryChName").value(DEFAULT_COUNTRY_CH_NAME))
            .andExpect(jsonPath("$.cerfNo").value(DEFAULT_CERF_NO))
            .andExpect(jsonPath("$.cerfVer").value(DEFAULT_CERF_VER))
            .andExpect(jsonPath("$.cerfStatus").value(DEFAULT_CERF_STATUS))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID.intValue()))
            .andExpect(jsonPath("$.prodNo").value(DEFAULT_PROD_NO))
            .andExpect(jsonPath("$.prodChName").value(DEFAULT_PROD_CH_NAME));
    }

    @Test
    @Transactional
    void getWcc412ViewsByIdFiltering() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        Long id = wcc412View.getId();

        defaultWcc412ViewFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWcc412ViewFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWcc412ViewFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId equals to
        defaultWcc412ViewFiltering("cerfId.equals=" + DEFAULT_CERF_ID, "cerfId.equals=" + UPDATED_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId in
        defaultWcc412ViewFiltering("cerfId.in=" + DEFAULT_CERF_ID + "," + UPDATED_CERF_ID, "cerfId.in=" + UPDATED_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId is not null
        defaultWcc412ViewFiltering("cerfId.specified=true", "cerfId.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId is greater than or equal to
        defaultWcc412ViewFiltering("cerfId.greaterThanOrEqual=" + DEFAULT_CERF_ID, "cerfId.greaterThanOrEqual=" + UPDATED_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId is less than or equal to
        defaultWcc412ViewFiltering("cerfId.lessThanOrEqual=" + DEFAULT_CERF_ID, "cerfId.lessThanOrEqual=" + SMALLER_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsLessThanSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId is less than
        defaultWcc412ViewFiltering("cerfId.lessThan=" + UPDATED_CERF_ID, "cerfId.lessThan=" + DEFAULT_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfId is greater than
        defaultWcc412ViewFiltering("cerfId.greaterThan=" + SMALLER_CERF_ID, "cerfId.greaterThan=" + DEFAULT_CERF_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryChName equals to
        defaultWcc412ViewFiltering("countryChName.equals=" + DEFAULT_COUNTRY_CH_NAME, "countryChName.equals=" + UPDATED_COUNTRY_CH_NAME);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryChNameIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryChName in
        defaultWcc412ViewFiltering(
            "countryChName.in=" + DEFAULT_COUNTRY_CH_NAME + "," + UPDATED_COUNTRY_CH_NAME,
            "countryChName.in=" + UPDATED_COUNTRY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryChName is not null
        defaultWcc412ViewFiltering("countryChName.specified=true", "countryChName.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryChNameContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryChName contains
        defaultWcc412ViewFiltering(
            "countryChName.contains=" + DEFAULT_COUNTRY_CH_NAME,
            "countryChName.contains=" + UPDATED_COUNTRY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryChNameNotContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryChName does not contain
        defaultWcc412ViewFiltering(
            "countryChName.doesNotContain=" + UPDATED_COUNTRY_CH_NAME,
            "countryChName.doesNotContain=" + DEFAULT_COUNTRY_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfNoIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfNo equals to
        defaultWcc412ViewFiltering("cerfNo.equals=" + DEFAULT_CERF_NO, "cerfNo.equals=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfNoIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfNo in
        defaultWcc412ViewFiltering("cerfNo.in=" + DEFAULT_CERF_NO + "," + UPDATED_CERF_NO, "cerfNo.in=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfNo is not null
        defaultWcc412ViewFiltering("cerfNo.specified=true", "cerfNo.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfNoContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfNo contains
        defaultWcc412ViewFiltering("cerfNo.contains=" + DEFAULT_CERF_NO, "cerfNo.contains=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfNoNotContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfNo does not contain
        defaultWcc412ViewFiltering("cerfNo.doesNotContain=" + UPDATED_CERF_NO, "cerfNo.doesNotContain=" + DEFAULT_CERF_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfVerIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfVer equals to
        defaultWcc412ViewFiltering("cerfVer.equals=" + DEFAULT_CERF_VER, "cerfVer.equals=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfVerIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfVer in
        defaultWcc412ViewFiltering("cerfVer.in=" + DEFAULT_CERF_VER + "," + UPDATED_CERF_VER, "cerfVer.in=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfVerIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfVer is not null
        defaultWcc412ViewFiltering("cerfVer.specified=true", "cerfVer.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfVerContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfVer contains
        defaultWcc412ViewFiltering("cerfVer.contains=" + DEFAULT_CERF_VER, "cerfVer.contains=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfVerNotContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfVer does not contain
        defaultWcc412ViewFiltering("cerfVer.doesNotContain=" + UPDATED_CERF_VER, "cerfVer.doesNotContain=" + DEFAULT_CERF_VER);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfStatus equals to
        defaultWcc412ViewFiltering("cerfStatus.equals=" + DEFAULT_CERF_STATUS, "cerfStatus.equals=" + UPDATED_CERF_STATUS);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfStatusIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfStatus in
        defaultWcc412ViewFiltering(
            "cerfStatus.in=" + DEFAULT_CERF_STATUS + "," + UPDATED_CERF_STATUS,
            "cerfStatus.in=" + UPDATED_CERF_STATUS
        );
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfStatus is not null
        defaultWcc412ViewFiltering("cerfStatus.specified=true", "cerfStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfStatusContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfStatus contains
        defaultWcc412ViewFiltering("cerfStatus.contains=" + DEFAULT_CERF_STATUS, "cerfStatus.contains=" + UPDATED_CERF_STATUS);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCerfStatusNotContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where cerfStatus does not contain
        defaultWcc412ViewFiltering("cerfStatus.doesNotContain=" + UPDATED_CERF_STATUS, "cerfStatus.doesNotContain=" + DEFAULT_CERF_STATUS);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId equals to
        defaultWcc412ViewFiltering("countryId.equals=" + DEFAULT_COUNTRY_ID, "countryId.equals=" + UPDATED_COUNTRY_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId in
        defaultWcc412ViewFiltering("countryId.in=" + DEFAULT_COUNTRY_ID + "," + UPDATED_COUNTRY_ID, "countryId.in=" + UPDATED_COUNTRY_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId is not null
        defaultWcc412ViewFiltering("countryId.specified=true", "countryId.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId is greater than or equal to
        defaultWcc412ViewFiltering(
            "countryId.greaterThanOrEqual=" + DEFAULT_COUNTRY_ID,
            "countryId.greaterThanOrEqual=" + UPDATED_COUNTRY_ID
        );
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId is less than or equal to
        defaultWcc412ViewFiltering("countryId.lessThanOrEqual=" + DEFAULT_COUNTRY_ID, "countryId.lessThanOrEqual=" + SMALLER_COUNTRY_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId is less than
        defaultWcc412ViewFiltering("countryId.lessThan=" + UPDATED_COUNTRY_ID, "countryId.lessThan=" + DEFAULT_COUNTRY_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByCountryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where countryId is greater than
        defaultWcc412ViewFiltering("countryId.greaterThan=" + SMALLER_COUNTRY_ID, "countryId.greaterThan=" + DEFAULT_COUNTRY_ID);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdNoIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodNo equals to
        defaultWcc412ViewFiltering("prodNo.equals=" + DEFAULT_PROD_NO, "prodNo.equals=" + UPDATED_PROD_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdNoIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodNo in
        defaultWcc412ViewFiltering("prodNo.in=" + DEFAULT_PROD_NO + "," + UPDATED_PROD_NO, "prodNo.in=" + UPDATED_PROD_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodNo is not null
        defaultWcc412ViewFiltering("prodNo.specified=true", "prodNo.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdNoContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodNo contains
        defaultWcc412ViewFiltering("prodNo.contains=" + DEFAULT_PROD_NO, "prodNo.contains=" + UPDATED_PROD_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdNoNotContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodNo does not contain
        defaultWcc412ViewFiltering("prodNo.doesNotContain=" + UPDATED_PROD_NO, "prodNo.doesNotContain=" + DEFAULT_PROD_NO);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodChName equals to
        defaultWcc412ViewFiltering("prodChName.equals=" + DEFAULT_PROD_CH_NAME, "prodChName.equals=" + UPDATED_PROD_CH_NAME);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdChNameIsInShouldWork() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodChName in
        defaultWcc412ViewFiltering(
            "prodChName.in=" + DEFAULT_PROD_CH_NAME + "," + UPDATED_PROD_CH_NAME,
            "prodChName.in=" + UPDATED_PROD_CH_NAME
        );
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodChName is not null
        defaultWcc412ViewFiltering("prodChName.specified=true", "prodChName.specified=false");
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdChNameContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodChName contains
        defaultWcc412ViewFiltering("prodChName.contains=" + DEFAULT_PROD_CH_NAME, "prodChName.contains=" + UPDATED_PROD_CH_NAME);
    }

    @Test
    @Transactional
    void getAllWcc412ViewsByProdChNameNotContainsSomething() throws Exception {
        // Initialize the database
        wcc412ViewRepository.saveAndFlush(wcc412View);

        // Get all the wcc412ViewList where prodChName does not contain
        defaultWcc412ViewFiltering(
            "prodChName.doesNotContain=" + UPDATED_PROD_CH_NAME,
            "prodChName.doesNotContain=" + DEFAULT_PROD_CH_NAME
        );
    }

    private void defaultWcc412ViewFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultWcc412ViewShouldBeFound(shouldBeFound);
        defaultWcc412ViewShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWcc412ViewShouldBeFound(String filter) throws Exception {
        restWcc412ViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wcc412View.getId().intValue())))
            .andExpect(jsonPath("$.[*].cerfId").value(hasItem(DEFAULT_CERF_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryChName").value(hasItem(DEFAULT_COUNTRY_CH_NAME)))
            .andExpect(jsonPath("$.[*].cerfNo").value(hasItem(DEFAULT_CERF_NO)))
            .andExpect(jsonPath("$.[*].cerfVer").value(hasItem(DEFAULT_CERF_VER)))
            .andExpect(jsonPath("$.[*].cerfStatus").value(hasItem(DEFAULT_CERF_STATUS)))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].prodNo").value(hasItem(DEFAULT_PROD_NO)))
            .andExpect(jsonPath("$.[*].prodChName").value(hasItem(DEFAULT_PROD_CH_NAME)));

        // Check, that the count call also returns 1
        restWcc412ViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWcc412ViewShouldNotBeFound(String filter) throws Exception {
        restWcc412ViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWcc412ViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWcc412View() throws Exception {
        // Get the wcc412View
        restWcc412ViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    protected long getRepositoryCount() {
        return wcc412ViewRepository.count();
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

    protected Wcc412View getPersistedWcc412View(Wcc412View wcc412View) {
        return wcc412ViewRepository.findById(wcc412View.getId()).orElseThrow();
    }

    protected void assertPersistedWcc412ViewToMatchAllProperties(Wcc412View expectedWcc412View) {
        assertWcc412ViewAllPropertiesEquals(expectedWcc412View, getPersistedWcc412View(expectedWcc412View));
    }

    protected void assertPersistedWcc412ViewToMatchUpdatableProperties(Wcc412View expectedWcc412View) {
        assertWcc412ViewAllUpdatablePropertiesEquals(expectedWcc412View, getPersistedWcc412View(expectedWcc412View));
    }
}
