package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Wcc412View;
import com.mycompany.myapp.repository.Wcc412ViewRepository;
import com.mycompany.myapp.service.criteria.Wcc412ViewCriteria;
import com.mycompany.myapp.service.dto.Wcc412ViewDTO;
import com.mycompany.myapp.service.mapper.Wcc412ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Wcc412View} entities in the database.
 * The main input is a {@link Wcc412ViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Wcc412ViewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Wcc412ViewQueryService extends QueryService<Wcc412View> {

    private final Logger log = LoggerFactory.getLogger(Wcc412ViewQueryService.class);

    private final Wcc412ViewRepository wcc412ViewRepository;

    private final Wcc412ViewMapper wcc412ViewMapper;

    public Wcc412ViewQueryService(Wcc412ViewRepository wcc412ViewRepository, Wcc412ViewMapper wcc412ViewMapper) {
        this.wcc412ViewRepository = wcc412ViewRepository;
        this.wcc412ViewMapper = wcc412ViewMapper;
    }

    /**
     * Return a {@link Page} of {@link Wcc412ViewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Wcc412ViewDTO> findByCriteria(Wcc412ViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Wcc412View> specification = createSpecification(criteria);
        return wcc412ViewRepository.findAll(specification, page).map(wcc412ViewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Wcc412ViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Wcc412View> specification = createSpecification(criteria);
        return wcc412ViewRepository.count(specification);
    }

    /**
     * Function to convert {@link Wcc412ViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Wcc412View> createSpecification(Wcc412ViewCriteria criteria) {
        Specification<Wcc412View> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Wcc412View_.id));
            }
            if (criteria.getCerfId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCerfId(), Wcc412View_.cerfId));
            }
            if (criteria.getCountryChName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryChName(), Wcc412View_.countryChName));
            }
            if (criteria.getCerfNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCerfNo(), Wcc412View_.cerfNo));
            }
            if (criteria.getCerfVer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCerfVer(), Wcc412View_.cerfVer));
            }
            if (criteria.getCerfStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCerfStatus(), Wcc412View_.cerfStatus));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCountryId(), Wcc412View_.countryId));
            }
            if (criteria.getProdNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProdNo(), Wcc412View_.prodNo));
            }
            if (criteria.getProdChName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProdChName(), Wcc412View_.prodChName));
            }
        }
        return specification;
    }
}
