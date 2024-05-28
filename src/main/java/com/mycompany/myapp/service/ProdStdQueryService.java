package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ProdStd;
import com.mycompany.myapp.repository.ProdStdRepository;
import com.mycompany.myapp.service.criteria.ProdStdCriteria;
import com.mycompany.myapp.service.dto.ProdStdDTO;
import com.mycompany.myapp.service.mapper.ProdStdMapper;
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
 * Service for executing complex queries for {@link ProdStd} entities in the database.
 * The main input is a {@link ProdStdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProdStdDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdStdQueryService extends QueryService<ProdStd> {

    private final Logger log = LoggerFactory.getLogger(ProdStdQueryService.class);

    private final ProdStdRepository prodStdRepository;

    private final ProdStdMapper prodStdMapper;

    public ProdStdQueryService(ProdStdRepository prodStdRepository, ProdStdMapper prodStdMapper) {
        this.prodStdRepository = prodStdRepository;
        this.prodStdMapper = prodStdMapper;
    }

    /**
     * Return a {@link Page} of {@link ProdStdDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdStdDTO> findByCriteria(ProdStdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProdStd> specification = createSpecification(criteria);
        return prodStdRepository.findAll(specification, page).map(prodStdMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdStdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProdStd> specification = createSpecification(criteria);
        return prodStdRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdStdCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProdStd> createSpecification(ProdStdCriteria criteria) {
        Specification<ProdStd> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProdStd_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), ProdStd_.relType));
            }
            if (criteria.getProdId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProdId(), root -> root.join(ProdStd_.prod, JoinType.LEFT).get(Prod_.id))
                );
            }
            if (criteria.getStdId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStdId(), root -> root.join(ProdStd_.std, JoinType.LEFT).get(Std_.id))
                );
            }
        }
        return specification;
    }
}
