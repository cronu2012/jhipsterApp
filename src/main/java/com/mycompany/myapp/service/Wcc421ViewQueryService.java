package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Wcc421View;
import com.mycompany.myapp.repository.Wcc421ViewRepository;
import com.mycompany.myapp.service.criteria.Wcc421ViewCriteria;
import com.mycompany.myapp.service.dto.Wcc421ViewDTO;
import com.mycompany.myapp.service.mapper.Wcc421ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Wcc421View} entities in the database.
 * The main input is a {@link Wcc421ViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Wcc421ViewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Wcc421ViewQueryService extends QueryService<Wcc421View> {

    private final Logger log = LoggerFactory.getLogger(Wcc421ViewQueryService.class);

    private final Wcc421ViewRepository wcc421ViewRepository;

    private final Wcc421ViewMapper wcc421ViewMapper;

    public Wcc421ViewQueryService(Wcc421ViewRepository wcc421ViewRepository, Wcc421ViewMapper wcc421ViewMapper) {
        this.wcc421ViewRepository = wcc421ViewRepository;
        this.wcc421ViewMapper = wcc421ViewMapper;
    }

    /**
     * Return a {@link Page} of {@link Wcc421ViewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Wcc421ViewDTO> findByCriteria(Wcc421ViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Wcc421View> specification = createSpecification(criteria);
        return wcc421ViewRepository.findAll(specification, page).map(wcc421ViewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Wcc421ViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Wcc421View> specification = createSpecification(criteria);
        return wcc421ViewRepository.count(specification);
    }

    /**
     * Function to convert {@link Wcc421ViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Wcc421View> createSpecification(Wcc421ViewCriteria criteria) {
        Specification<Wcc421View> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Wcc421View_.id));
            }
            if (criteria.getCerfId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCerfId(), Wcc421View_.cerfId));
            }
            if (criteria.getCountryChName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryChName(), Wcc421View_.countryChName));
            }
            if (criteria.getCerfNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCerfNo(), Wcc421View_.cerfNo));
            }
            if (criteria.getCerfVer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCerfVer(), Wcc421View_.cerfVer));
            }
            if (criteria.getCerfStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCerfStatus(), Wcc421View_.cerfStatus));
            }
            if (criteria.getCompanyChName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyChName(), Wcc421View_.companyChName));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), Wcc421View_.relType));
            }
        }
        return specification;
    }
}
