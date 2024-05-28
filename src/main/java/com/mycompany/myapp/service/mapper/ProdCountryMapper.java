package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.domain.ProdCountry;
import com.mycompany.myapp.service.dto.CountryDTO;
import com.mycompany.myapp.service.dto.ProdCountryDTO;
import com.mycompany.myapp.service.dto.ProdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProdCountry} and its DTO {@link ProdCountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdCountryMapper extends EntityMapper<ProdCountryDTO, ProdCountry> {
    @Mapping(target = "prod", source = "prod", qualifiedByName = "prodId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    ProdCountryDTO toDto(ProdCountry s);

    @Named("prodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdDTO toDtoProdId(Prod prod);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);
}
