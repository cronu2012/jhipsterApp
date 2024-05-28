package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfMark;
import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.dto.CerfMarkDTO;
import com.mycompany.myapp.service.dto.MarkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CerfMark} and its DTO {@link CerfMarkDTO}.
 */
@Mapper(componentModel = "spring")
public interface CerfMarkMapper extends EntityMapper<CerfMarkDTO, CerfMark> {
    @Mapping(target = "cerf", source = "cerf", qualifiedByName = "cerfId")
    @Mapping(target = "mark", source = "mark", qualifiedByName = "markId")
    CerfMarkDTO toDto(CerfMark s);

    @Named("cerfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CerfDTO toDtoCerfId(Cerf cerf);

    @Named("markId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MarkDTO toDtoMarkId(Mark mark);
}
