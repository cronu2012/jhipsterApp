package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.repository.CountryRepository;
import com.mycompany.myapp.service.criteria.CountryCriteria;
import com.mycompany.myapp.service.dto.CountryDTO;
import com.mycompany.myapp.service.mapper.CountryMapper;
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
 * Service for executing complex queries for {@link Country} entities in the database.
 * The main input is a {@link CountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CountryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryQueryService extends QueryService<Country> {

    private final Logger log = LoggerFactory.getLogger(CountryQueryService.class);

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    public CountryQueryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    /**
     * Return a {@link Page} of {@link CountryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryDTO> findByCriteria(CountryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.findAll(specification, page).map(countryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.count(specification);
    }

    /**
     * Function to convert {@link CountryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Country> createSpecification(CountryCriteria criteria) {
        Specification<Country> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Country_.id));
            }
            if (criteria.getCountryNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryNo(), Country_.countryNo));
            }
            if (criteria.getEnName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnName(), Country_.enName));
            }
            if (criteria.getChName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChName(), Country_.chName));
            }
            if (criteria.getProdCountryId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getProdCountryId(),
                        root -> root.join(Country_.prodCountries, JoinType.LEFT).get(ProdCountry_.id)
                    )
                );
            }
            if (criteria.getCountryStdId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCountryStdId(),
                        root -> root.join(Country_.countryStds, JoinType.LEFT).get(CountryStd_.id)
                    )
                );
            }
            if (criteria.getCountryCertId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCountryCertId(),
                        root -> root.join(Country_.countryCerts, JoinType.LEFT).get(CountryCert_.id)
                    )
                );
            }
            if (criteria.getCountryMarkId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCountryMarkId(),
                        root -> root.join(Country_.countryMarks, JoinType.LEFT).get(CountryMark_.id)
                    )
                );
            }
        }
        return specification;
    }
}
