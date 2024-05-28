package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CountryMarkRepository;
import com.mycompany.myapp.service.CountryMarkQueryService;
import com.mycompany.myapp.service.CountryMarkService;
import com.mycompany.myapp.service.criteria.CountryMarkCriteria;
import com.mycompany.myapp.service.dto.CountryMarkDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CountryMark}.
 */
@RestController
@RequestMapping("/api/country-marks")
public class CountryMarkResource {

    private final Logger log = LoggerFactory.getLogger(CountryMarkResource.class);

    private static final String ENTITY_NAME = "countryMark";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryMarkService countryMarkService;

    private final CountryMarkRepository countryMarkRepository;

    private final CountryMarkQueryService countryMarkQueryService;

    public CountryMarkResource(
        CountryMarkService countryMarkService,
        CountryMarkRepository countryMarkRepository,
        CountryMarkQueryService countryMarkQueryService
    ) {
        this.countryMarkService = countryMarkService;
        this.countryMarkRepository = countryMarkRepository;
        this.countryMarkQueryService = countryMarkQueryService;
    }

    /**
     * {@code POST  /country-marks} : Create a new countryMark.
     *
     * @param countryMarkDTO the countryMarkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryMarkDTO, or with status {@code 400 (Bad Request)} if the countryMark has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CountryMarkDTO> createCountryMark(@Valid @RequestBody CountryMarkDTO countryMarkDTO) throws URISyntaxException {
        log.debug("REST request to save CountryMark : {}", countryMarkDTO);
        if (countryMarkDTO.getId() != null) {
            throw new BadRequestAlertException("A new countryMark cannot already have an ID", ENTITY_NAME, "idexists");
        }
        countryMarkDTO = countryMarkService.save(countryMarkDTO);
        return ResponseEntity.created(new URI("/api/country-marks/" + countryMarkDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, countryMarkDTO.getId().toString()))
            .body(countryMarkDTO);
    }

    /**
     * {@code PUT  /country-marks/:id} : Updates an existing countryMark.
     *
     * @param id the id of the countryMarkDTO to save.
     * @param countryMarkDTO the countryMarkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryMarkDTO,
     * or with status {@code 400 (Bad Request)} if the countryMarkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryMarkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CountryMarkDTO> updateCountryMark(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CountryMarkDTO countryMarkDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CountryMark : {}, {}", id, countryMarkDTO);
        if (countryMarkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countryMarkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryMarkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        countryMarkDTO = countryMarkService.update(countryMarkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryMarkDTO.getId().toString()))
            .body(countryMarkDTO);
    }

    /**
     * {@code PATCH  /country-marks/:id} : Partial updates given fields of an existing countryMark, field will ignore if it is null
     *
     * @param id the id of the countryMarkDTO to save.
     * @param countryMarkDTO the countryMarkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryMarkDTO,
     * or with status {@code 400 (Bad Request)} if the countryMarkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the countryMarkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the countryMarkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CountryMarkDTO> partialUpdateCountryMark(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CountryMarkDTO countryMarkDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CountryMark partially : {}, {}", id, countryMarkDTO);
        if (countryMarkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countryMarkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryMarkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CountryMarkDTO> result = countryMarkService.partialUpdate(countryMarkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryMarkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /country-marks} : get all the countryMarks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countryMarks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CountryMarkDTO>> getAllCountryMarks(
        CountryMarkCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CountryMarks by criteria: {}", criteria);

        Page<CountryMarkDTO> page = countryMarkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /country-marks/count} : count all the countryMarks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCountryMarks(CountryMarkCriteria criteria) {
        log.debug("REST request to count CountryMarks by criteria: {}", criteria);
        return ResponseEntity.ok().body(countryMarkQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /country-marks/:id} : get the "id" countryMark.
     *
     * @param id the id of the countryMarkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryMarkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CountryMarkDTO> getCountryMark(@PathVariable("id") Long id) {
        log.debug("REST request to get CountryMark : {}", id);
        Optional<CountryMarkDTO> countryMarkDTO = countryMarkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryMarkDTO);
    }

    /**
     * {@code DELETE  /country-marks/:id} : delete the "id" countryMark.
     *
     * @param id the id of the countryMarkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryMark(@PathVariable("id") Long id) {
        log.debug("REST request to delete CountryMark : {}", id);
        countryMarkService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
