package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.FeeProdCerfCompanyRepository;
import com.mycompany.myapp.service.FeeProdCerfCompanyQueryService;
import com.mycompany.myapp.service.FeeProdCerfCompanyService;
import com.mycompany.myapp.service.criteria.FeeProdCerfCompanyCriteria;
import com.mycompany.myapp.service.dto.FeeProdCerfCompanyDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FeeProdCerfCompany}.
 */
@RestController
@RequestMapping("/api/fee-prod-cerf-companies")
public class FeeProdCerfCompanyResource {

    private final Logger log = LoggerFactory.getLogger(FeeProdCerfCompanyResource.class);

    private static final String ENTITY_NAME = "feeProdCerfCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeeProdCerfCompanyService feeProdCerfCompanyService;

    private final FeeProdCerfCompanyRepository feeProdCerfCompanyRepository;

    private final FeeProdCerfCompanyQueryService feeProdCerfCompanyQueryService;

    public FeeProdCerfCompanyResource(
        FeeProdCerfCompanyService feeProdCerfCompanyService,
        FeeProdCerfCompanyRepository feeProdCerfCompanyRepository,
        FeeProdCerfCompanyQueryService feeProdCerfCompanyQueryService
    ) {
        this.feeProdCerfCompanyService = feeProdCerfCompanyService;
        this.feeProdCerfCompanyRepository = feeProdCerfCompanyRepository;
        this.feeProdCerfCompanyQueryService = feeProdCerfCompanyQueryService;
    }

    /**
     * {@code POST  /fee-prod-cerf-companies} : Create a new feeProdCerfCompany.
     *
     * @param feeProdCerfCompanyDTO the feeProdCerfCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feeProdCerfCompanyDTO, or with status {@code 400 (Bad Request)} if the feeProdCerfCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FeeProdCerfCompanyDTO> createFeeProdCerfCompany(@Valid @RequestBody FeeProdCerfCompanyDTO feeProdCerfCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save FeeProdCerfCompany : {}", feeProdCerfCompanyDTO);
        if (feeProdCerfCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new feeProdCerfCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        feeProdCerfCompanyDTO = feeProdCerfCompanyService.save(feeProdCerfCompanyDTO);
        return ResponseEntity.created(new URI("/api/fee-prod-cerf-companies/" + feeProdCerfCompanyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, feeProdCerfCompanyDTO.getId().toString()))
            .body(feeProdCerfCompanyDTO);
    }

    /**
     * {@code PUT  /fee-prod-cerf-companies/:id} : Updates an existing feeProdCerfCompany.
     *
     * @param id the id of the feeProdCerfCompanyDTO to save.
     * @param feeProdCerfCompanyDTO the feeProdCerfCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feeProdCerfCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the feeProdCerfCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feeProdCerfCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FeeProdCerfCompanyDTO> updateFeeProdCerfCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeeProdCerfCompanyDTO feeProdCerfCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FeeProdCerfCompany : {}, {}", id, feeProdCerfCompanyDTO);
        if (feeProdCerfCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feeProdCerfCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feeProdCerfCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        feeProdCerfCompanyDTO = feeProdCerfCompanyService.update(feeProdCerfCompanyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feeProdCerfCompanyDTO.getId().toString()))
            .body(feeProdCerfCompanyDTO);
    }

    /**
     * {@code PATCH  /fee-prod-cerf-companies/:id} : Partial updates given fields of an existing feeProdCerfCompany, field will ignore if it is null
     *
     * @param id the id of the feeProdCerfCompanyDTO to save.
     * @param feeProdCerfCompanyDTO the feeProdCerfCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feeProdCerfCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the feeProdCerfCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feeProdCerfCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feeProdCerfCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeeProdCerfCompanyDTO> partialUpdateFeeProdCerfCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeeProdCerfCompanyDTO feeProdCerfCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeeProdCerfCompany partially : {}, {}", id, feeProdCerfCompanyDTO);
        if (feeProdCerfCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feeProdCerfCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feeProdCerfCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeeProdCerfCompanyDTO> result = feeProdCerfCompanyService.partialUpdate(feeProdCerfCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feeProdCerfCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fee-prod-cerf-companies} : get all the feeProdCerfCompanies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feeProdCerfCompanies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FeeProdCerfCompanyDTO>> getAllFeeProdCerfCompanies(
        FeeProdCerfCompanyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeeProdCerfCompanies by criteria: {}", criteria);

        Page<FeeProdCerfCompanyDTO> page = feeProdCerfCompanyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fee-prod-cerf-companies/count} : count all the feeProdCerfCompanies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFeeProdCerfCompanies(FeeProdCerfCompanyCriteria criteria) {
        log.debug("REST request to count FeeProdCerfCompanies by criteria: {}", criteria);
        return ResponseEntity.ok().body(feeProdCerfCompanyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fee-prod-cerf-companies/:id} : get the "id" feeProdCerfCompany.
     *
     * @param id the id of the feeProdCerfCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feeProdCerfCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FeeProdCerfCompanyDTO> getFeeProdCerfCompany(@PathVariable("id") Long id) {
        log.debug("REST request to get FeeProdCerfCompany : {}", id);
        Optional<FeeProdCerfCompanyDTO> feeProdCerfCompanyDTO = feeProdCerfCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feeProdCerfCompanyDTO);
    }

    /**
     * {@code DELETE  /fee-prod-cerf-companies/:id} : delete the "id" feeProdCerfCompany.
     *
     * @param id the id of the feeProdCerfCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeProdCerfCompany(@PathVariable("id") Long id) {
        log.debug("REST request to delete FeeProdCerfCompany : {}", id);
        feeProdCerfCompanyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
