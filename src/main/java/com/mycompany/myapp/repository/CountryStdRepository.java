package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CountryStd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CountryStd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryStdRepository extends JpaRepository<CountryStd, Long>, JpaSpecificationExecutor<CountryStd> {}
