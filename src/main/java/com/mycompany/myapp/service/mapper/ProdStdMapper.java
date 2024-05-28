package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.domain.ProdStd;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.service.dto.ProdDTO;
import com.mycompany.myapp.service.dto.ProdStdDTO;
import com.mycompany.myapp.service.dto.StdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProdStd} and its DTO {@link ProdStdDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdStdMapper extends EntityMapper<ProdStdDTO, ProdStd> {
    @Mapping(target = "prod", source = "prod", qualifiedByName = "prodId")
    @Mapping(target = "std", source = "std", qualifiedByName = "stdId")
    ProdStdDTO toDto(ProdStd s);

    @Named("prodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdDTO toDtoProdId(Prod prod);

    @Named("stdId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StdDTO toDtoStdId(Std std);
}
