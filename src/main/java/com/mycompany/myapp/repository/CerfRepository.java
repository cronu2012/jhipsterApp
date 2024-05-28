package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cerf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cerf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CerfRepository extends JpaRepository<Cerf, Long>, JpaSpecificationExecutor<Cerf> {}
