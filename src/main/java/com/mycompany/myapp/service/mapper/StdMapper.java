package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.service.dto.StdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Std} and its DTO {@link StdDTO}.
 */
@Mapper(componentModel = "spring")
public interface StdMapper extends EntityMapper<StdDTO, Std> {}
