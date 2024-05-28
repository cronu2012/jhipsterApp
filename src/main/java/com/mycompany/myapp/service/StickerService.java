package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.repository.StickerRepository;
import com.mycompany.myapp.service.dto.StickerDTO;
import com.mycompany.myapp.service.mapper.StickerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Sticker}.
 */
@Service
@Transactional
public class StickerService {

    private final Logger log = LoggerFactory.getLogger(StickerService.class);

    private final StickerRepository stickerRepository;

    private final StickerMapper stickerMapper;

    public StickerService(StickerRepository stickerRepository, StickerMapper stickerMapper) {
        this.stickerRepository = stickerRepository;
        this.stickerMapper = stickerMapper;
    }

    /**
     * Save a sticker.
     *
     * @param stickerDTO the entity to save.
     * @return the persisted entity.
     */
    public StickerDTO save(StickerDTO stickerDTO) {
        log.debug("Request to save Sticker : {}", stickerDTO);
        Sticker sticker = stickerMapper.toEntity(stickerDTO);
        sticker = stickerRepository.save(sticker);
        return stickerMapper.toDto(sticker);
    }

    /**
     * Update a sticker.
     *
     * @param stickerDTO the entity to save.
     * @return the persisted entity.
     */
    public StickerDTO update(StickerDTO stickerDTO) {
        log.debug("Request to update Sticker : {}", stickerDTO);
        Sticker sticker = stickerMapper.toEntity(stickerDTO);
        sticker = stickerRepository.save(sticker);
        return stickerMapper.toDto(sticker);
    }

    /**
     * Partially update a sticker.
     *
     * @param stickerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StickerDTO> partialUpdate(StickerDTO stickerDTO) {
        log.debug("Request to partially update Sticker : {}", stickerDTO);

        return stickerRepository
            .findById(stickerDTO.getId())
            .map(existingSticker -> {
                stickerMapper.partialUpdate(existingSticker, stickerDTO);

                return existingSticker;
            })
            .map(stickerRepository::save)
            .map(stickerMapper::toDto);
    }

    /**
     * Get one sticker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StickerDTO> findOne(Long id) {
        log.debug("Request to get Sticker : {}", id);
        return stickerRepository.findById(id).map(stickerMapper::toDto);
    }

    /**
     * Delete the sticker by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sticker : {}", id);
        stickerRepository.deleteById(id);
    }
}
