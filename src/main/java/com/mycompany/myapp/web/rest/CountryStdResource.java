package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CountryStdRepository;
import com.mycompany.myapp.service.CountryStdQueryService;
import com.mycompany.myapp.service.CountryStdService;
import com.mycompany.myapp.service.criteria.CountryStdCriteria;
import com.mycompany.myapp.service.dto.CountryStdDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CountryStd}.
 */
@RestController
@RequestMapping("/api/country-stds")
public class CountryStdResource {

    private final Logger log = LoggerFactory.getLogger(CountryStdResource.class);

    private static final String ENTITY_NAME = "countryStd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryStdService countryStdService;

    private final CountryStdRepository countryStdRepository;

    private final CountryStdQueryService countryStdQueryService;

    public CountryStdResource(
        CountryStdService countryStdService,
        CountryStdRepository countryStdRepository,
        CountryStdQueryService countryStdQueryService
    ) {
        this.countryStdService = countryStdService;
        this.countryStdRepository = countryStdRepository;
        this.countryStdQueryService = countryStdQueryService;
    }

    /**
     * {@code POST  /country-stds} : Create a new countryStd.
     *
     * @param countryStdDTO the countryStdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryStdDTO, or with status {@code 400 (Bad Request)} if the countryStd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CountryStdDTO> createCountryStd(@Valid @RequestBody CountryStdDTO countryStdDTO) throws URISyntaxException {
        log.debug("REST request to save CountryStd : {}", countryStdDTO);
        if (countryStdDTO.getId() != null) {
            throw new BadRequestAlertException("A new countryStd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        countryStdDTO = countryStdService.save(countryStdDTO);
        return ResponseEntity.created(new URI("/api/country-stds/" + countryStdDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, countryStdDTO.getId().toString()))
            .body(countryStdDTO);
    }

    /**
     * {@code PUT  /country-stds/:id} : Updates an existing countryStd.
     *
     * @param id the id of the countryStdDTO to save.
     * @param countryStdDTO the countryStdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryStdDTO,
     * or with status {@code 400 (Bad Request)} if the countryStdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryStdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CountryStdDTO> updateCountryStd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CountryStdDTO countryStdDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CountryStd : {}, {}", id, countryStdDTO);
        if (countryStdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countryStdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryStdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        countryStdDTO = countryStdService.update(countryStdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryStdDTO.getId().toString()))
            .body(countryStdDTO);
    }

    /**
     * {@code PATCH  /country-stds/:id} : Partial updates given fields of an existing countryStd, field will ignore if it is null
     *
     * @param id the id of the countryStdDTO to save.
     * @param countryStdDTO the countryStdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryStdDTO,
     * or with status {@code 400 (Bad Request)} if the countryStdDTO is not valid,
     * or with status {@code 404 (Not Found)} if the countryStdDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the countryStdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CountryStdDTO> partialUpdateCountryStd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CountryStdDTO countryStdDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CountryStd partially : {}, {}", id, countryStdDTO);
        if (countryStdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countryStdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryStdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CountryStdDTO> result = countryStdService.partialUpdate(countryStdDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryStdDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /country-stds} : get all the countryStds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countryStds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CountryStdDTO>> getAllCountryStds(
        CountryStdCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CountryStds by criteria: {}", criteria);

        Page<CountryStdDTO> page = countryStdQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /country-stds/count} : count all the countryStds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCountryStds(CountryStdCriteria criteria) {
        log.debug("REST request to count CountryStds by criteria: {}", criteria);
        return ResponseEntity.ok().body(countryStdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /country-stds/:id} : get the "id" countryStd.
     *
     * @param id the id of the countryStdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryStdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CountryStdDTO> getCountryStd(@PathVariable("id") Long id) {
        log.debug("REST request to get CountryStd : {}", id);
        Optional<CountryStdDTO> countryStdDTO = countryStdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryStdDTO);
    }

    /**
     * {@code DELETE  /country-stds/:id} : delete the "id" countryStd.
     *
     * @param id the id of the countryStdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryStd(@PathVariable("id") Long id) {
        log.debug("REST request to delete CountryStd : {}", id);
        countryStdService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
