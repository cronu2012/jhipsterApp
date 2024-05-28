package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CerfCompanyRepository;
import com.mycompany.myapp.service.CerfCompanyQueryService;
import com.mycompany.myapp.service.CerfCompanyService;
import com.mycompany.myapp.service.criteria.CerfCompanyCriteria;
import com.mycompany.myapp.service.dto.CerfCompanyDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CerfCompany}.
 */
@RestController
@RequestMapping("/api/cerf-companies")
public class CerfCompanyResource {

    private final Logger log = LoggerFactory.getLogger(CerfCompanyResource.class);

    private static final String ENTITY_NAME = "cerfCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CerfCompanyService cerfCompanyService;

    private final CerfCompanyRepository cerfCompanyRepository;

    private final CerfCompanyQueryService cerfCompanyQueryService;

    public CerfCompanyResource(
        CerfCompanyService cerfCompanyService,
        CerfCompanyRepository cerfCompanyRepository,
        CerfCompanyQueryService cerfCompanyQueryService
    ) {
        this.cerfCompanyService = cerfCompanyService;
        this.cerfCompanyRepository = cerfCompanyRepository;
        this.cerfCompanyQueryService = cerfCompanyQueryService;
    }

    /**
     * {@code POST  /cerf-companies} : Create a new cerfCompany.
     *
     * @param cerfCompanyDTO the cerfCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cerfCompanyDTO, or with status {@code 400 (Bad Request)} if the cerfCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CerfCompanyDTO> createCerfCompany(@Valid @RequestBody CerfCompanyDTO cerfCompanyDTO) throws URISyntaxException {
        log.debug("REST request to save CerfCompany : {}", cerfCompanyDTO);
        if (cerfCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new cerfCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cerfCompanyDTO = cerfCompanyService.save(cerfCompanyDTO);
        return ResponseEntity.created(new URI("/api/cerf-companies/" + cerfCompanyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cerfCompanyDTO.getId().toString()))
            .body(cerfCompanyDTO);
    }

    /**
     * {@code PUT  /cerf-companies/:id} : Updates an existing cerfCompany.
     *
     * @param id the id of the cerfCompanyDTO to save.
     * @param cerfCompanyDTO the cerfCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cerfCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the cerfCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cerfCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CerfCompanyDTO> updateCerfCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CerfCompanyDTO cerfCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CerfCompany : {}, {}", id, cerfCompanyDTO);
        if (cerfCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cerfCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cerfCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cerfCompanyDTO = cerfCompanyService.update(cerfCompanyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cerfCompanyDTO.getId().toString()))
            .body(cerfCompanyDTO);
    }

    /**
     * {@code PATCH  /cerf-companies/:id} : Partial updates given fields of an existing cerfCompany, field will ignore if it is null
     *
     * @param id the id of the cerfCompanyDTO to save.
     * @param cerfCompanyDTO the cerfCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cerfCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the cerfCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cerfCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cerfCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CerfCompanyDTO> partialUpdateCerfCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CerfCompanyDTO cerfCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CerfCompany partially : {}, {}", id, cerfCompanyDTO);
        if (cerfCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cerfCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cerfCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CerfCompanyDTO> result = cerfCompanyService.partialUpdate(cerfCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cerfCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cerf-companies} : get all the cerfCompanies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cerfCompanies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CerfCompanyDTO>> getAllCerfCompanies(
        CerfCompanyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CerfCompanies by criteria: {}", criteria);

        Page<CerfCompanyDTO> page = cerfCompanyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cerf-companies/count} : count all the cerfCompanies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCerfCompanies(CerfCompanyCriteria criteria) {
        log.debug("REST request to count CerfCompanies by criteria: {}", criteria);
        return ResponseEntity.ok().body(cerfCompanyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cerf-companies/:id} : get the "id" cerfCompany.
     *
     * @param id the id of the cerfCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cerfCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CerfCompanyDTO> getCerfCompany(@PathVariable("id") Long id) {
        log.debug("REST request to get CerfCompany : {}", id);
        Optional<CerfCompanyDTO> cerfCompanyDTO = cerfCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cerfCompanyDTO);
    }

    /**
     * {@code DELETE  /cerf-companies/:id} : delete the "id" cerfCompany.
     *
     * @param id the id of the cerfCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCerfCompany(@PathVariable("id") Long id) {
        log.debug("REST request to delete CerfCompany : {}", id);
        cerfCompanyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
