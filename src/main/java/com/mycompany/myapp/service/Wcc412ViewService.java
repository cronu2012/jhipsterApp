package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Wcc412View;
import com.mycompany.myapp.repository.Wcc412ViewRepository;
import com.mycompany.myapp.service.dto.Wcc412ViewDTO;
import com.mycompany.myapp.service.mapper.Wcc412ViewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Wcc412View}.
 */
@Service
@Transactional
public class Wcc412ViewService {

    private final Logger log = LoggerFactory.getLogger(Wcc412ViewService.class);

    private final Wcc412ViewRepository wcc412ViewRepository;

    private final Wcc412ViewMapper wcc412ViewMapper;

    public Wcc412ViewService(Wcc412ViewRepository wcc412ViewRepository, Wcc412ViewMapper wcc412ViewMapper) {
        this.wcc412ViewRepository = wcc412ViewRepository;
        this.wcc412ViewMapper = wcc412ViewMapper;
    }

    /**
     * Save a wcc412View.
     *
     * @param wcc412ViewDTO the entity to save.
     * @return the persisted entity.
     */
    public Wcc412ViewDTO save(Wcc412ViewDTO wcc412ViewDTO) {
        log.debug("Request to save Wcc412View : {}", wcc412ViewDTO);
        Wcc412View wcc412View = wcc412ViewMapper.toEntity(wcc412ViewDTO);
        wcc412View = wcc412ViewRepository.save(wcc412View);
        return wcc412ViewMapper.toDto(wcc412View);
    }

    /**
     * Update a wcc412View.
     *
     * @param wcc412ViewDTO the entity to save.
     * @return the persisted entity.
     */
    public Wcc412ViewDTO update(Wcc412ViewDTO wcc412ViewDTO) {
        log.debug("Request to update Wcc412View : {}", wcc412ViewDTO);
        Wcc412View wcc412View = wcc412ViewMapper.toEntity(wcc412ViewDTO);
        wcc412View = wcc412ViewRepository.save(wcc412View);
        return wcc412ViewMapper.toDto(wcc412View);
    }

    /**
     * Partially update a wcc412View.
     *
     * @param wcc412ViewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Wcc412ViewDTO> partialUpdate(Wcc412ViewDTO wcc412ViewDTO) {
        log.debug("Request to partially update Wcc412View : {}", wcc412ViewDTO);

        return wcc412ViewRepository
            .findById(wcc412ViewDTO.getId())
            .map(existingWcc412View -> {
                wcc412ViewMapper.partialUpdate(existingWcc412View, wcc412ViewDTO);

                return existingWcc412View;
            })
            .map(wcc412ViewRepository::save)
            .map(wcc412ViewMapper::toDto);
    }

    /**
     * Get one wcc412View by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Wcc412ViewDTO> findOne(Long id) {
        log.debug("Request to get Wcc412View : {}", id);
        return wcc412ViewRepository.findById(id).map(wcc412ViewMapper::toDto);
    }

    /**
     * Delete the wcc412View by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Wcc412View : {}", id);
        wcc412ViewRepository.deleteById(id);
    }
}
