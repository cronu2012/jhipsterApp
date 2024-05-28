package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Wcc421View;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wcc421View entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Wcc421ViewRepository extends JpaRepository<Wcc421View, Long>, JpaSpecificationExecutor<Wcc421View> {}
