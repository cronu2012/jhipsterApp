package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CountryMark;
import com.mycompany.myapp.repository.CountryMarkRepository;
import com.mycompany.myapp.service.dto.CountryMarkDTO;
import com.mycompany.myapp.service.mapper.CountryMarkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CountryMark}.
 */
@Service
@Transactional
public class CountryMarkService {

    private final Logger log = LoggerFactory.getLogger(CountryMarkService.class);

    private final CountryMarkRepository countryMarkRepository;

    private final CountryMarkMapper countryMarkMapper;

    public CountryMarkService(CountryMarkRepository countryMarkRepository, CountryMarkMapper countryMarkMapper) {
        this.countryMarkRepository = countryMarkRepository;
        this.countryMarkMapper = countryMarkMapper;
    }

    /**
     * Save a countryMark.
     *
     * @param countryMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public CountryMarkDTO save(CountryMarkDTO countryMarkDTO) {
        log.debug("Request to save CountryMark : {}", countryMarkDTO);
        CountryMark countryMark = countryMarkMapper.toEntity(countryMarkDTO);
        countryMark = countryMarkRepository.save(countryMark);
        return countryMarkMapper.toDto(countryMark);
    }

    /**
     * Update a countryMark.
     *
     * @param countryMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public CountryMarkDTO update(CountryMarkDTO countryMarkDTO) {
        log.debug("Request to update CountryMark : {}", countryMarkDTO);
        CountryMark countryMark = countryMarkMapper.toEntity(countryMarkDTO);
        countryMark = countryMarkRepository.save(countryMark);
        return countryMarkMapper.toDto(countryMark);
    }

    /**
     * Partially update a countryMark.
     *
     * @param countryMarkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CountryMarkDTO> partialUpdate(CountryMarkDTO countryMarkDTO) {
        log.debug("Request to partially update CountryMark : {}", countryMarkDTO);

        return countryMarkRepository
            .findById(countryMarkDTO.getId())
            .map(existingCountryMark -> {
                countryMarkMapper.partialUpdate(existingCountryMark, countryMarkDTO);

                return existingCountryMark;
            })
            .map(countryMarkRepository::save)
            .map(countryMarkMapper::toDto);
    }

    /**
     * Get one countryMark by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CountryMarkDTO> findOne(Long id) {
        log.debug("Request to get CountryMark : {}", id);
        return countryMarkRepository.findById(id).map(countryMarkMapper::toDto);
    }

    /**
     * Delete the countryMark by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CountryMark : {}", id);
        countryMarkRepository.deleteById(id);
    }
}
