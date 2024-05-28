package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.Wcc421ViewQueryService;
import com.mycompany.myapp.service.Wcc421ViewService;
import com.mycompany.myapp.service.criteria.Wcc421ViewCriteria;
import com.mycompany.myapp.service.dto.Wcc421ViewDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Wcc421View}.
 */
@RestController
@RequestMapping("/api/wcc-421-views")
public class Wcc421ViewResource {

    private final Logger log = LoggerFactory.getLogger(Wcc421ViewResource.class);

    private final Wcc421ViewService wcc421ViewService;

    private final Wcc421ViewQueryService wcc421ViewQueryService;

    public Wcc421ViewResource(Wcc421ViewService wcc421ViewService, Wcc421ViewQueryService wcc421ViewQueryService) {
        this.wcc421ViewService = wcc421ViewService;
        this.wcc421ViewQueryService = wcc421ViewQueryService;
    }

    /**
     * {@code GET  /wcc-421-views} : get all the wcc421Views.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wcc421Views in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Wcc421ViewDTO>> getAllWcc421Views(
        Wcc421ViewCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Wcc421Views by criteria: {}", criteria);

        Page<Wcc421ViewDTO> page = wcc421ViewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wcc-421-views/count} : count all the wcc421Views.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWcc421Views(Wcc421ViewCriteria criteria) {
        log.debug("REST request to count Wcc421Views by criteria: {}", criteria);
        return ResponseEntity.ok().body(wcc421ViewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wcc-421-views/:id} : get the "id" wcc421View.
     *
     * @param id the id of the wcc421ViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wcc421ViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wcc421ViewDTO> getWcc421View(@PathVariable("id") Long id) {
        log.debug("REST request to get Wcc421View : {}", id);
        Optional<Wcc421ViewDTO> wcc421ViewDTO = wcc421ViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wcc421ViewDTO);
    }
}
