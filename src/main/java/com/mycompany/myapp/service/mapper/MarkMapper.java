package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.service.dto.MarkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mark} and its DTO {@link MarkDTO}.
 */
@Mapper(componentModel = "spring")
public interface MarkMapper extends EntityMapper<MarkDTO, Mark> {}
