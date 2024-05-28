package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.StdAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.repository.StdRepository;
import com.mycompany.myapp.service.dto.StdDTO;
import com.mycompany.myapp.service.mapper.StdMapper;
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
 * Integration tests for the {@link StdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StdResourceIT {

    private static final String DEFAULT_STD_NO = "AAAAAAAAAA";
    private static final String UPDATED_STD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_STD_VER = "AAAAAAAAAA";
    private static final String UPDATED_STD_VER = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSU_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSU_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ISSU_DT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_EXP_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXP_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXP_DT = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/stds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StdRepository stdRepository;

    @Autowired
    private StdMapper stdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStdMockMvc;

    private Std std;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Std createEntity(EntityManager em) {
        Std std = new Std()
            .stdNo(DEFAULT_STD_NO)
            .stdVer(DEFAULT_STD_VER)
            .enName(DEFAULT_EN_NAME)
            .chName(DEFAULT_CH_NAME)
            .status(DEFAULT_STATUS)
            .issuDt(DEFAULT_ISSU_DT)
            .expDt(DEFAULT_EXP_DT);
        return std;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Std createUpdatedEntity(EntityManager em) {
        Std std = new Std()
            .stdNo(UPDATED_STD_NO)
            .stdVer(UPDATED_STD_VER)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .status(UPDATED_STATUS)
            .issuDt(UPDATED_ISSU_DT)
            .expDt(UPDATED_EXP_DT);
        return std;
    }

    @BeforeEach
    public void initTest() {
        std = createEntity(em);
    }

    @Test
    @Transactional
    void createStd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);
        var returnedStdDTO = om.readValue(
            restStdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stdDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StdDTO.class
        );

        // Validate the Std in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStd = stdMapper.toEntity(returnedStdDTO);
        assertStdUpdatableFieldsEquals(returnedStd, getPersistedStd(returnedStd));
    }

    @Test
    @Transactional
    void createStdWithExistingId() throws Exception {
        // Create the Std with an existing ID
        std.setId(1L);
        StdDTO stdDTO = stdMapper.toDto(std);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStds() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList
        restStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(std.getId().intValue())))
            .andExpect(jsonPath("$.[*].stdNo").value(hasItem(DEFAULT_STD_NO)))
            .andExpect(jsonPath("$.[*].stdVer").value(hasItem(DEFAULT_STD_VER)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)))
            .andExpect(jsonPath("$.[*].chName").value(hasItem(DEFAULT_CH_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].issuDt").value(hasItem(DEFAULT_ISSU_DT.toString())))
            .andExpect(jsonPath("$.[*].expDt").value(hasItem(DEFAULT_EXP_DT.toString())));
    }

    @Test
    @Transactional
    void getStd() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get the std
        restStdMockMvc
            .perform(get(ENTITY_API_URL_ID, std.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(std.getId().intValue()))
            .andExpect(jsonPath("$.stdNo").value(DEFAULT_STD_NO))
            .andExpect(jsonPath("$.stdVer").value(DEFAULT_STD_VER))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME))
            .andExpect(jsonPath("$.chName").value(DEFAULT_CH_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.issuDt").value(DEFAULT_ISSU_DT.toString()))
            .andExpect(jsonPath("$.expDt").value(DEFAULT_EXP_DT.toString()));
    }

    @Test
    @Transactional
    void getStdsByIdFiltering() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        Long id = std.getId();

        defaultStdFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStdFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStdFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStdsByStdNoIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdNo equals to
        defaultStdFiltering("stdNo.equals=" + DEFAULT_STD_NO, "stdNo.equals=" + UPDATED_STD_NO);
    }

    @Test
    @Transactional
    void getAllStdsByStdNoIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdNo in
        defaultStdFiltering("stdNo.in=" + DEFAULT_STD_NO + "," + UPDATED_STD_NO, "stdNo.in=" + UPDATED_STD_NO);
    }

    @Test
    @Transactional
    void getAllStdsByStdNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdNo is not null
        defaultStdFiltering("stdNo.specified=true", "stdNo.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByStdNoContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdNo contains
        defaultStdFiltering("stdNo.contains=" + DEFAULT_STD_NO, "stdNo.contains=" + UPDATED_STD_NO);
    }

    @Test
    @Transactional
    void getAllStdsByStdNoNotContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdNo does not contain
        defaultStdFiltering("stdNo.doesNotContain=" + UPDATED_STD_NO, "stdNo.doesNotContain=" + DEFAULT_STD_NO);
    }

    @Test
    @Transactional
    void getAllStdsByStdVerIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdVer equals to
        defaultStdFiltering("stdVer.equals=" + DEFAULT_STD_VER, "stdVer.equals=" + UPDATED_STD_VER);
    }

    @Test
    @Transactional
    void getAllStdsByStdVerIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdVer in
        defaultStdFiltering("stdVer.in=" + DEFAULT_STD_VER + "," + UPDATED_STD_VER, "stdVer.in=" + UPDATED_STD_VER);
    }

    @Test
    @Transactional
    void getAllStdsByStdVerIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdVer is not null
        defaultStdFiltering("stdVer.specified=true", "stdVer.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByStdVerContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdVer contains
        defaultStdFiltering("stdVer.contains=" + DEFAULT_STD_VER, "stdVer.contains=" + UPDATED_STD_VER);
    }

    @Test
    @Transactional
    void getAllStdsByStdVerNotContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where stdVer does not contain
        defaultStdFiltering("stdVer.doesNotContain=" + UPDATED_STD_VER, "stdVer.doesNotContain=" + DEFAULT_STD_VER);
    }

    @Test
    @Transactional
    void getAllStdsByEnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where enName equals to
        defaultStdFiltering("enName.equals=" + DEFAULT_EN_NAME, "enName.equals=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByEnNameIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where enName in
        defaultStdFiltering("enName.in=" + DEFAULT_EN_NAME + "," + UPDATED_EN_NAME, "enName.in=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByEnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where enName is not null
        defaultStdFiltering("enName.specified=true", "enName.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByEnNameContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where enName contains
        defaultStdFiltering("enName.contains=" + DEFAULT_EN_NAME, "enName.contains=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByEnNameNotContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where enName does not contain
        defaultStdFiltering("enName.doesNotContain=" + UPDATED_EN_NAME, "enName.doesNotContain=" + DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where chName equals to
        defaultStdFiltering("chName.equals=" + DEFAULT_CH_NAME, "chName.equals=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByChNameIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where chName in
        defaultStdFiltering("chName.in=" + DEFAULT_CH_NAME + "," + UPDATED_CH_NAME, "chName.in=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where chName is not null
        defaultStdFiltering("chName.specified=true", "chName.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByChNameContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where chName contains
        defaultStdFiltering("chName.contains=" + DEFAULT_CH_NAME, "chName.contains=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByChNameNotContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where chName does not contain
        defaultStdFiltering("chName.doesNotContain=" + UPDATED_CH_NAME, "chName.doesNotContain=" + DEFAULT_CH_NAME);
    }

    @Test
    @Transactional
    void getAllStdsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where status equals to
        defaultStdFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStdsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where status in
        defaultStdFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStdsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where status is not null
        defaultStdFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByStatusContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where status contains
        defaultStdFiltering("status.contains=" + DEFAULT_STATUS, "status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStdsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where status does not contain
        defaultStdFiltering("status.doesNotContain=" + UPDATED_STATUS, "status.doesNotContain=" + DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt equals to
        defaultStdFiltering("issuDt.equals=" + DEFAULT_ISSU_DT, "issuDt.equals=" + UPDATED_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt in
        defaultStdFiltering("issuDt.in=" + DEFAULT_ISSU_DT + "," + UPDATED_ISSU_DT, "issuDt.in=" + UPDATED_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt is not null
        defaultStdFiltering("issuDt.specified=true", "issuDt.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt is greater than or equal to
        defaultStdFiltering("issuDt.greaterThanOrEqual=" + DEFAULT_ISSU_DT, "issuDt.greaterThanOrEqual=" + UPDATED_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt is less than or equal to
        defaultStdFiltering("issuDt.lessThanOrEqual=" + DEFAULT_ISSU_DT, "issuDt.lessThanOrEqual=" + SMALLER_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsLessThanSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt is less than
        defaultStdFiltering("issuDt.lessThan=" + UPDATED_ISSU_DT, "issuDt.lessThan=" + DEFAULT_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllStdsByIssuDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where issuDt is greater than
        defaultStdFiltering("issuDt.greaterThan=" + SMALLER_ISSU_DT, "issuDt.greaterThan=" + DEFAULT_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt equals to
        defaultStdFiltering("expDt.equals=" + DEFAULT_EXP_DT, "expDt.equals=" + UPDATED_EXP_DT);
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsInShouldWork() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt in
        defaultStdFiltering("expDt.in=" + DEFAULT_EXP_DT + "," + UPDATED_EXP_DT, "expDt.in=" + UPDATED_EXP_DT);
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt is not null
        defaultStdFiltering("expDt.specified=true", "expDt.specified=false");
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt is greater than or equal to
        defaultStdFiltering("expDt.greaterThanOrEqual=" + DEFAULT_EXP_DT, "expDt.greaterThanOrEqual=" + UPDATED_EXP_DT);
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt is less than or equal to
        defaultStdFiltering("expDt.lessThanOrEqual=" + DEFAULT_EXP_DT, "expDt.lessThanOrEqual=" + SMALLER_EXP_DT);
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsLessThanSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt is less than
        defaultStdFiltering("expDt.lessThan=" + UPDATED_EXP_DT, "expDt.lessThan=" + DEFAULT_EXP_DT);
    }

    @Test
    @Transactional
    void getAllStdsByExpDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        // Get all the stdList where expDt is greater than
        defaultStdFiltering("expDt.greaterThan=" + SMALLER_EXP_DT, "expDt.greaterThan=" + DEFAULT_EXP_DT);
    }

    private void defaultStdFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStdShouldBeFound(shouldBeFound);
        defaultStdShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStdShouldBeFound(String filter) throws Exception {
        restStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(std.getId().intValue())))
            .andExpect(jsonPath("$.[*].stdNo").value(hasItem(DEFAULT_STD_NO)))
            .andExpect(jsonPath("$.[*].stdVer").value(hasItem(DEFAULT_STD_VER)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)))
            .andExpect(jsonPath("$.[*].chName").value(hasItem(DEFAULT_CH_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].issuDt").value(hasItem(DEFAULT_ISSU_DT.toString())))
            .andExpect(jsonPath("$.[*].expDt").value(hasItem(DEFAULT_EXP_DT.toString())));

        // Check, that the count call also returns 1
        restStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStdShouldNotBeFound(String filter) throws Exception {
        restStdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStd() throws Exception {
        // Get the std
        restStdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStd() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the std
        Std updatedStd = stdRepository.findById(std.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStd are not directly saved in db
        em.detach(updatedStd);
        updatedStd
            .stdNo(UPDATED_STD_NO)
            .stdVer(UPDATED_STD_VER)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .status(UPDATED_STATUS)
            .issuDt(UPDATED_ISSU_DT)
            .expDt(UPDATED_EXP_DT);
        StdDTO stdDTO = stdMapper.toDto(updatedStd);

        restStdMockMvc
            .perform(put(ENTITY_API_URL_ID, stdDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stdDTO)))
            .andExpect(status().isOk());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStdToMatchAllProperties(updatedStd);
    }

    @Test
    @Transactional
    void putNonExistingStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        std.setId(longCount.incrementAndGet());

        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdMockMvc
            .perform(put(ENTITY_API_URL_ID, stdDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        std.setId(longCount.incrementAndGet());

        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        std.setId(longCount.incrementAndGet());

        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStdWithPatch() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the std using partial update
        Std partialUpdatedStd = new Std();
        partialUpdatedStd.setId(std.getId());

        partialUpdatedStd
            .stdNo(UPDATED_STD_NO)
            .stdVer(UPDATED_STD_VER)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .status(UPDATED_STATUS)
            .expDt(UPDATED_EXP_DT);

        restStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStd))
            )
            .andExpect(status().isOk());

        // Validate the Std in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStdUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStd, std), getPersistedStd(std));
    }

    @Test
    @Transactional
    void fullUpdateStdWithPatch() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the std using partial update
        Std partialUpdatedStd = new Std();
        partialUpdatedStd.setId(std.getId());

        partialUpdatedStd
            .stdNo(UPDATED_STD_NO)
            .stdVer(UPDATED_STD_VER)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .status(UPDATED_STATUS)
            .issuDt(UPDATED_ISSU_DT)
            .expDt(UPDATED_EXP_DT);

        restStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStd))
            )
            .andExpect(status().isOk());

        // Validate the Std in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStdUpdatableFieldsEquals(partialUpdatedStd, getPersistedStd(partialUpdatedStd));
    }

    @Test
    @Transactional
    void patchNonExistingStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        std.setId(longCount.incrementAndGet());

        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stdDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        std.setId(longCount.incrementAndGet());

        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        std.setId(longCount.incrementAndGet());

        // Create the Std
        StdDTO stdDTO = stdMapper.toDto(std);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stdDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Std in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStd() throws Exception {
        // Initialize the database
        stdRepository.saveAndFlush(std);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the std
        restStdMockMvc.perform(delete(ENTITY_API_URL_ID, std.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stdRepository.count();
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

    protected Std getPersistedStd(Std std) {
        return stdRepository.findById(std.getId()).orElseThrow();
    }

    protected void assertPersistedStdToMatchAllProperties(Std expectedStd) {
        assertStdAllPropertiesEquals(expectedStd, getPersistedStd(expectedStd));
    }

    protected void assertPersistedStdToMatchUpdatableProperties(Std expectedStd) {
        assertStdAllUpdatablePropertiesEquals(expectedStd, getPersistedStd(expectedStd));
    }
}
