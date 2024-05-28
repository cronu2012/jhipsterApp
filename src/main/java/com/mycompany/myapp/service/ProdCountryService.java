package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProdCountry;
import com.mycompany.myapp.repository.ProdCountryRepository;
import com.mycompany.myapp.service.dto.ProdCountryDTO;
import com.mycompany.myapp.service.mapper.ProdCountryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ProdCountry}.
 */
@Service
@Transactional
public class ProdCountryService {

    private final Logger log = LoggerFactory.getLogger(ProdCountryService.class);

    private final ProdCountryRepository prodCountryRepository;

    private final ProdCountryMapper prodCountryMapper;

    public ProdCountryService(ProdCountryRepository prodCountryRepository, ProdCountryMapper prodCountryMapper) {
        this.prodCountryRepository = prodCountryRepository;
        this.prodCountryMapper = prodCountryMapper;
    }

    /**
     * Save a prodCountry.
     *
     * @param prodCountryDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdCountryDTO save(ProdCountryDTO prodCountryDTO) {
        log.debug("Request to save ProdCountry : {}", prodCountryDTO);
        ProdCountry prodCountry = prodCountryMapper.toEntity(prodCountryDTO);
        prodCountry = prodCountryRepository.save(prodCountry);
        return prodCountryMapper.toDto(prodCountry);
    }

    /**
     * Update a prodCountry.
     *
     * @param prodCountryDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdCountryDTO update(ProdCountryDTO prodCountryDTO) {
        log.debug("Request to update ProdCountry : {}", prodCountryDTO);
        ProdCountry prodCountry = prodCountryMapper.toEntity(prodCountryDTO);
        prodCountry = prodCountryRepository.save(prodCountry);
        return prodCountryMapper.toDto(prodCountry);
    }

    /**
     * Partially update a prodCountry.
     *
     * @param prodCountryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProdCountryDTO> partialUpdate(ProdCountryDTO prodCountryDTO) {
        log.debug("Request to partially update ProdCountry : {}", prodCountryDTO);

        return prodCountryRepository
            .findById(prodCountryDTO.getId())
            .map(existingProdCountry -> {
                prodCountryMapper.partialUpdate(existingProdCountry, prodCountryDTO);

                return existingProdCountry;
            })
            .map(prodCountryRepository::save)
            .map(prodCountryMapper::toDto);
    }

    /**
     * Get one prodCountry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdCountryDTO> findOne(Long id) {
        log.debug("Request to get ProdCountry : {}", id);
        return prodCountryRepository.findById(id).map(prodCountryMapper::toDto);
    }

    /**
     * Delete the prodCountry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProdCountry : {}", id);
        prodCountryRepository.deleteById(id);
    }
}
