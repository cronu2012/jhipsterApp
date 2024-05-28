package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProdStdRepository;
import com.mycompany.myapp.service.ProdStdQueryService;
import com.mycompany.myapp.service.ProdStdService;
import com.mycompany.myapp.service.criteria.ProdStdCriteria;
import com.mycompany.myapp.service.dto.ProdStdDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProdStd}.
 */
@RestController
@RequestMapping("/api/prod-stds")
public class ProdStdResource {

    private final Logger log = LoggerFactory.getLogger(ProdStdResource.class);

    private static final String ENTITY_NAME = "prodStd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdStdService prodStdService;

    private final ProdStdRepository prodStdRepository;

    private final ProdStdQueryService prodStdQueryService;

    public ProdStdResource(ProdStdService prodStdService, ProdStdRepository prodStdRepository, ProdStdQueryService prodStdQueryService) {
        this.prodStdService = prodStdService;
        this.prodStdRepository = prodStdRepository;
        this.prodStdQueryService = prodStdQueryService;
    }

    /**
     * {@code POST  /prod-stds} : Create a new prodStd.
     *
     * @param prodStdDTO the prodStdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodStdDTO, or with status {@code 400 (Bad Request)} if the prodStd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProdStdDTO> createProdStd(@Valid @RequestBody ProdStdDTO prodStdDTO) throws URISyntaxException {
        log.debug("REST request to save ProdStd : {}", prodStdDTO);
        if (prodStdDTO.getId() != null) {
            throw new BadRequestAlertException("A new prodStd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prodStdDTO = prodStdService.save(prodStdDTO);
        return ResponseEntity.created(new URI("/api/prod-stds/" + prodStdDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, prodStdDTO.getId().toString()))
            .body(prodStdDTO);
    }

    /**
     * {@code PUT  /prod-stds/:id} : Updates an existing prodStd.
     *
     * @param id the id of the prodStdDTO to save.
     * @param prodStdDTO the prodStdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodStdDTO,
     * or with status {@code 400 (Bad Request)} if the prodStdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodStdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdStdDTO> updateProdStd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProdStdDTO prodStdDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProdStd : {}, {}", id, prodStdDTO);
        if (prodStdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodStdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodStdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prodStdDTO = prodStdService.update(prodStdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodStdDTO.getId().toString()))
            .body(prodStdDTO);
    }

    /**
     * {@code PATCH  /prod-stds/:id} : Partial updates given fields of an existing prodStd, field will ignore if it is null
     *
     * @param id the id of the prodStdDTO to save.
     * @param prodStdDTO the prodStdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodStdDTO,
     * or with status {@code 400 (Bad Request)} if the prodStdDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prodStdDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prodStdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProdStdDTO> partialUpdateProdStd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProdStdDTO prodStdDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProdStd partially : {}, {}", id, prodStdDTO);
        if (prodStdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodStdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodStdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProdStdDTO> result = prodStdService.partialUpdate(prodStdDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodStdDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prod-stds} : get all the prodStds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prodStds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProdStdDTO>> getAllProdStds(
        ProdStdCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProdStds by criteria: {}", criteria);

        Page<ProdStdDTO> page = prodStdQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prod-stds/count} : count all the prodStds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProdStds(ProdStdCriteria criteria) {
        log.debug("REST request to count ProdStds by criteria: {}", criteria);
        return ResponseEntity.ok().body(prodStdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prod-stds/:id} : get the "id" prodStd.
     *
     * @param id the id of the prodStdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodStdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdStdDTO> getProdStd(@PathVariable("id") Long id) {
        log.debug("REST request to get ProdStd : {}", id);
        Optional<ProdStdDTO> prodStdDTO = prodStdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prodStdDTO);
    }

    /**
     * {@code DELETE  /prod-stds/:id} : delete the "id" prodStd.
     *
     * @param id the id of the prodStdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdStd(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProdStd : {}", id);
        prodStdService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
