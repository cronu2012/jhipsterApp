package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Wcc412View;
import com.mycompany.myapp.service.dto.Wcc412ViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wcc412View} and its DTO {@link Wcc412ViewDTO}.
 */
@Mapper(componentModel = "spring")
public interface Wcc412ViewMapper extends EntityMapper<Wcc412ViewDTO, Wcc412View> {}
