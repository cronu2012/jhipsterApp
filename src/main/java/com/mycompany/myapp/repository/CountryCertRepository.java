package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CountryCert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CountryCert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryCertRepository extends JpaRepository<CountryCert, Long>, JpaSpecificationExecutor<CountryCert> {}
