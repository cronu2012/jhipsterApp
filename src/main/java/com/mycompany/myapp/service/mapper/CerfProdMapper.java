package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfProd;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.dto.CerfProdDTO;
import com.mycompany.myapp.service.dto.ProdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CerfProd} and its DTO {@link CerfProdDTO}.
 */
@Mapper(componentModel = "spring")
public interface CerfProdMapper extends EntityMapper<CerfProdDTO, CerfProd> {
    @Mapping(target = "cerf", source = "cerf", qualifiedByName = "cerfId")
    @Mapping(target = "prod", source = "prod", qualifiedByName = "prodId")
    CerfProdDTO toDto(CerfProd s);

    @Named("cerfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CerfDTO toDtoCerfId(Cerf cerf);

    @Named("prodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdDTO toDtoProdId(Prod prod);
}
