package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CerfCompany;
import com.mycompany.myapp.repository.CerfCompanyRepository;
import com.mycompany.myapp.service.dto.CerfCompanyDTO;
import com.mycompany.myapp.service.mapper.CerfCompanyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CerfCompany}.
 */
@Service
@Transactional
public class CerfCompanyService {

    private final Logger log = LoggerFactory.getLogger(CerfCompanyService.class);

    private final CerfCompanyRepository cerfCompanyRepository;

    private final CerfCompanyMapper cerfCompanyMapper;

    public CerfCompanyService(CerfCompanyRepository cerfCompanyRepository, CerfCompanyMapper cerfCompanyMapper) {
        this.cerfCompanyRepository = cerfCompanyRepository;
        this.cerfCompanyMapper = cerfCompanyMapper;
    }

    /**
     * Save a cerfCompany.
     *
     * @param cerfCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfCompanyDTO save(CerfCompanyDTO cerfCompanyDTO) {
        log.debug("Request to save CerfCompany : {}", cerfCompanyDTO);
        CerfCompany cerfCompany = cerfCompanyMapper.toEntity(cerfCompanyDTO);
        cerfCompany = cerfCompanyRepository.save(cerfCompany);
        return cerfCompanyMapper.toDto(cerfCompany);
    }

    /**
     * Update a cerfCompany.
     *
     * @param cerfCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public CerfCompanyDTO update(CerfCompanyDTO cerfCompanyDTO) {
        log.debug("Request to update CerfCompany : {}", cerfCompanyDTO);
        CerfCompany cerfCompany = cerfCompanyMapper.toEntity(cerfCompanyDTO);
        cerfCompany = cerfCompanyRepository.save(cerfCompany);
        return cerfCompanyMapper.toDto(cerfCompany);
    }

    /**
     * Partially update a cerfCompany.
     *
     * @param cerfCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CerfCompanyDTO> partialUpdate(CerfCompanyDTO cerfCompanyDTO) {
        log.debug("Request to partially update CerfCompany : {}", cerfCompanyDTO);

        return cerfCompanyRepository
            .findById(cerfCompanyDTO.getId())
            .map(existingCerfCompany -> {
                cerfCompanyMapper.partialUpdate(existingCerfCompany, cerfCompanyDTO);

                return existingCerfCompany;
            })
            .map(cerfCompanyRepository::save)
            .map(cerfCompanyMapper::toDto);
    }

    /**
     * Get one cerfCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CerfCompanyDTO> findOne(Long id) {
        log.debug("Request to get CerfCompany : {}", id);
        return cerfCompanyRepository.findById(id).map(cerfCompanyMapper::toDto);
    }

    /**
     * Delete the cerfCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CerfCompany : {}", id);
        cerfCompanyRepository.deleteById(id);
    }
}
