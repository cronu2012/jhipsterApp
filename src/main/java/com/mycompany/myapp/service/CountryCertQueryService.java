package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CountryCert;
import com.mycompany.myapp.repository.CountryCertRepository;
import com.mycompany.myapp.service.criteria.CountryCertCriteria;
import com.mycompany.myapp.service.dto.CountryCertDTO;
import com.mycompany.myapp.service.mapper.CountryCertMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CountryCert} entities in the database.
 * The main input is a {@link CountryCertCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CountryCertDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryCertQueryService extends QueryService<CountryCert> {

    private final Logger log = LoggerFactory.getLogger(CountryCertQueryService.class);

    private final CountryCertRepository countryCertRepository;

    private final CountryCertMapper countryCertMapper;

    public CountryCertQueryService(CountryCertRepository countryCertRepository, CountryCertMapper countryCertMapper) {
        this.countryCertRepository = countryCertRepository;
        this.countryCertMapper = countryCertMapper;
    }

    /**
     * Return a {@link Page} of {@link CountryCertDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryCertDTO> findByCriteria(CountryCertCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CountryCert> specification = createSpecification(criteria);
        return countryCertRepository.findAll(specification, page).map(countryCertMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryCertCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CountryCert> specification = createSpecification(criteria);
        return countryCertRepository.count(specification);
    }

    /**
     * Function to convert {@link CountryCertCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CountryCert> createSpecification(CountryCertCriteria criteria) {
        Specification<CountryCert> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CountryCert_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), CountryCert_.relType));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCountryId(), root -> root.join(CountryCert_.country, JoinType.LEFT).get(Country_.id))
                );
            }
            if (criteria.getCerfId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCerfId(), root -> root.join(CountryCert_.cerf, JoinType.LEFT).get(Cerf_.id))
                );
            }
        }
        return specification;
    }
}
