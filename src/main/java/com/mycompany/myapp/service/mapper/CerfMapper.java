package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.service.dto.CerfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cerf} and its DTO {@link CerfDTO}.
 */
@Mapper(componentModel = "spring")
public interface CerfMapper extends EntityMapper<CerfDTO, Cerf> {}
