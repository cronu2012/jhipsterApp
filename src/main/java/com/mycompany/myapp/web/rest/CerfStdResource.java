package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CerfStdRepository;
import com.mycompany.myapp.service.CerfStdQueryService;
import com.mycompany.myapp.service.CerfStdService;
import com.mycompany.myapp.service.criteria.CerfStdCriteria;
import com.mycompany.myapp.service.dto.CerfStdDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CerfStd}.
 */
@RestController
@RequestMapping("/api/cerf-stds")
public class CerfStdResource {

    private final Logger log = LoggerFactory.getLogger(CerfStdResource.class);

    private static final String ENTITY_NAME = "cerfStd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CerfStdService cerfStdService;

    private final CerfStdRepository cerfStdRepository;

    private final CerfStdQueryService cerfStdQueryService;

    public CerfStdResource(CerfStdService cerfStdService, CerfStdRepository cerfStdRepository, CerfStdQueryService cerfStdQueryService) {
        this.cerfStdService = cerfStdService;
        this.cerfStdRepository = cerfStdRepository;
        this.cerfStdQueryService = cerfStdQueryService;
    }

    /**
     * {@code POST  /cerf-stds} : Create a new cerfStd.
     *
     * @param cerfStdDTO the cerfStdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cerfStdDTO, or with status {@code 400 (Bad Request)} if the cerfStd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CerfStdDTO> createCerfStd(@Valid @RequestBody CerfStdDTO cerfStdDTO) throws URISyntaxException {
        log.debug("REST request to save CerfStd : {}", cerfStdDTO);
        if (cerfStdDTO.getId() != null) {
            throw new BadRequestAlertException("A new cerfStd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cerfStdDTO = cerfStdService.save(cerfStdDTO);
        return ResponseEntity.created(new URI("/api/cerf-stds/" + cerfStdDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cerfStdDTO.getId().toString()))
            .body(cerfStdDTO);
    }

    /**
     * {@code PUT  /cerf-stds/:id} : Updates an existing cerfStd.
     *
     * @param id the id of the cerfStdDTO to save.
     * @param cerfStdDTO the cerfStdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cerfStdDTO,
     * or with status {@code 400 (Bad Request)} if the cerfStdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cerfStdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CerfStdDTO> updateCerfStd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CerfStdDTO cerfStdDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CerfStd : {}, {}", id, cerfStdDTO);
        if (cerfStdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cerfStdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cerfStdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cerfStdDTO = cerfStdService.update(cerfStdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cerfStdDTO.getId().toString()))
            .body(cerfStdDTO);
    }

    /**
     * {@code PATCH  /cerf-stds/:id} : Partial updates given fields of an existing cerfStd, field will ignore if it is null
     *
     * @param id the id of the cerfStdDTO to save.
     * @param cerfStdDTO the cerfStdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cerfStdDTO,
     * or with status {@code 400 (Bad Request)} if the cerfStdDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cerfStdDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cerfStdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CerfStdDTO> partialUpdateCerfStd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CerfStdDTO cerfStdDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CerfStd partially : {}, {}", id, cerfStdDTO);
        if (cerfStdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cerfStdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cerfStdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CerfStdDTO> result = cerfStdService.partialUpdate(cerfStdDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cerfStdDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cerf-stds} : get all the cerfStds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cerfStds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CerfStdDTO>> getAllCerfStds(
        CerfStdCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CerfStds by criteria: {}", criteria);

        Page<CerfStdDTO> page = cerfStdQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cerf-stds/count} : count all the cerfStds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCerfStds(CerfStdCriteria criteria) {
        log.debug("REST request to count CerfStds by criteria: {}", criteria);
        return ResponseEntity.ok().body(cerfStdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cerf-stds/:id} : get the "id" cerfStd.
     *
     * @param id the id of the cerfStdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cerfStdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CerfStdDTO> getCerfStd(@PathVariable("id") Long id) {
        log.debug("REST request to get CerfStd : {}", id);
        Optional<CerfStdDTO> cerfStdDTO = cerfStdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cerfStdDTO);
    }

    /**
     * {@code DELETE  /cerf-stds/:id} : delete the "id" cerfStd.
     *
     * @param id the id of the cerfStdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCerfStd(@PathVariable("id") Long id) {
        log.debug("REST request to delete CerfStd : {}", id);
        cerfStdService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
