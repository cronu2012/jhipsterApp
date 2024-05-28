package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.StickerRepository;
import com.mycompany.myapp.service.StickerQueryService;
import com.mycompany.myapp.service.StickerService;
import com.mycompany.myapp.service.criteria.StickerCriteria;
import com.mycompany.myapp.service.dto.StickerDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Sticker}.
 */
@RestController
@RequestMapping("/api/stickers")
public class StickerResource {

    private final Logger log = LoggerFactory.getLogger(StickerResource.class);

    private static final String ENTITY_NAME = "sticker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StickerService stickerService;

    private final StickerRepository stickerRepository;

    private final StickerQueryService stickerQueryService;

    public StickerResource(StickerService stickerService, StickerRepository stickerRepository, StickerQueryService stickerQueryService) {
        this.stickerService = stickerService;
        this.stickerRepository = stickerRepository;
        this.stickerQueryService = stickerQueryService;
    }

    /**
     * {@code POST  /stickers} : Create a new sticker.
     *
     * @param stickerDTO the stickerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stickerDTO, or with status {@code 400 (Bad Request)} if the sticker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StickerDTO> createSticker(@Valid @RequestBody StickerDTO stickerDTO) throws URISyntaxException {
        log.debug("REST request to save Sticker : {}", stickerDTO);
        if (stickerDTO.getId() != null) {
            throw new BadRequestAlertException("A new sticker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stickerDTO = stickerService.save(stickerDTO);
        return ResponseEntity.created(new URI("/api/stickers/" + stickerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, stickerDTO.getId().toString()))
            .body(stickerDTO);
    }

    /**
     * {@code PUT  /stickers/:id} : Updates an existing sticker.
     *
     * @param id the id of the stickerDTO to save.
     * @param stickerDTO the stickerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stickerDTO,
     * or with status {@code 400 (Bad Request)} if the stickerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stickerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StickerDTO> updateSticker(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StickerDTO stickerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sticker : {}, {}", id, stickerDTO);
        if (stickerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stickerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stickerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stickerDTO = stickerService.update(stickerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stickerDTO.getId().toString()))
            .body(stickerDTO);
    }

    /**
     * {@code PATCH  /stickers/:id} : Partial updates given fields of an existing sticker, field will ignore if it is null
     *
     * @param id the id of the stickerDTO to save.
     * @param stickerDTO the stickerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stickerDTO,
     * or with status {@code 400 (Bad Request)} if the stickerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stickerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stickerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StickerDTO> partialUpdateSticker(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StickerDTO stickerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sticker partially : {}, {}", id, stickerDTO);
        if (stickerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stickerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stickerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StickerDTO> result = stickerService.partialUpdate(stickerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stickerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stickers} : get all the stickers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stickers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StickerDTO>> getAllStickers(
        StickerCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Stickers by criteria: {}", criteria);

        Page<StickerDTO> page = stickerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stickers/count} : count all the stickers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStickers(StickerCriteria criteria) {
        log.debug("REST request to count Stickers by criteria: {}", criteria);
        return ResponseEntity.ok().body(stickerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stickers/:id} : get the "id" sticker.
     *
     * @param id the id of the stickerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stickerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StickerDTO> getSticker(@PathVariable("id") Long id) {
        log.debug("REST request to get Sticker : {}", id);
        Optional<StickerDTO> stickerDTO = stickerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stickerDTO);
    }

    /**
     * {@code DELETE  /stickers/:id} : delete the "id" sticker.
     *
     * @param id the id of the stickerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sticker : {}", id);
        stickerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
