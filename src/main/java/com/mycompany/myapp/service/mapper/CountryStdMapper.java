package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.CountryStd;
import com.mycompany.myapp.domain.Std;
import com.mycompany.myapp.service.dto.CountryDTO;
import com.mycompany.myapp.service.dto.CountryStdDTO;
import com.mycompany.myapp.service.dto.StdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountryStd} and its DTO {@link CountryStdDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryStdMapper extends EntityMapper<CountryStdDTO, CountryStd> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    @Mapping(target = "std", source = "std", qualifiedByName = "stdId")
    CountryStdDTO toDto(CountryStd s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("stdId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StdDTO toDtoStdId(Std std);
}
