package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProdRepository;
import com.mycompany.myapp.service.ProdQueryService;
import com.mycompany.myapp.service.ProdService;
import com.mycompany.myapp.service.criteria.ProdCriteria;
import com.mycompany.myapp.service.dto.ProdDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Prod}.
 */
@RestController
@RequestMapping("/api/prods")
public class ProdResource {

    private final Logger log = LoggerFactory.getLogger(ProdResource.class);

    private static final String ENTITY_NAME = "prod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdService prodService;

    private final ProdRepository prodRepository;

    private final ProdQueryService prodQueryService;

    public ProdResource(ProdService prodService, ProdRepository prodRepository, ProdQueryService prodQueryService) {
        this.prodService = prodService;
        this.prodRepository = prodRepository;
        this.prodQueryService = prodQueryService;
    }

    /**
     * {@code POST  /prods} : Create a new prod.
     *
     * @param prodDTO the prodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodDTO, or with status {@code 400 (Bad Request)} if the prod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProdDTO> createProd(@Valid @RequestBody ProdDTO prodDTO) throws URISyntaxException {
        log.debug("REST request to save Prod : {}", prodDTO);
        if (prodDTO.getId() != null) {
            throw new BadRequestAlertException("A new prod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prodDTO = prodService.save(prodDTO);
        return ResponseEntity.created(new URI("/api/prods/" + prodDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, prodDTO.getId().toString()))
            .body(prodDTO);
    }

    /**
     * {@code PUT  /prods/:id} : Updates an existing prod.
     *
     * @param id the id of the prodDTO to save.
     * @param prodDTO the prodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodDTO,
     * or with status {@code 400 (Bad Request)} if the prodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdDTO> updateProd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProdDTO prodDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Prod : {}, {}", id, prodDTO);
        if (prodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prodDTO = prodService.update(prodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodDTO.getId().toString()))
            .body(prodDTO);
    }

    /**
     * {@code PATCH  /prods/:id} : Partial updates given fields of an existing prod, field will ignore if it is null
     *
     * @param id the id of the prodDTO to save.
     * @param prodDTO the prodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodDTO,
     * or with status {@code 400 (Bad Request)} if the prodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProdDTO> partialUpdateProd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProdDTO prodDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prod partially : {}, {}", id, prodDTO);
        if (prodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProdDTO> result = prodService.partialUpdate(prodDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prods} : get all the prods.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prods in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProdDTO>> getAllProds(
        ProdCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Prods by criteria: {}", criteria);

        Page<ProdDTO> page = prodQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prods/count} : count all the prods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProds(ProdCriteria criteria) {
        log.debug("REST request to count Prods by criteria: {}", criteria);
        return ResponseEntity.ok().body(prodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prods/:id} : get the "id" prod.
     *
     * @param id the id of the prodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdDTO> getProd(@PathVariable("id") Long id) {
        log.debug("REST request to get Prod : {}", id);
        Optional<ProdDTO> prodDTO = prodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prodDTO);
    }

    /**
     * {@code DELETE  /prods/:id} : delete the "id" prod.
     *
     * @param id the id of the prodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prod : {}", id);
        prodService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
