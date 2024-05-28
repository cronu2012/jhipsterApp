package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cerf;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.FeeProdCerfCompany;
import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.service.dto.CerfDTO;
import com.mycompany.myapp.service.dto.CompanyDTO;
import com.mycompany.myapp.service.dto.FeeProdCerfCompanyDTO;
import com.mycompany.myapp.service.dto.ProdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FeeProdCerfCompany} and its DTO {@link FeeProdCerfCompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface FeeProdCerfCompanyMapper extends EntityMapper<FeeProdCerfCompanyDTO, FeeProdCerfCompany> {
    @Mapping(target = "prod", source = "prod", qualifiedByName = "prodId")
    @Mapping(target = "cerf", source = "cerf", qualifiedByName = "cerfId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    FeeProdCerfCompanyDTO toDto(FeeProdCerfCompany s);

    @Named("prodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdDTO toDtoProdId(Prod prod);

    @Named("cerfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CerfDTO toDtoCerfId(Cerf cerf);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
