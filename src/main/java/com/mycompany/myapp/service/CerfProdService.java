package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CerfProd;
import com.mycompany.myapp.repository.CerfProdRepository;
import com.mycompany.myapp.service.dto.CerfProdDTO;
import com.mycompany.myapp.service.mapper.CerfProdMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CerfProd}.
 */
@Service
@Transactional
public class CerfProdService {

    private final Logger log = LoggerFactory.getLogger(CerfProdService.class);

    private final CerfProdRepository cerfProdRepository;

    private final CerfProdMapper cerfProdMapper;

    public CerfProdService(CerfProdRepository cerfProdRepository, CerfProdMapper cerfProdMapper) {
        this.cerfProdRepository = cerfProdRepository;
        this.cerfProdMapper = cerfProdMapper;
    }

    /**
     * Save a cerfProd.
     *
     * @param cerfProdDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfProdDTO save(CerfProdDTO cerfProdDTO) {
        log.debug("Request to save CerfProd : {}", cerfProdDTO);
        CerfProd cerfProd = cerfProdMapper.toEntity(cerfProdDTO);
        cerfProd = cerfProdRepository.save(cerfProd);
        return cerfProdMapper.toDto(cerfProd);
    }

    /**
     * Update a cerfProd.
     *
     * @param cerfProdDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfProdDTO update(CerfProdDTO cerfProdDTO) {
        log.debug("Request to update CerfProd : {}", cerfProdDTO);
        CerfProd cerfProd = cerfProdMapper.toEntity(cerfProdDTO);
        cerfProd = cerfProdRepository.save(cerfProd);
        return cerfProdMapper.toDto(cerfProd);
    }

    /**
     * Partially update a cerfProd.
     *
     * @param cerfProdDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CerfProdDTO> partialUpdate(CerfProdDTO cerfProdDTO) {
        log.debug("Request to partially update CerfProd : {}", cerfProdDTO);

        return cerfProdRepository
            .findById(cerfProdDTO.getId())
            .map(existingCerfProd -> {
                cerfProdMapper.partialUpdate(existingCerfProd, cerfProdDTO);

                return existingCerfProd;
            })
            .map(cerfProdRepository::save)
            .map(cerfProdMapper::toDto);
    }

    /**
     * Get one cerfProd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CerfProdDTO> findOne(Long id) {
        log.debug("Request to get CerfProd : {}", id);
        return cerfProdRepository.findById(id).map(cerfProdMapper::toDto);
    }

    /**
     * Delete the cerfProd by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CerfProd : {}", id);
        cerfProdRepository.deleteById(id);
    }
}
