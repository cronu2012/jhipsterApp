package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CountryStd;
import com.mycompany.myapp.repository.CountryStdRepository;
import com.mycompany.myapp.service.dto.CountryStdDTO;
import com.mycompany.myapp.service.mapper.CountryStdMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CountryStd}.
 */
@Service
@Transactional
public class CountryStdService {

    private final Logger log = LoggerFactory.getLogger(CountryStdService.class);

    private final CountryStdRepository countryStdRepository;

    private final CountryStdMapper countryStdMapper;

    public CountryStdService(CountryStdRepository countryStdRepository, CountryStdMapper countryStdMapper) {
        this.countryStdRepository = countryStdRepository;
        this.countryStdMapper = countryStdMapper;
    }

    /**
     * Save a countryStd.
     *
     * @param countryStdDTO the entity to save.
     * @return the persisted entity.
     */
    public CountryStdDTO save(CountryStdDTO countryStdDTO) {
        log.debug("Request to save CountryStd : {}", countryStdDTO);
        CountryStd countryStd = countryStdMapper.toEntity(countryStdDTO);
        countryStd = countryStdRepository.save(countryStd);
        return countryStdMapper.toDto(countryStd);
    }

    /**
     * Update a countryStd.
     *
     * @param countryStdDTO the entity to save.
     * @return the persisted entity.
     */
    public CountryStdDTO update(CountryStdDTO countryStdDTO) {
        log.debug("Request to update CountryStd : {}", countryStdDTO);
        CountryStd countryStd = countryStdMapper.toEntity(countryStdDTO);
        countryStd = countryStdRepository.save(countryStd);
        return countryStdMapper.toDto(countryStd);
    }

    /**
     * Partially update a countryStd.
     *
     * @param countryStdDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CountryStdDTO> partialUpdate(CountryStdDTO countryStdDTO) {
        log.debug("Request to partially update CountryStd : {}", countryStdDTO);

        return countryStdRepository
            .findById(countryStdDTO.getId())
            .map(existingCountryStd -> {
                countryStdMapper.partialUpdate(existingCountryStd, countryStdDTO);

                return existingCountryStd;
            })
            .map(countryStdRepository::save)
            .map(countryStdMapper::toDto);
    }

    /**
     * Get one countryStd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CountryStdDTO> findOne(Long id) {
        log.debug("Request to get CountryStd : {}", id);
        return countryStdRepository.findById(id).map(countryStdMapper::toDto);
    }

    /**
     * Delete the countryStd by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CountryStd : {}", id);
        countryStdRepository.deleteById(id);
    }
}
