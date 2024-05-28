package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfStd;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.dto.CerfStdDTO;
import com.mycompany.myapp.service.dto.StdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CerfStd} and its DTO {@link CerfStdDTO}.
 */
@Mapper(componentModel = "spring")
public interface CerfStdMapper extends EntityMapper<CerfStdDTO, CerfStd> {
    @Mapping(target = "cerf", source = "cerf", qualifiedByName = "cerfId")
    @Mapping(target = "std", source = "std", qualifiedByName = "stdId")
    CerfStdDTO toDto(CerfStd s);

    @Named("cerfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CerfDTO toDtoCerfId(Cerf cerf);

    @Named("stdId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StdDTO toDtoStdId(Std std);
}
