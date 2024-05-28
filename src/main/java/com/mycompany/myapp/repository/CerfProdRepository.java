package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CerfProd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CerfProd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CerfProdRepository extends JpaRepository<CerfProd, Long>, JpaSpecificationExecutor<CerfProd> {}
