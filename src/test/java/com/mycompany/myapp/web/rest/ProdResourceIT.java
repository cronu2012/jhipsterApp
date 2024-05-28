package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ProdAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.repository.ProdRepository;
import com.mycompany.myapp.service.dto.ProdDTO;
import com.mycompany.myapp.service.mapper.ProdMapper;
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
 * Integration tests for the {@link ProdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdResourceIT {

    private static final String DEFAULT_PROD_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CCC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CCC_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdMockMvc;

    private Prod prod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prod createEntity(EntityManager em) {
        Prod prod = new Prod()
            .prodNo(DEFAULT_PROD_NO)
            .enName(DEFAULT_EN_NAME)
            .chName(DEFAULT_CH_NAME)
            .hsCode(DEFAULT_HS_CODE)
            .cccCode(DEFAULT_CCC_CODE);
        return prod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prod createUpdatedEntity(EntityManager em) {
        Prod prod = new Prod()
            .prodNo(UPDATED_PROD_NO)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .hsCode(UPDATED_HS_CODE)
            .cccCode(UPDATED_CCC_CODE);
        return prod;
    }

    @BeforeEach
    public void initTest() {
        prod = createEntity(em);
    }

    @Test
    @Transactional
    void createProd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);
        var returnedProdDTO = om.readValue(
            restProdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProdDTO.class
        );

        // Validate the Prod in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProd = prodMapper.toEntity(returnedProdDTO);
        assertProdUpdatableFieldsEquals(returnedProd, getPersistedProd(returnedProd));
    }

    @Test
    @Transactional
    void createProdWithExistingId() throws Exception {
        // Create the Prod with an existing ID
        prod.setId(1L);
        ProdDTO prodDTO = prodMapper.toDto(prod);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProds() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList
        restProdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prod.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodNo").value(hasItem(DEFAULT_PROD_NO)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)))
            .andExpect(jsonPath("$.[*].chName").value(hasItem(DEFAULT_CH_NAME)))
            .andExpect(jsonPath("$.[*].hsCode").value(hasItem(DEFAULT_HS_CODE)))
            .andExpect(jsonPath("$.[*].cccCode").value(hasItem(DEFAULT_CCC_CODE)));
    }

    @Test
    @Transactional
    void getProd() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get the prod
        restProdMockMvc
            .perform(get(ENTITY_API_URL_ID, prod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prod.getId().intValue()))
            .andExpect(jsonPath("$.prodNo").value(DEFAULT_PROD_NO))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME))
            .andExpect(jsonPath("$.chName").value(DEFAULT_CH_NAME))
            .andExpect(jsonPath("$.hsCode").value(DEFAULT_HS_CODE))
            .andExpect(jsonPath("$.cccCode").value(DEFAULT_CCC_CODE));
    }

    @Test
    @Transactional
    void getProdsByIdFiltering() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        Long id = prod.getId();

        defaultProdFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProdFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProdFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProdsByProdNoIsEqualToSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where prodNo equals to
        defaultProdFiltering("prodNo.equals=" + DEFAULT_PROD_NO, "prodNo.equals=" + UPDATED_PROD_NO);
    }

    @Test
    @Transactional
    void getAllProdsByProdNoIsInShouldWork() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where prodNo in
        defaultProdFiltering("prodNo.in=" + DEFAULT_PROD_NO + "," + UPDATED_PROD_NO, "prodNo.in=" + UPDATED_PROD_NO);
    }

    @Test
    @Transactional
    void getAllProdsByProdNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where prodNo is not null
        defaultProdFiltering("prodNo.specified=true", "prodNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProdsByProdNoContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where prodNo contains
        defaultProdFiltering("prodNo.contains=" + DEFAULT_PROD_NO, "prodNo.contains=" + UPDATED_PROD_NO);
    }

    @Test
    @Transactional
    void getAllProdsByProdNoNotContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where prodNo does not contain
        defaultProdFiltering("prodNo.doesNotContain=" + UPDATED_PROD_NO, "prodNo.doesNotContain=" + DEFAULT_PROD_NO);
    }

    @Test
    @Transactional
    void getAllProdsByEnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where enName equals to
        defaultProdFiltering("enName.equals=" + DEFAULT_EN_NAME, "enName.equals=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByEnNameIsInShouldWork() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where enName in
        defaultProdFiltering("enName.in=" + DEFAULT_EN_NAME + "," + UPDATED_EN_NAME, "enName.in=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByEnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where enName is not null
        defaultProdFiltering("enName.specified=true", "enName.specified=false");
    }

    @Test
    @Transactional
    void getAllProdsByEnNameContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where enName contains
        defaultProdFiltering("enName.contains=" + DEFAULT_EN_NAME, "enName.contains=" + UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByEnNameNotContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where enName does not contain
        defaultProdFiltering("enName.doesNotContain=" + UPDATED_EN_NAME, "enName.doesNotContain=" + DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByChNameIsEqualToSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where chName equals to
        defaultProdFiltering("chName.equals=" + DEFAULT_CH_NAME, "chName.equals=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByChNameIsInShouldWork() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where chName in
        defaultProdFiltering("chName.in=" + DEFAULT_CH_NAME + "," + UPDATED_CH_NAME, "chName.in=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByChNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where chName is not null
        defaultProdFiltering("chName.specified=true", "chName.specified=false");
    }

    @Test
    @Transactional
    void getAllProdsByChNameContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where chName contains
        defaultProdFiltering("chName.contains=" + DEFAULT_CH_NAME, "chName.contains=" + UPDATED_CH_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByChNameNotContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where chName does not contain
        defaultProdFiltering("chName.doesNotContain=" + UPDATED_CH_NAME, "chName.doesNotContain=" + DEFAULT_CH_NAME);
    }

    @Test
    @Transactional
    void getAllProdsByHsCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where hsCode equals to
        defaultProdFiltering("hsCode.equals=" + DEFAULT_HS_CODE, "hsCode.equals=" + UPDATED_HS_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByHsCodeIsInShouldWork() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where hsCode in
        defaultProdFiltering("hsCode.in=" + DEFAULT_HS_CODE + "," + UPDATED_HS_CODE, "hsCode.in=" + UPDATED_HS_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByHsCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where hsCode is not null
        defaultProdFiltering("hsCode.specified=true", "hsCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProdsByHsCodeContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where hsCode contains
        defaultProdFiltering("hsCode.contains=" + DEFAULT_HS_CODE, "hsCode.contains=" + UPDATED_HS_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByHsCodeNotContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where hsCode does not contain
        defaultProdFiltering("hsCode.doesNotContain=" + UPDATED_HS_CODE, "hsCode.doesNotContain=" + DEFAULT_HS_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByCccCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where cccCode equals to
        defaultProdFiltering("cccCode.equals=" + DEFAULT_CCC_CODE, "cccCode.equals=" + UPDATED_CCC_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByCccCodeIsInShouldWork() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where cccCode in
        defaultProdFiltering("cccCode.in=" + DEFAULT_CCC_CODE + "," + UPDATED_CCC_CODE, "cccCode.in=" + UPDATED_CCC_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByCccCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where cccCode is not null
        defaultProdFiltering("cccCode.specified=true", "cccCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProdsByCccCodeContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where cccCode contains
        defaultProdFiltering("cccCode.contains=" + DEFAULT_CCC_CODE, "cccCode.contains=" + UPDATED_CCC_CODE);
    }

    @Test
    @Transactional
    void getAllProdsByCccCodeNotContainsSomething() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        // Get all the prodList where cccCode does not contain
        defaultProdFiltering("cccCode.doesNotContain=" + UPDATED_CCC_CODE, "cccCode.doesNotContain=" + DEFAULT_CCC_CODE);
    }

    private void defaultProdFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProdShouldBeFound(shouldBeFound);
        defaultProdShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdShouldBeFound(String filter) throws Exception {
        restProdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prod.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodNo").value(hasItem(DEFAULT_PROD_NO)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)))
            .andExpect(jsonPath("$.[*].chName").value(hasItem(DEFAULT_CH_NAME)))
            .andExpect(jsonPath("$.[*].hsCode").value(hasItem(DEFAULT_HS_CODE)))
            .andExpect(jsonPath("$.[*].cccCode").value(hasItem(DEFAULT_CCC_CODE)));

        // Check, that the count call also returns 1
        restProdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdShouldNotBeFound(String filter) throws Exception {
        restProdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProd() throws Exception {
        // Get the prod
        restProdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProd() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prod
        Prod updatedProd = prodRepository.findById(prod.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProd are not directly saved in db
        em.detach(updatedProd);
        updatedProd
            .prodNo(UPDATED_PROD_NO)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .hsCode(UPDATED_HS_CODE)
            .cccCode(UPDATED_CCC_CODE);
        ProdDTO prodDTO = prodMapper.toDto(updatedProd);

        restProdMockMvc
            .perform(put(ENTITY_API_URL_ID, prodDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodDTO)))
            .andExpect(status().isOk());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProdToMatchAllProperties(updatedProd);
    }

    @Test
    @Transactional
    void putNonExistingProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prod.setId(longCount.incrementAndGet());

        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdMockMvc
            .perform(put(ENTITY_API_URL_ID, prodDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prod.setId(longCount.incrementAndGet());

        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prod.setId(longCount.incrementAndGet());

        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prodDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdWithPatch() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prod using partial update
        Prod partialUpdatedProd = new Prod();
        partialUpdatedProd.setId(prod.getId());

        partialUpdatedProd.prodNo(UPDATED_PROD_NO).enName(UPDATED_EN_NAME).hsCode(UPDATED_HS_CODE).cccCode(UPDATED_CCC_CODE);

        restProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProd))
            )
            .andExpect(status().isOk());

        // Validate the Prod in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProd, prod), getPersistedProd(prod));
    }

    @Test
    @Transactional
    void fullUpdateProdWithPatch() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prod using partial update
        Prod partialUpdatedProd = new Prod();
        partialUpdatedProd.setId(prod.getId());

        partialUpdatedProd
            .prodNo(UPDATED_PROD_NO)
            .enName(UPDATED_EN_NAME)
            .chName(UPDATED_CH_NAME)
            .hsCode(UPDATED_HS_CODE)
            .cccCode(UPDATED_CCC_CODE);

        restProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProd))
            )
            .andExpect(status().isOk());

        // Validate the Prod in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProdUpdatableFieldsEquals(partialUpdatedProd, getPersistedProd(partialUpdatedProd));
    }

    @Test
    @Transactional
    void patchNonExistingProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prod.setId(longCount.incrementAndGet());

        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prodDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prod.setId(longCount.incrementAndGet());

        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prod.setId(longCount.incrementAndGet());

        // Create the Prod
        ProdDTO prodDTO = prodMapper.toDto(prod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prodDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProd() throws Exception {
        // Initialize the database
        prodRepository.saveAndFlush(prod);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prod
        restProdMockMvc
            .perform(delete(ENTITY_API_URL_ID, prod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prodRepository.count();
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

    protected Prod getPersistedProd(Prod prod) {
        return prodRepository.findById(prod.getId()).orElseThrow();
    }

    protected void assertPersistedProdToMatchAllProperties(Prod expectedProd) {
        assertProdAllPropertiesEquals(expectedProd, getPersistedProd(expectedProd));
    }

    protected void assertPersistedProdToMatchUpdatableProperties(Prod expectedProd) {
        assertProdAllUpdatablePropertiesEquals(expectedProd, getPersistedProd(expectedProd));
    }
}
