package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProdSticker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProdSticker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdStickerRepository extends JpaRepository<ProdSticker, Long>, JpaSpecificationExecutor<ProdSticker> {}
