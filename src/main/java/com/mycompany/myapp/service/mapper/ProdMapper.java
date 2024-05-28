package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.service.dto.ProdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prod} and its DTO {@link ProdDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdMapper extends EntityMapper<ProdDTO, Prod> {}
