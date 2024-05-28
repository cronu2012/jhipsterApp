package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ProdCountry;
import com.mycompany.myapp.repository.ProdCountryRepository;
import com.mycompany.myapp.service.criteria.ProdCountryCriteria;
import com.mycompany.myapp.service.dto.ProdCountryDTO;
import com.mycompany.myapp.service.mapper.ProdCountryMapper;
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
 * Service for executing complex queries for {@link ProdCountry} entities in the database.
 * The main input is a {@link ProdCountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProdCountryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdCountryQueryService extends QueryService<ProdCountry> {

    private final Logger log = LoggerFactory.getLogger(ProdCountryQueryService.class);

    private final ProdCountryRepository prodCountryRepository;

    private final ProdCountryMapper prodCountryMapper;

    public ProdCountryQueryService(ProdCountryRepository prodCountryRepository, ProdCountryMapper prodCountryMapper) {
        this.prodCountryRepository = prodCountryRepository;
        this.prodCountryMapper = prodCountryMapper;
    }

    /**
     * Return a {@link Page} of {@link ProdCountryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdCountryDTO> findByCriteria(ProdCountryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProdCountry> specification = createSpecification(criteria);
        return prodCountryRepository.findAll(specification, page).map(prodCountryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdCountryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProdCountry> specification = createSpecification(criteria);
        return prodCountryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdCountryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProdCountry> createSpecification(ProdCountryCriteria criteria) {
        Specification<ProdCountry> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProdCountry_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), ProdCountry_.relType));
            }
            if (criteria.getProdId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProdId(), root -> root.join(ProdCountry_.prod, JoinType.LEFT).get(Prod_.id))
                );
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCountryId(), root -> root.join(ProdCountry_.country, JoinType.LEFT).get(Country_.id))
                );
            }
        }
        return specification;
    }
}
