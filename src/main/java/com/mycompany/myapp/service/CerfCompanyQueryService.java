package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CerfCompany;
import com.mycompany.myapp.repository.CerfCompanyRepository;
import com.mycompany.myapp.service.criteria.CerfCompanyCriteria;
import com.mycompany.myapp.service.dto.CerfCompanyDTO;
import com.mycompany.myapp.service.mapper.CerfCompanyMapper;
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
 * Service for executing complex queries for {@link CerfCompany} entities in the database.
 * The main input is a {@link CerfCompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CerfCompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CerfCompanyQueryService extends QueryService<CerfCompany> {

    private final Logger log = LoggerFactory.getLogger(CerfCompanyQueryService.class);

    private final CerfCompanyRepository cerfCompanyRepository;

    private final CerfCompanyMapper cerfCompanyMapper;

    public CerfCompanyQueryService(CerfCompanyRepository cerfCompanyRepository, CerfCompanyMapper cerfCompanyMapper) {
        this.cerfCompanyRepository = cerfCompanyRepository;
        this.cerfCompanyMapper = cerfCompanyMapper;
    }

    /**
     * Return a {@link Page} of {@link CerfCompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CerfCompanyDTO> findByCriteria(CerfCompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CerfCompany> specification = createSpecification(criteria);
        return cerfCompanyRepository.findAll(specification, page).map(cerfCompanyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CerfCompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CerfCompany> specification = createSpecification(criteria);
        return cerfCompanyRepository.count(specification);
    }

    /**
     * Function to convert {@link CerfCompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CerfCompany> createSpecification(CerfCompanyCriteria criteria) {
        Specification<CerfCompany> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CerfCompany_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), CerfCompany_.relType));
            }
            if (criteria.getCerfId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCerfId(), root -> root.join(CerfCompany_.cerf, JoinType.LEFT).get(Cerf_.id))
                );
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCompanyId(), root -> root.join(CerfCompany_.company, JoinType.LEFT).get(Company_.id))
                );
            }
        }
        return specification;
    }
}
