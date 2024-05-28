package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ProdSticker;
import com.mycompany.myapp.repository.ProdStickerRepository;
import com.mycompany.myapp.service.criteria.ProdStickerCriteria;
import com.mycompany.myapp.service.dto.ProdStickerDTO;
import com.mycompany.myapp.service.mapper.ProdStickerMapper;
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
 * Service for executing complex queries for {@link ProdSticker} entities in the database.
 * The main input is a {@link ProdStickerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProdStickerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdStickerQueryService extends QueryService<ProdSticker> {

    private final Logger log = LoggerFactory.getLogger(ProdStickerQueryService.class);

    private final ProdStickerRepository prodStickerRepository;

    private final ProdStickerMapper prodStickerMapper;

    public ProdStickerQueryService(ProdStickerRepository prodStickerRepository, ProdStickerMapper prodStickerMapper) {
        this.prodStickerRepository = prodStickerRepository;
        this.prodStickerMapper = prodStickerMapper;
    }

    /**
     * Return a {@link Page} of {@link ProdStickerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdStickerDTO> findByCriteria(ProdStickerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProdSticker> specification = createSpecification(criteria);
        return prodStickerRepository.findAll(specification, page).map(prodStickerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdStickerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProdSticker> specification = createSpecification(criteria);
        return prodStickerRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdStickerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProdSticker> createSpecification(ProdStickerCriteria criteria) {
        Specification<ProdSticker> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProdSticker_.id));
            }
            if (criteria.getRelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelType(), ProdSticker_.relType));
            }
            if (criteria.getProdId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProdId(), root -> root.join(ProdSticker_.prod, JoinType.LEFT).get(Prod_.id))
                );
            }
            if (criteria.getStickerId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStickerId(), root -> root.join(ProdSticker_.sticker, JoinType.LEFT).get(Sticker_.id))
                );
            }
        }
        return specification;
    }
}
