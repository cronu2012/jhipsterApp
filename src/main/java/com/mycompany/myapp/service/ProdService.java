package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.repository.ProdRepository;
import com.mycompany.myapp.service.dto.ProdDTO;
import com.mycompany.myapp.service.mapper.ProdMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Prod}.
 */
@Service
@Transactional
public class ProdService {

    private final Logger log = LoggerFactory.getLogger(ProdService.class);

    private final ProdRepository prodRepository;

    private final ProdMapper prodMapper;

    public ProdService(ProdRepository prodRepository, ProdMapper prodMapper) {
        this.prodRepository = prodRepository;
        this.prodMapper = prodMapper;
    }

    /**
     * Save a prod.
     *
     * @param prodDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdDTO save(ProdDTO prodDTO) {
        log.debug("Request to save Prod : {}", prodDTO);
        Prod prod = prodMapper.toEntity(prodDTO);
        prod = prodRepository.save(prod);
        return prodMapper.toDto(prod);
    }

    /**
     * Update a prod.
     *
     * @param prodDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdDTO update(ProdDTO prodDTO) {
        log.debug("Request to update Prod : {}", prodDTO);
        Prod prod = prodMapper.toEntity(prodDTO);
        prod = prodRepository.save(prod);
        return prodMapper.toDto(prod);
    }

    /**
     * Partially update a prod.
     *
     * @param prodDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProdDTO> partialUpdate(ProdDTO prodDTO) {
        log.debug("Request to partially update Prod : {}", prodDTO);

        return prodRepository
            .findById(prodDTO.getId())
            .map(existingProd -> {
                prodMapper.partialUpdate(existingProd, prodDTO);

                return existingProd;
            })
            .map(prodRepository::save)
            .map(prodMapper::toDto);
    }

    /**
     * Get one prod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdDTO> findOne(Long id) {
        log.debug("Request to get Prod : {}", id);
        return prodRepository.findById(id).map(prodMapper::toDto);
    }

    /**
     * Delete the prod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Prod : {}", id);
        prodRepository.deleteById(id);
    }
}
