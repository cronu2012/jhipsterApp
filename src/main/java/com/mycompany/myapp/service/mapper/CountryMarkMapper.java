package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Country;
import com.mycompany.myapp.domain.CountryMark;
import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.service.dto.CountryDTO;
import com.mycompany.myapp.service.dto.CountryMarkDTO;
import com.mycompany.myapp.service.dto.MarkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountryMark} and its DTO {@link CountryMarkDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMarkMapper extends EntityMapper<CountryMarkDTO, CountryMark> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    @Mapping(target = "mark", source = "mark", qualifiedByName = "markId")
    CountryMarkDTO toDto(CountryMark s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("markId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MarkDTO toDtoMarkId(Mark mark);
}
