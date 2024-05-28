package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.repository.CerfRepository;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.mapper.CerfMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Cerf}.
 */
@Service
@Transactional
public class CerfService {

    private final Logger log = LoggerFactory.getLogger(CerfService.class);

    private final CerfRepository cerfRepository;

    private final CerfMapper cerfMapper;

    public CerfService(CerfRepository cerfRepository, CerfMapper cerfMapper) {
        this.cerfRepository = cerfRepository;
        this.cerfMapper = cerfMapper;
    }

    /**
     * Save a cerf.
     *
     * @param cerfDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfDTO save(CerfDTO cerfDTO) {
        log.debug("Request to save Cerf : {}", cerfDTO);
        Cerf cerf = cerfMapper.toEntity(cerfDTO);
        cerf = cerfRepository.save(cerf);
        return cerfMapper.toDto(cerf);
    }

    /**
     * Update a cerf.
     *
     * @param cerfDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfDTO update(CerfDTO cerfDTO) {
        log.debug("Request to update Cerf : {}", cerfDTO);
        Cerf cerf = cerfMapper.toEntity(cerfDTO);
        cerf = cerfRepository.save(cerf);
        return cerfMapper.toDto(cerf);
    }

    /**
     * Partially update a cerf.
     *
     * @param cerfDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CerfDTO> partialUpdate(CerfDTO cerfDTO) {
        log.debug("Request to partially update Cerf : {}", cerfDTO);

        return cerfRepository
            .findById(cerfDTO.getId())
            .map(existingCerf -> {
                cerfMapper.partialUpdate(existingCerf, cerfDTO);

                return existingCerf;
            })
            .map(cerfRepository::save)
            .map(cerfMapper::toDto);
    }

    /**
     * Get one cerf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CerfDTO> findOne(Long id) {
        log.debug("Request to get Cerf : {}", id);
        return cerfRepository.findById(id).map(cerfMapper::toDto);
    }

    /**
     * Delete the cerf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cerf : {}", id);
        cerfRepository.deleteById(id);
    }
}
