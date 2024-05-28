package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.StickerMark;
import com.mycompany.myapp.repository.StickerMarkRepository;
import com.mycompany.myapp.service.dto.StickerMarkDTO;
import com.mycompany.myapp.service.mapper.StickerMarkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.StickerMark}.
 */
@Service
@Transactional
public class StickerMarkService {

    private final Logger log = LoggerFactory.getLogger(StickerMarkService.class);

    private final StickerMarkRepository stickerMarkRepository;

    private final StickerMarkMapper stickerMarkMapper;

    public StickerMarkService(StickerMarkRepository stickerMarkRepository, StickerMarkMapper stickerMarkMapper) {
        this.stickerMarkRepository = stickerMarkRepository;
        this.stickerMarkMapper = stickerMarkMapper;
    }

    /**
     * Save a stickerMark.
     *
     * @param stickerMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public StickerMarkDTO save(StickerMarkDTO stickerMarkDTO) {
        log.debug("Request to save StickerMark : {}", stickerMarkDTO);
        StickerMark stickerMark = stickerMarkMapper.toEntity(stickerMarkDTO);
        stickerMark = stickerMarkRepository.save(stickerMark);
        return stickerMarkMapper.toDto(stickerMark);
    }

    /**
     * Update a stickerMark.
     *
     * @param stickerMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public StickerMarkDTO update(StickerMarkDTO stickerMarkDTO) {
        log.debug("Request to update StickerMark : {}", stickerMarkDTO);
        StickerMark stickerMark = stickerMarkMapper.toEntity(stickerMarkDTO);
        stickerMark = stickerMarkRepository.save(stickerMark);
        return stickerMarkMapper.toDto(stickerMark);
    }

    /**
     * Partially update a stickerMark.
     *
     * @param stickerMarkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StickerMarkDTO> partialUpdate(StickerMarkDTO stickerMarkDTO) {
        log.debug("Request to partially update StickerMark : {}", stickerMarkDTO);

        return stickerMarkRepository
            .findById(stickerMarkDTO.getId())
            .map(existingStickerMark -> {
                stickerMarkMapper.partialUpdate(existingStickerMark, stickerMarkDTO);

                return existingStickerMark;
            })
            .map(stickerMarkRepository::save)
            .map(stickerMarkMapper::toDto);
    }

    /**
     * Get one stickerMark by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StickerMarkDTO> findOne(Long id) {
        log.debug("Request to get StickerMark : {}", id);
        return stickerMarkRepository.findById(id).map(stickerMarkMapper::toDto);
    }

    /**
     * Delete the stickerMark by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StickerMark : {}", id);
        stickerMarkRepository.deleteById(id);
    }
}
