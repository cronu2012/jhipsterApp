package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CerfStd;
import com.mycompany.myapp.repository.CerfStdRepository;
import com.mycompany.myapp.service.dto.CerfStdDTO;
import com.mycompany.myapp.service.mapper.CerfStdMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CerfStd}.
 */
@Service
@Transactional
public class CerfStdService {

    private final Logger log = LoggerFactory.getLogger(CerfStdService.class);

    private final CerfStdRepository cerfStdRepository;

    private final CerfStdMapper cerfStdMapper;

    public CerfStdService(CerfStdRepository cerfStdRepository, CerfStdMapper cerfStdMapper) {
        this.cerfStdRepository = cerfStdRepository;
        this.cerfStdMapper = cerfStdMapper;
    }

    /**
     * Save a cerfStd.
     *
     * @param cerfStdDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfStdDTO save(CerfStdDTO cerfStdDTO) {
        log.debug("Request to save CerfStd : {}", cerfStdDTO);
        CerfStd cerfStd = cerfStdMapper.toEntity(cerfStdDTO);
        cerfStd = cerfStdRepository.save(cerfStd);
        return cerfStdMapper.toDto(cerfStd);
    }

    /**
     * Update a cerfStd.
     *
     * @param cerfStdDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfStdDTO update(CerfStdDTO cerfStdDTO) {
        log.debug("Request to update CerfStd : {}", cerfStdDTO);
        CerfStd cerfStd = cerfStdMapper.toEntity(cerfStdDTO);
        cerfStd = cerfStdRepository.save(cerfStd);
        return cerfStdMapper.toDto(cerfStd);
    }

    /**
     * Partially update a cerfStd.
     *
     * @param cerfStdDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CerfStdDTO> partialUpdate(CerfStdDTO cerfStdDTO) {
        log.debug("Request to partially update CerfStd : {}", cerfStdDTO);

        return cerfStdRepository
            .findById(cerfStdDTO.getId())
            .map(existingCerfStd -> {
                cerfStdMapper.partialUpdate(existingCerfStd, cerfStdDTO);

                return existingCerfStd;
            })
            .map(cerfStdRepository::save)
            .map(cerfStdMapper::toDto);
    }

    /**
     * Get one cerfStd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CerfStdDTO> findOne(Long id) {
        log.debug("Request to get CerfStd : {}", id);
        return cerfStdRepository.findById(id).map(cerfStdMapper::toDto);
    }

    /**
     * Delete the cerfStd by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CerfStd : {}", id);
        cerfStdRepository.deleteById(id);
    }
}
