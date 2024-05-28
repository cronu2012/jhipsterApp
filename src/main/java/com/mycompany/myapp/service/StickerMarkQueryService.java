package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.StickerMark;
import com.mycompany.myapp.repository.StickerMarkRepository;
import com.mycompany.myapp.service.criteria.StickerMarkCriteria;
import com.mycompany.myapp.service.dto.StickerMarkDTO;
import com.mycompany.myapp.service.mapper.StickerMarkMapper;
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
 * Service for executing complex queries for {@link StickerMark} entities in the database.
 * The main input is a {@link StickerMarkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link StickerMarkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StickerMarkQueryService extends QueryService<StickerMark> {

    private final Logger log = LoggerFactory.getLogger(StickerMarkQueryService.class);

    private final StickerMarkRepository stickerMarkRepository;

    private final StickerMarkMapper stickerMarkMapper;

    public StickerMarkQueryService(StickerMarkRepository stickerMarkRepository, StickerMarkMapper stickerMarkMapper) {
        this.stickerMarkRepository = stickerMarkRepository;
        this.stickerMarkMapper = stickerMarkMapper;
    }

    /**
     * Return a {@link Page} of {@link StickerMarkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StickerMarkDTO> findByCriteria(StickerMarkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StickerMark> specification = createSpecification(criteria);
        return stickerMarkRepository.findAll(specification, page).map(stickerMarkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StickerMarkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StickerMark> specification = createSpecification(criteria);
        return stickerMarkRepository.count(specification);
    }

    /**
     * Function to convert {@link StickerMarkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StickerMark> createSpecification(StickerMarkCriteria criteria) {
        Specification<StickerMark> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StickerMark_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), StickerMark_.relType));
            }
            if (criteria.getStickerId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStickerId(), root -> root.join(StickerMark_.sticker, JoinType.LEFT).get(Sticker_.id))
                );
            }
            if (criteria.getMarkId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getMarkId(), root -> root.join(StickerMark_.mark, JoinType.LEFT).get(Mark_.id))
                );
            }
        }
        return specification;
    }
}
