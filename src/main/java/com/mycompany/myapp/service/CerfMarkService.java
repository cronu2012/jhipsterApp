package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CerfMark;
import com.mycompany.myapp.repository.CerfMarkRepository;
import com.mycompany.myapp.service.dto.CerfMarkDTO;
import com.mycompany.myapp.service.mapper.CerfMarkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CerfMark}.
 */
@Service
@Transactional
public class CerfMarkService {

    private final Logger log = LoggerFactory.getLogger(CerfMarkService.class);

    private final CerfMarkRepository cerfMarkRepository;

    private final CerfMarkMapper cerfMarkMapper;

    public CerfMarkService(CerfMarkRepository cerfMarkRepository, CerfMarkMapper cerfMarkMapper) {
        this.cerfMarkRepository = cerfMarkRepository;
        this.cerfMarkMapper = cerfMarkMapper;
    }

    /**
     * Save a cerfMark.
     *
     * @param cerfMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfMarkDTO save(CerfMarkDTO cerfMarkDTO) {
        log.debug("Request to save CerfMark : {}", cerfMarkDTO);
        CerfMark cerfMark = cerfMarkMapper.toEntity(cerfMarkDTO);
        cerfMark = cerfMarkRepository.save(cerfMark);
        return cerfMarkMapper.toDto(cerfMark);
    }

    /**
     * Update a cerfMark.
     *
     * @param cerfMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfMarkDTO update(CerfMarkDTO cerfMarkDTO) {
        log.debug("Request to update CerfMark : {}", cerfMarkDTO);
        CerfMark cerfMark = cerfMarkMapper.toEntity(cerfMarkDTO);
        cerfMark = cerfMarkRepository.save(cerfMark);
        return cerfMarkMapper.toDto(cerfMark);
    }

    /**
     * Partially update a cerfMark.
     *
     * @param cerfMarkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CerfMarkDTO> partialUpdate(CerfMarkDTO cerfMarkDTO) {
        log.debug("Request to partially update CerfMark : {}", cerfMarkDTO);

        return cerfMarkRepository
            .findById(cerfMarkDTO.getId())
            .map(existingCerfMark -> {
                cerfMarkMapper.partialUpdate(existingCerfMark, cerfMarkDTO);

                return existingCerfMark;
            })
            .map(cerfMarkRepository::save)
            .map(cerfMarkMapper::toDto);
    }

    /**
     * Get one cerfMark by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CerfMarkDTO> findOne(Long id) {
        log.debug("Request to get CerfMark : {}", id);
        return cerfMarkRepository.findById(id).map(cerfMarkMapper::toDto);
    }

    /**
     * Delete the cerfMark by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CerfMark : {}", id);
        cerfMarkRepository.deleteById(id);
    }
}
