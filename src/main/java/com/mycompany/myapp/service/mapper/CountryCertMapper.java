package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.CountryCert;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.dto.CountryCertDTO;
import com.mycompany.myapp.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountryCert} and its DTO {@link CountryCertDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryCertMapper extends EntityMapper<CountryCertDTO, CountryCert> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    @Mapping(target = "cerf", source = "cerf", qualifiedByName = "cerfId")
    CountryCertDTO toDto(CountryCert s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("cerfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CerfDTO toDtoCerfId(Cerf cerf);
}
