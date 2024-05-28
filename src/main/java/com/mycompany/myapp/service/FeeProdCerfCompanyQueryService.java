package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.FeeProdCerfCompany;
import com.mycompany.myapp.repository.FeeProdCerfCompanyRepository;
import com.mycompany.myapp.service.criteria.FeeProdCerfCompanyCriteria;
import com.mycompany.myapp.service.dto.FeeProdCerfCompanyDTO;
import com.mycompany.myapp.service.mapper.FeeProdCerfCompanyMapper;
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
 * Service for executing complex queries for {@link FeeProdCerfCompany} entities in the database.
 * The main input is a {@link FeeProdCerfCompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FeeProdCerfCompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeeProdCerfCompanyQueryService extends QueryService<FeeProdCerfCompany> {

    private final Logger log = LoggerFactory.getLogger(FeeProdCerfCompanyQueryService.class);

    private final FeeProdCerfCompanyRepository feeProdCerfCompanyRepository;

    private final FeeProdCerfCompanyMapper feeProdCerfCompanyMapper;

    public FeeProdCerfCompanyQueryService(
        FeeProdCerfCompanyRepository feeProdCerfCompanyRepository,
        FeeProdCerfCompanyMapper feeProdCerfCompanyMapper
    ) {
        this.feeProdCerfCompanyRepository = feeProdCerfCompanyRepository;
        this.feeProdCerfCompanyMapper = feeProdCerfCompanyMapper;
    }

    /**
     * Return a {@link Page} of {@link FeeProdCerfCompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeeProdCerfCompanyDTO> findByCriteria(FeeProdCerfCompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeeProdCerfCompany> specification = createSpecification(criteria);
        return feeProdCerfCompanyRepository.findAll(specification, page).map(feeProdCerfCompanyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeeProdCerfCompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeeProdCerfCompany> specification = createSpecification(criteria);
        return feeProdCerfCompanyRepository.count(specification);
    }

    /**
     * Function to convert {@link FeeProdCerfCompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeeProdCerfCompany> createSpecification(FeeProdCerfCompanyCriteria criteria) {
        Specification<FeeProdCerfCompany> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeeProdCerfCompany_.id));
            }
            if (criteria.getFee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFee(), FeeProdCerfCompany_.fee));
            }
            if (criteria.getFeeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeeType(), FeeProdCerfCompany_.feeType));
            }
            if (criteria.getFeeDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeeDt(), FeeProdCerfCompany_.feeDt));
            }
            if (criteria.getProdId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProdId(), root -> root.join(FeeProdCerfCompany_.prod, JoinType.LEFT).get(Prod_.id))
                );
            }
            if (criteria.getCerfId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCerfId(), root -> root.join(FeeProdCerfCompany_.cerf, JoinType.LEFT).get(Cerf_.id))
                );
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCompanyId(),
                        root -> root.join(FeeProdCerfCompany_.company, JoinType.LEFT).get(Company_.id)
                    )
                );
            }
        }
        return specification;
    }
}
