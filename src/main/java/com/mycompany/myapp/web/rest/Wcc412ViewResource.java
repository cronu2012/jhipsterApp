package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.Wcc412ViewQueryService;
import com.mycompany.myapp.service.Wcc412ViewService;
import com.mycompany.myapp.service.criteria.Wcc412ViewCriteria;
import com.mycompany.myapp.service.dto.Wcc412ViewDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Wcc412View}.
 */
@RestController
@RequestMapping("/api/wcc-412-views")
public class Wcc412ViewResource {

    private final Logger log = LoggerFactory.getLogger(Wcc412ViewResource.class);

    private final Wcc412ViewService wcc412ViewService;

    private final Wcc412ViewQueryService wcc412ViewQueryService;

    public Wcc412ViewResource(Wcc412ViewService wcc412ViewService, Wcc412ViewQueryService wcc412ViewQueryService) {
        this.wcc412ViewService = wcc412ViewService;
        this.wcc412ViewQueryService = wcc412ViewQueryService;
    }

    /**
     * {@code GET  /wcc-412-views} : get all the wcc412Views.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wcc412Views in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Wcc412ViewDTO>> getAllWcc412Views(
        Wcc412ViewCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Wcc412Views by criteria: {}", criteria);

        Page<Wcc412ViewDTO> page = wcc412ViewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wcc-412-views/count} : count all the wcc412Views.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWcc412Views(Wcc412ViewCriteria criteria) {
        log.debug("REST request to count Wcc412Views by criteria: {}", criteria);
        return ResponseEntity.ok().body(wcc412ViewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wcc-412-views/:id} : get the "id" wcc412View.
     *
     * @param id the id of the wcc412ViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wcc412ViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wcc412ViewDTO> getWcc412View(@PathVariable("id") Long id) {
        log.debug("REST request to get Wcc412View : {}", id);
        Optional<Wcc412ViewDTO> wcc412ViewDTO = wcc412ViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wcc412ViewDTO);
    }
}
