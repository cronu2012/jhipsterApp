package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProdSticker;
import com.mycompany.myapp.repository.ProdStickerRepository;
import com.mycompany.myapp.service.dto.ProdStickerDTO;
import com.mycompany.myapp.service.mapper.ProdStickerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ProdSticker}.
 */
@Service
@Transactional
public class ProdStickerService {

    private final Logger log = LoggerFactory.getLogger(ProdStickerService.class);

    private final ProdStickerRepository prodStickerRepository;

    private final ProdStickerMapper prodStickerMapper;

    public ProdStickerService(ProdStickerRepository prodStickerRepository, ProdStickerMapper prodStickerMapper) {
        this.prodStickerRepository = prodStickerRepository;
        this.prodStickerMapper = prodStickerMapper;
    }

    /**
     * Save a prodSticker.
     *
     * @param prodStickerDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdStickerDTO save(ProdStickerDTO prodStickerDTO) {
        log.debug("Request to save ProdSticker : {}", prodStickerDTO);
        ProdSticker prodSticker = prodStickerMapper.toEntity(prodStickerDTO);
        prodSticker = prodStickerRepository.save(prodSticker);
        return prodStickerMapper.toDto(prodSticker);
    }

    /**
     * Update a prodSticker.
     *
     * @param prodStickerDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdStickerDTO update(ProdStickerDTO prodStickerDTO) {
        log.debug("Request to update ProdSticker : {}", prodStickerDTO);
        ProdSticker prodSticker = prodStickerMapper.toEntity(prodStickerDTO);
        prodSticker = prodStickerRepository.save(prodSticker);
        return prodStickerMapper.toDto(prodSticker);
    }

    /**
     * Partially update a prodSticker.
     *
     * @param prodStickerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProdStickerDTO> partialUpdate(ProdStickerDTO prodStickerDTO) {
        log.debug("Request to partially update ProdSticker : {}", prodStickerDTO);

        return prodStickerRepository
            .findById(prodStickerDTO.getId())
            .map(existingProdSticker -> {
                prodStickerMapper.partialUpdate(existingProdSticker, prodStickerDTO);

                return existingProdSticker;
            })
            .map(prodStickerRepository::save)
            .map(prodStickerMapper::toDto);
    }

    /**
     * Get one prodSticker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdStickerDTO> findOne(Long id) {
        log.debug("Request to get ProdSticker : {}", id);
        return prodStickerRepository.findById(id).map(prodStickerMapper::toDto);
    }

    /**
     * Delete the prodSticker by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProdSticker : {}", id);
        prodStickerRepository.deleteById(id);
    }
}
