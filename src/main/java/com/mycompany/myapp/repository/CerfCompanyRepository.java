package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CerfCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CerfCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CerfCompanyRepository extends JpaRepository<CerfCompany, Long>, JpaSpecificationExecutor<CerfCompany> {}
