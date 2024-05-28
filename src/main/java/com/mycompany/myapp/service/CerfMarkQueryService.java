package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CerfMark;
import com.mycompany.myapp.repository.CerfMarkRepository;
import com.mycompany.myapp.service.criteria.CerfMarkCriteria;
import com.mycompany.myapp.service.dto.CerfMarkDTO;
import com.mycompany.myapp.service.mapper.CerfMarkMapper;
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
 * Service for executing complex queries for {@link CerfMark} entities in the database.
 * The main input is a {@link CerfMarkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CerfMarkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CerfMarkQueryService extends QueryService<CerfMark> {

    private final Logger log = LoggerFactory.getLogger(CerfMarkQueryService.class);

    private final CerfMarkRepository cerfMarkRepository;

    private final CerfMarkMapper cerfMarkMapper;

    public CerfMarkQueryService(CerfMarkRepository cerfMarkRepository, CerfMarkMapper cerfMarkMapper) {
        this.cerfMarkRepository = cerfMarkRepository;
        this.cerfMarkMapper = cerfMarkMapper;
    }

    /**
     * Return a {@link Page} of {@link CerfMarkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CerfMarkDTO> findByCriteria(CerfMarkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CerfMark> specification = createSpecification(criteria);
        return cerfMarkRepository.findAll(specification, page).map(cerfMarkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CerfMarkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CerfMark> specification = createSpecification(criteria);
        return cerfMarkRepository.count(specification);
    }

    /**
     * Function to convert {@link CerfMarkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CerfMark> createSpecification(CerfMarkCriteria criteria) {
        Specification<CerfMark> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CerfMark_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), CerfMark_.relType));
            }
            if (criteria.getCerfId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCerfId(), root -> root.join(CerfMark_.cerf, JoinType.LEFT).get(Cerf_.id))
                );
            }
            if (criteria.getMarkId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getMarkId(), root -> root.join(CerfMark_.mark, JoinType.LEFT).get(Mark_.id))
                );
            }
        }
        return specification;
    }
}
