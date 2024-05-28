package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CountryCertRepository;
import com.mycompany.myapp.service.CountryCertQueryService;
import com.mycompany.myapp.service.CountryCertService;
import com.mycompany.myapp.service.criteria.CountryCertCriteria;
import com.mycompany.myapp.service.dto.CountryCertDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CountryCert}.
 */
@RestController
@RequestMapping("/api/country-certs")
public class CountryCertResource {

    private final Logger log = LoggerFactory.getLogger(CountryCertResource.class);

    private static final String ENTITY_NAME = "countryCert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryCertService countryCertService;

    private final CountryCertRepository countryCertRepository;

    private final CountryCertQueryService countryCertQueryService;

    public CountryCertResource(
        CountryCertService countryCertService,
        CountryCertRepository countryCertRepository,
        CountryCertQueryService countryCertQueryService
    ) {
        this.countryCertService = countryCertService;
        this.countryCertRepository = countryCertRepository;
        this.countryCertQueryService = countryCertQueryService;
    }

    /**
     * {@code POST  /country-certs} : Create a new countryCert.
     *
     * @param countryCertDTO the countryCertDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryCertDTO, or with status {@code 400 (Bad Request)} if the countryCert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CountryCertDTO> createCountryCert(@Valid @RequestBody CountryCertDTO countryCertDTO) throws URISyntaxException {
        log.debug("REST request to save CountryCert : {}", countryCertDTO);
        if (countryCertDTO.getId() != null) {
            throw new BadRequestAlertException("A new countryCert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        countryCertDTO = countryCertService.save(countryCertDTO);
        return ResponseEntity.created(new URI("/api/country-certs/" + countryCertDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, countryCertDTO.getId().toString()))
            .body(countryCertDTO);
    }

    /**
     * {@code PUT  /country-certs/:id} : Updates an existing countryCert.
     *
     * @param id the id of the countryCertDTO to save.
     * @param countryCertDTO the countryCertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryCertDTO,
     * or with status {@code 400 (Bad Request)} if the countryCertDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryCertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CountryCertDTO> updateCountryCert(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CountryCertDTO countryCertDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CountryCert : {}, {}", id, countryCertDTO);
        if (countryCertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countryCertDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryCertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        countryCertDTO = countryCertService.update(countryCertDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryCertDTO.getId().toString()))
            .body(countryCertDTO);
    }

    /**
     * {@code PATCH  /country-certs/:id} : Partial updates given fields of an existing countryCert, field will ignore if it is null
     *
     * @param id the id of the countryCertDTO to save.
     * @param countryCertDTO the countryCertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryCertDTO,
     * or with status {@code 400 (Bad Request)} if the countryCertDTO is not valid,
     * or with status {@code 404 (Not Found)} if the countryCertDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the countryCertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CountryCertDTO> partialUpdateCountryCert(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CountryCertDTO countryCertDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CountryCert partially : {}, {}", id, countryCertDTO);
        if (countryCertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countryCertDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryCertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CountryCertDTO> result = countryCertService.partialUpdate(countryCertDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryCertDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /country-certs} : get all the countryCerts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countryCerts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CountryCertDTO>> getAllCountryCerts(
        CountryCertCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CountryCerts by criteria: {}", criteria);

        Page<CountryCertDTO> page = countryCertQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /country-certs/count} : count all the countryCerts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCountryCerts(CountryCertCriteria criteria) {
        log.debug("REST request to count CountryCerts by criteria: {}", criteria);
        return ResponseEntity.ok().body(countryCertQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /country-certs/:id} : get the "id" countryCert.
     *
     * @param id the id of the countryCertDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryCertDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CountryCertDTO> getCountryCert(@PathVariable("id") Long id) {
        log.debug("REST request to get CountryCert : {}", id);
        Optional<CountryCertDTO> countryCertDTO = countryCertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryCertDTO);
    }

    /**
     * {@code DELETE  /country-certs/:id} : delete the "id" countryCert.
     *
     * @param id the id of the countryCertDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryCert(@PathVariable("id") Long id) {
        log.debug("REST request to delete CountryCert : {}", id);
        countryCertService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
