package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Wcc421View;
import com.mycompany.myapp.repository.Wcc421ViewRepository;
import com.mycompany.myapp.service.dto.Wcc421ViewDTO;
import com.mycompany.myapp.service.mapper.Wcc421ViewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Wcc421View}.
 */
@Service
@Transactional
public class Wcc421ViewService {

    private final Logger log = LoggerFactory.getLogger(Wcc421ViewService.class);

    private final Wcc421ViewRepository wcc421ViewRepository;

    private final Wcc421ViewMapper wcc421ViewMapper;

    public Wcc421ViewService(Wcc421ViewRepository wcc421ViewRepository, Wcc421ViewMapper wcc421ViewMapper) {
        this.wcc421ViewRepository = wcc421ViewRepository;
        this.wcc421ViewMapper = wcc421ViewMapper;
    }

    /**
     * Save a wcc421View.
     *
     * @param wcc421ViewDTO the entity to save.
     * @return the persisted entity.
     */
    public Wcc421ViewDTO save(Wcc421ViewDTO wcc421ViewDTO) {
        log.debug("Request to save Wcc421View : {}", wcc421ViewDTO);
        Wcc421View wcc421View = wcc421ViewMapper.toEntity(wcc421ViewDTO);
        wcc421View = wcc421ViewRepository.save(wcc421View);
        return wcc421ViewMapper.toDto(wcc421View);
    }

    /**
     * Update a wcc421View.
     *
     * @param wcc421ViewDTO the entity to save.
     * @return the persisted entity.
     */
    public Wcc421ViewDTO update(Wcc421ViewDTO wcc421ViewDTO) {
        log.debug("Request to update Wcc421View : {}", wcc421ViewDTO);
        Wcc421View wcc421View = wcc421ViewMapper.toEntity(wcc421ViewDTO);
        wcc421View = wcc421ViewRepository.save(wcc421View);
        return wcc421ViewMapper.toDto(wcc421View);
    }

    /**
     * Partially update a wcc421View.
     *
     * @param wcc421ViewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Wcc421ViewDTO> partialUpdate(Wcc421ViewDTO wcc421ViewDTO) {
        log.debug("Request to partially update Wcc421View : {}", wcc421ViewDTO);

        return wcc421ViewRepository
            .findById(wcc421ViewDTO.getId())
            .map(existingWcc421View -> {
                wcc421ViewMapper.partialUpdate(existingWcc421View, wcc421ViewDTO);

                return existingWcc421View;
            })
            .map(wcc421ViewRepository::save)
            .map(wcc421ViewMapper::toDto);
    }

    /**
     * Get one wcc421View by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Wcc421ViewDTO> findOne(Long id) {
        log.debug("Request to get Wcc421View : {}", id);
        return wcc421ViewRepository.findById(id).map(wcc421ViewMapper::toDto);
    }

    /**
     * Delete the wcc421View by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Wcc421View : {}", id);
        wcc421ViewRepository.deleteById(id);
    }
}
