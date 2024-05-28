package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CerfMark;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CerfMark entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CerfMarkRepository extends JpaRepository<CerfMark, Long>, JpaSpecificationExecutor<CerfMark> {}
