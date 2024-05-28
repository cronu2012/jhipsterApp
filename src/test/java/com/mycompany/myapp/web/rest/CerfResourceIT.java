package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CerfAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.repository.CerfRepository;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.mapper.CerfMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
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
 * Integration tests for the {@link CerfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CerfResourceIT {

    private static final String DEFAULT_CERF_NO = "AAAAAAAAAA";
    private static final String UPDATED_CERF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CERF_VER = "AAAAAAAAAA";
    private static final String UPDATED_CERF_VER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PDF_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_ISSU_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSU_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ISSU_DT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_EXP_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXP_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXP_DT = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/cerfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CerfRepository cerfRepository;

    @Autowired
    private CerfMapper cerfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCerfMockMvc;

    private Cerf cerf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cerf createEntity(EntityManager em) {
        Cerf cerf = new Cerf()
            .cerfNo(DEFAULT_CERF_NO)
            .cerfVer(DEFAULT_CERF_VER)
            .status(DEFAULT_STATUS)
            .pdf(DEFAULT_PDF)
            .pdfContentType(DEFAULT_PDF_CONTENT_TYPE)
            .issuDt(DEFAULT_ISSU_DT)
            .expDt(DEFAULT_EXP_DT);
        return cerf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cerf createUpdatedEntity(EntityManager em) {
        Cerf cerf = new Cerf()
            .cerfNo(UPDATED_CERF_NO)
            .cerfVer(UPDATED_CERF_VER)
            .status(UPDATED_STATUS)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE)
            .issuDt(UPDATED_ISSU_DT)
            .expDt(UPDATED_EXP_DT);
        return cerf;
    }

    @BeforeEach
    public void initTest() {
        cerf = createEntity(em);
    }

    @Test
    @Transactional
    void createCerf() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);
        var returnedCerfDTO = om.readValue(
            restCerfMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CerfDTO.class
        );

        // Validate the Cerf in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCerf = cerfMapper.toEntity(returnedCerfDTO);
        assertCerfUpdatableFieldsEquals(returnedCerf, getPersistedCerf(returnedCerf));
    }

    @Test
    @Transactional
    void createCerfWithExistingId() throws Exception {
        // Create the Cerf with an existing ID
        cerf.setId(1L);
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCerfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCerfs() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList
        restCerfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerf.getId().intValue())))
            .andExpect(jsonPath("$.[*].cerfNo").value(hasItem(DEFAULT_CERF_NO)))
            .andExpect(jsonPath("$.[*].cerfVer").value(hasItem(DEFAULT_CERF_VER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].pdfContentType").value(hasItem(DEFAULT_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdf").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PDF))))
            .andExpect(jsonPath("$.[*].issuDt").value(hasItem(DEFAULT_ISSU_DT.toString())))
            .andExpect(jsonPath("$.[*].expDt").value(hasItem(DEFAULT_EXP_DT.toString())));
    }

    @Test
    @Transactional
    void getCerf() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get the cerf
        restCerfMockMvc
            .perform(get(ENTITY_API_URL_ID, cerf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cerf.getId().intValue()))
            .andExpect(jsonPath("$.cerfNo").value(DEFAULT_CERF_NO))
            .andExpect(jsonPath("$.cerfVer").value(DEFAULT_CERF_VER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.pdfContentType").value(DEFAULT_PDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.pdf").value(Base64.getEncoder().encodeToString(DEFAULT_PDF)))
            .andExpect(jsonPath("$.issuDt").value(DEFAULT_ISSU_DT.toString()))
            .andExpect(jsonPath("$.expDt").value(DEFAULT_EXP_DT.toString()));
    }

    @Test
    @Transactional
    void getCerfsByIdFiltering() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        Long id = cerf.getId();

        defaultCerfFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCerfFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCerfFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfNo equals to
        defaultCerfFiltering("cerfNo.equals=" + DEFAULT_CERF_NO, "cerfNo.equals=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfNoIsInShouldWork() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfNo in
        defaultCerfFiltering("cerfNo.in=" + DEFAULT_CERF_NO + "," + UPDATED_CERF_NO, "cerfNo.in=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfNo is not null
        defaultCerfFiltering("cerfNo.specified=true", "cerfNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfsByCerfNoContainsSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfNo contains
        defaultCerfFiltering("cerfNo.contains=" + DEFAULT_CERF_NO, "cerfNo.contains=" + UPDATED_CERF_NO);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfNoNotContainsSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfNo does not contain
        defaultCerfFiltering("cerfNo.doesNotContain=" + UPDATED_CERF_NO, "cerfNo.doesNotContain=" + DEFAULT_CERF_NO);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfVerIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfVer equals to
        defaultCerfFiltering("cerfVer.equals=" + DEFAULT_CERF_VER, "cerfVer.equals=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfVerIsInShouldWork() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfVer in
        defaultCerfFiltering("cerfVer.in=" + DEFAULT_CERF_VER + "," + UPDATED_CERF_VER, "cerfVer.in=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfVerIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfVer is not null
        defaultCerfFiltering("cerfVer.specified=true", "cerfVer.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfsByCerfVerContainsSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfVer contains
        defaultCerfFiltering("cerfVer.contains=" + DEFAULT_CERF_VER, "cerfVer.contains=" + UPDATED_CERF_VER);
    }

    @Test
    @Transactional
    void getAllCerfsByCerfVerNotContainsSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where cerfVer does not contain
        defaultCerfFiltering("cerfVer.doesNotContain=" + UPDATED_CERF_VER, "cerfVer.doesNotContain=" + DEFAULT_CERF_VER);
    }

    @Test
    @Transactional
    void getAllCerfsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where status equals to
        defaultCerfFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCerfsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where status in
        defaultCerfFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCerfsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where status is not null
        defaultCerfFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfsByStatusContainsSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where status contains
        defaultCerfFiltering("status.contains=" + DEFAULT_STATUS, "status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCerfsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where status does not contain
        defaultCerfFiltering("status.doesNotContain=" + UPDATED_STATUS, "status.doesNotContain=" + DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt equals to
        defaultCerfFiltering("issuDt.equals=" + DEFAULT_ISSU_DT, "issuDt.equals=" + UPDATED_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsInShouldWork() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt in
        defaultCerfFiltering("issuDt.in=" + DEFAULT_ISSU_DT + "," + UPDATED_ISSU_DT, "issuDt.in=" + UPDATED_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt is not null
        defaultCerfFiltering("issuDt.specified=true", "issuDt.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt is greater than or equal to
        defaultCerfFiltering("issuDt.greaterThanOrEqual=" + DEFAULT_ISSU_DT, "issuDt.greaterThanOrEqual=" + UPDATED_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt is less than or equal to
        defaultCerfFiltering("issuDt.lessThanOrEqual=" + DEFAULT_ISSU_DT, "issuDt.lessThanOrEqual=" + SMALLER_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsLessThanSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt is less than
        defaultCerfFiltering("issuDt.lessThan=" + UPDATED_ISSU_DT, "issuDt.lessThan=" + DEFAULT_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByIssuDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where issuDt is greater than
        defaultCerfFiltering("issuDt.greaterThan=" + SMALLER_ISSU_DT, "issuDt.greaterThan=" + DEFAULT_ISSU_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt equals to
        defaultCerfFiltering("expDt.equals=" + DEFAULT_EXP_DT, "expDt.equals=" + UPDATED_EXP_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsInShouldWork() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt in
        defaultCerfFiltering("expDt.in=" + DEFAULT_EXP_DT + "," + UPDATED_EXP_DT, "expDt.in=" + UPDATED_EXP_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt is not null
        defaultCerfFiltering("expDt.specified=true", "expDt.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt is greater than or equal to
        defaultCerfFiltering("expDt.greaterThanOrEqual=" + DEFAULT_EXP_DT, "expDt.greaterThanOrEqual=" + UPDATED_EXP_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt is less than or equal to
        defaultCerfFiltering("expDt.lessThanOrEqual=" + DEFAULT_EXP_DT, "expDt.lessThanOrEqual=" + SMALLER_EXP_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsLessThanSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt is less than
        defaultCerfFiltering("expDt.lessThan=" + UPDATED_EXP_DT, "expDt.lessThan=" + DEFAULT_EXP_DT);
    }

    @Test
    @Transactional
    void getAllCerfsByExpDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        // Get all the cerfList where expDt is greater than
        defaultCerfFiltering("expDt.greaterThan=" + SMALLER_EXP_DT, "expDt.greaterThan=" + DEFAULT_EXP_DT);
    }

    private void defaultCerfFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCerfShouldBeFound(shouldBeFound);
        defaultCerfShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCerfShouldBeFound(String filter) throws Exception {
        restCerfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerf.getId().intValue())))
            .andExpect(jsonPath("$.[*].cerfNo").value(hasItem(DEFAULT_CERF_NO)))
            .andExpect(jsonPath("$.[*].cerfVer").value(hasItem(DEFAULT_CERF_VER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].pdfContentType").value(hasItem(DEFAULT_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdf").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PDF))))
            .andExpect(jsonPath("$.[*].issuDt").value(hasItem(DEFAULT_ISSU_DT.toString())))
            .andExpect(jsonPath("$.[*].expDt").value(hasItem(DEFAULT_EXP_DT.toString())));

        // Check, that the count call also returns 1
        restCerfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCerfShouldNotBeFound(String filter) throws Exception {
        restCerfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCerfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCerf() throws Exception {
        // Get the cerf
        restCerfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCerf() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerf
        Cerf updatedCerf = cerfRepository.findById(cerf.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCerf are not directly saved in db
        em.detach(updatedCerf);
        updatedCerf
            .cerfNo(UPDATED_CERF_NO)
            .cerfVer(UPDATED_CERF_VER)
            .status(UPDATED_STATUS)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE)
            .issuDt(UPDATED_ISSU_DT)
            .expDt(UPDATED_EXP_DT);
        CerfDTO cerfDTO = cerfMapper.toDto(updatedCerf);

        restCerfMockMvc
            .perform(put(ENTITY_API_URL_ID, cerfDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfDTO)))
            .andExpect(status().isOk());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCerfToMatchAllProperties(updatedCerf);
    }

    @Test
    @Transactional
    void putNonExistingCerf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerf.setId(longCount.incrementAndGet());

        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfMockMvc
            .perform(put(ENTITY_API_URL_ID, cerfDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCerf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerf.setId(longCount.incrementAndGet());

        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCerf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerf.setId(longCount.incrementAndGet());

        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCerfWithPatch() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerf using partial update
        Cerf partialUpdatedCerf = new Cerf();
        partialUpdatedCerf.setId(cerf.getId());

        partialUpdatedCerf.cerfVer(UPDATED_CERF_VER).pdf(UPDATED_PDF).pdfContentType(UPDATED_PDF_CONTENT_TYPE);

        restCerfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerf.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerf))
            )
            .andExpect(status().isOk());

        // Validate the Cerf in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCerf, cerf), getPersistedCerf(cerf));
    }

    @Test
    @Transactional
    void fullUpdateCerfWithPatch() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerf using partial update
        Cerf partialUpdatedCerf = new Cerf();
        partialUpdatedCerf.setId(cerf.getId());

        partialUpdatedCerf
            .cerfNo(UPDATED_CERF_NO)
            .cerfVer(UPDATED_CERF_VER)
            .status(UPDATED_STATUS)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE)
            .issuDt(UPDATED_ISSU_DT)
            .expDt(UPDATED_EXP_DT);

        restCerfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerf.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerf))
            )
            .andExpect(status().isOk());

        // Validate the Cerf in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfUpdatableFieldsEquals(partialUpdatedCerf, getPersistedCerf(partialUpdatedCerf));
    }

    @Test
    @Transactional
    void patchNonExistingCerf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerf.setId(longCount.incrementAndGet());

        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cerfDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cerfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCerf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerf.setId(longCount.incrementAndGet());

        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCerf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerf.setId(longCount.incrementAndGet());

        // Create the Cerf
        CerfDTO cerfDTO = cerfMapper.toDto(cerf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cerfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cerf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCerf() throws Exception {
        // Initialize the database
        cerfRepository.saveAndFlush(cerf);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cerf
        restCerfMockMvc
            .perform(delete(ENTITY_API_URL_ID, cerf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cerfRepository.count();
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

    protected Cerf getPersistedCerf(Cerf cerf) {
        return cerfRepository.findById(cerf.getId()).orElseThrow();
    }

    protected void assertPersistedCerfToMatchAllProperties(Cerf expectedCerf) {
        assertCerfAllPropertiesEquals(expectedCerf, getPersistedCerf(expectedCerf));
    }

    protected void assertPersistedCerfToMatchUpdatableProperties(Cerf expectedCerf) {
        assertCerfAllUpdatablePropertiesEquals(expectedCerf, getPersistedCerf(expectedCerf));
    }
}
