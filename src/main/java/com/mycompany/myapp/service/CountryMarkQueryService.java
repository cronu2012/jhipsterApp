package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CountryMark;
import com.mycompany.myapp.repository.CountryMarkRepository;
import com.mycompany.myapp.service.criteria.CountryMarkCriteria;
import com.mycompany.myapp.service.dto.CountryMarkDTO;
import com.mycompany.myapp.service.mapper.CountryMarkMapper;
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
 * Service for executing complex queries for {@link CountryMark} entities in the database.
 * The main input is a {@link CountryMarkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CountryMarkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryMarkQueryService extends QueryService<CountryMark> {

    private final Logger log = LoggerFactory.getLogger(CountryMarkQueryService.class);

    private final CountryMarkRepository countryMarkRepository;

    private final CountryMarkMapper countryMarkMapper;

    public CountryMarkQueryService(CountryMarkRepository countryMarkRepository, CountryMarkMapper countryMarkMapper) {
        this.countryMarkRepository = countryMarkRepository;
        this.countryMarkMapper = countryMarkMapper;
    }

    /**
     * Return a {@link Page} of {@link CountryMarkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryMarkDTO> findByCriteria(CountryMarkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CountryMark> specification = createSpecification(criteria);
        return countryMarkRepository.findAll(specification, page).map(countryMarkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryMarkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CountryMark> specification = createSpecification(criteria);
        return countryMarkRepository.count(specification);
    }

    /**
     * Function to convert {@link CountryMarkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CountryMark> createSpecification(CountryMarkCriteria criteria) {
        Specification<CountryMark> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CountryMark_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), CountryMark_.relType));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCountryId(), root -> root.join(CountryMark_.country, JoinType.LEFT).get(Country_.id))
                );
            }
            if (criteria.getMarkId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getMarkId(), root -> root.join(CountryMark_.mark, JoinType.LEFT).get(Mark_.id))
                );
            }
        }
        return specification;
    }
}
