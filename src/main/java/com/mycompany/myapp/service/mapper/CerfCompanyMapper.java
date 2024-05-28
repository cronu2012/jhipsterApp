package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.CerfCompany;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.service.dto.CerfCompanyDTO;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CerfCompany} and its DTO {@link CerfCompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CerfCompanyMapper extends EntityMapper<CerfCompanyDTO, CerfCompany> {
    @Mapping(target = "cerf", source = "cerf", qualifiedByName = "cerfId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    CerfCompanyDTO toDto(CerfCompany s);

    @Named("cerfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CerfDTO toDtoCerfId(Cerf cerf);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
