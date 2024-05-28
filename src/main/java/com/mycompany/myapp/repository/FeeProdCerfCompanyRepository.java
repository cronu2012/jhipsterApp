package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FeeProdCerfCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeeProdCerfCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeeProdCerfCompanyRepository
    extends JpaRepository<FeeProdCerfCompany, Long>, JpaSpecificationExecutor<FeeProdCerfCompany> {}
