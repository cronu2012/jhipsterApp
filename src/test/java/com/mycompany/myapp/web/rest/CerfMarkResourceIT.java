package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CerfMarkAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfMark;
import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.repository.CerfMarkRepository;
import com.mycompany.myapp.service.dto.CerfMarkDTO;
import com.mycompany.myapp.service.mapper.CerfMarkMapper;
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
 * Integration tests for the {@link CerfMarkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CerfMarkResourceIT {

    private static final String DEFAULT_REL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REL_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cerf-marks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CerfMarkRepository cerfMarkRepository;

    @Autowired
    private CerfMarkMapper cerfMarkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCerfMarkMockMvc;

    private CerfMark cerfMark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfMark createEntity(EntityManager em) {
        CerfMark cerfMark = new CerfMark().relType(DEFAULT_REL_TYPE);
        return cerfMark;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CerfMark createUpdatedEntity(EntityManager em) {
        CerfMark cerfMark = new CerfMark().relType(UPDATED_REL_TYPE);
        return cerfMark;
    }

    @BeforeEach
    public void initTest() {
        cerfMark = createEntity(em);
    }

    @Test
    @Transactional
    void createCerfMark() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);
        var returnedCerfMarkDTO = om.readValue(
            restCerfMarkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfMarkDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CerfMarkDTO.class
        );

        // Validate the CerfMark in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCerfMark = cerfMarkMapper.toEntity(returnedCerfMarkDTO);
        assertCerfMarkUpdatableFieldsEquals(returnedCerfMark, getPersistedCerfMark(returnedCerfMark));
    }

    @Test
    @Transactional
    void createCerfMarkWithExistingId() throws Exception {
        // Create the CerfMark with an existing ID
        cerfMark.setId(1L);
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCerfMarkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfMarkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCerfMarks() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get all the cerfMarkList
        restCerfMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));
    }

    @Test
    @Transactional
    void getCerfMark() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get the cerfMark
        restCerfMarkMockMvc
            .perform(get(ENTITY_API_URL_ID, cerfMark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cerfMark.getId().intValue()))
            .andExpect(jsonPath("$.relType").value(DEFAULT_REL_TYPE));
    }

    @Test
    @Transactional
    void getCerfMarksByIdFiltering() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        Long id = cerfMark.getId();

        defaultCerfMarkFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCerfMarkFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCerfMarkFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCerfMarksByRelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get all the cerfMarkList where relType equals to
        defaultCerfMarkFiltering("relType.equals=" + DEFAULT_REL_TYPE, "relType.equals=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfMarksByRelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get all the cerfMarkList where relType in
        defaultCerfMarkFiltering("relType.in=" + DEFAULT_REL_TYPE + "," + UPDATED_REL_TYPE, "relType.in=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfMarksByRelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get all the cerfMarkList where relType is not null
        defaultCerfMarkFiltering("relType.specified=true", "relType.specified=false");
    }

    @Test
    @Transactional
    void getAllCerfMarksByRelTypeContainsSomething() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get all the cerfMarkList where relType contains
        defaultCerfMarkFiltering("relType.contains=" + DEFAULT_REL_TYPE, "relType.contains=" + UPDATED_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfMarksByRelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        // Get all the cerfMarkList where relType does not contain
        defaultCerfMarkFiltering("relType.doesNotContain=" + UPDATED_REL_TYPE, "relType.doesNotContain=" + DEFAULT_REL_TYPE);
    }

    @Test
    @Transactional
    void getAllCerfMarksByCerfIsEqualToSomething() throws Exception {
        Cerf cerf;
        if (TestUtil.findAll(em, Cerf.class).isEmpty()) {
            cerfMarkRepository.saveAndFlush(cerfMark);
            cerf = CerfResourceIT.createEntity(em);
        } else {
            cerf = TestUtil.findAll(em, Cerf.class).get(0);
        }
        em.persist(cerf);
        em.flush();
        cerfMark.setCerf(cerf);
        cerfMarkRepository.saveAndFlush(cerfMark);
        Long cerfId = cerf.getId();
        // Get all the cerfMarkList where cerf equals to cerfId
        defaultCerfMarkShouldBeFound("cerfId.equals=" + cerfId);

        // Get all the cerfMarkList where cerf equals to (cerfId + 1)
        defaultCerfMarkShouldNotBeFound("cerfId.equals=" + (cerfId + 1));
    }

    @Test
    @Transactional
    void getAllCerfMarksByMarkIsEqualToSomething() throws Exception {
        Mark mark;
        if (TestUtil.findAll(em, Mark.class).isEmpty()) {
            cerfMarkRepository.saveAndFlush(cerfMark);
            mark = MarkResourceIT.createEntity(em);
        } else {
            mark = TestUtil.findAll(em, Mark.class).get(0);
        }
        em.persist(mark);
        em.flush();
        cerfMark.setMark(mark);
        cerfMarkRepository.saveAndFlush(cerfMark);
        Long markId = mark.getId();
        // Get all the cerfMarkList where mark equals to markId
        defaultCerfMarkShouldBeFound("markId.equals=" + markId);

        // Get all the cerfMarkList where mark equals to (markId + 1)
        defaultCerfMarkShouldNotBeFound("markId.equals=" + (markId + 1));
    }

    private void defaultCerfMarkFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCerfMarkShouldBeFound(shouldBeFound);
        defaultCerfMarkShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCerfMarkShouldBeFound(String filter) throws Exception {
        restCerfMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cerfMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].relType").value(hasItem(DEFAULT_REL_TYPE)));

        // Check, that the count call also returns 1
        restCerfMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCerfMarkShouldNotBeFound(String filter) throws Exception {
        restCerfMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCerfMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCerfMark() throws Exception {
        // Get the cerfMark
        restCerfMarkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCerfMark() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfMark
        CerfMark updatedCerfMark = cerfMarkRepository.findById(cerfMark.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCerfMark are not directly saved in db
        em.detach(updatedCerfMark);
        updatedCerfMark.relType(UPDATED_REL_TYPE);
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(updatedCerfMark);

        restCerfMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfMarkDTO))
            )
            .andExpect(status().isOk());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCerfMarkToMatchAllProperties(updatedCerfMark);
    }

    @Test
    @Transactional
    void putNonExistingCerfMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfMark.setId(longCount.incrementAndGet());

        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cerfMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCerfMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfMark.setId(longCount.incrementAndGet());

        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cerfMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCerfMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfMark.setId(longCount.incrementAndGet());

        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMarkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cerfMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCerfMarkWithPatch() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfMark using partial update
        CerfMark partialUpdatedCerfMark = new CerfMark();
        partialUpdatedCerfMark.setId(cerfMark.getId());

        partialUpdatedCerfMark.relType(UPDATED_REL_TYPE);

        restCerfMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfMark))
            )
            .andExpect(status().isOk());

        // Validate the CerfMark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfMarkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCerfMark, cerfMark), getPersistedCerfMark(cerfMark));
    }

    @Test
    @Transactional
    void fullUpdateCerfMarkWithPatch() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cerfMark using partial update
        CerfMark partialUpdatedCerfMark = new CerfMark();
        partialUpdatedCerfMark.setId(cerfMark.getId());

        partialUpdatedCerfMark.relType(UPDATED_REL_TYPE);

        restCerfMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCerfMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCerfMark))
            )
            .andExpect(status().isOk());

        // Validate the CerfMark in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCerfMarkUpdatableFieldsEquals(partialUpdatedCerfMark, getPersistedCerfMark(partialUpdatedCerfMark));
    }

    @Test
    @Transactional
    void patchNonExistingCerfMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfMark.setId(longCount.incrementAndGet());

        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCerfMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cerfMarkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCerfMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfMark.setId(longCount.incrementAndGet());

        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cerfMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCerfMark() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cerfMark.setId(longCount.incrementAndGet());

        // Create the CerfMark
        CerfMarkDTO cerfMarkDTO = cerfMarkMapper.toDto(cerfMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCerfMarkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cerfMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CerfMark in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCerfMark() throws Exception {
        // Initialize the database
        cerfMarkRepository.saveAndFlush(cerfMark);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cerfMark
        restCerfMarkMockMvc
            .perform(delete(ENTITY_API_URL_ID, cerfMark.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cerfMarkRepository.count();
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

    protected CerfMark getPersistedCerfMark(CerfMark cerfMark) {
        return cerfMarkRepository.findById(cerfMark.getId()).orElseThrow();
    }

    protected void assertPersistedCerfMarkToMatchAllProperties(CerfMark expectedCerfMark) {
        assertCerfMarkAllPropertiesEquals(expectedCerfMark, getPersistedCerfMark(expectedCerfMark));
    }

    protected void assertPersistedCerfMarkToMatchUpdatableProperties(CerfMark expectedCerfMark) {
        assertCerfMarkAllUpdatablePropertiesEquals(expectedCerfMark, getPersistedCerfMark(expectedCerfMark));
    }
}
