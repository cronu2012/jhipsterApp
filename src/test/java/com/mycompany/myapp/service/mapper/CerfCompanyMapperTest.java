package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CerfCompanyAsserts.*;
import static com.mycompany.myapp.domain.CerfCompanyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CerfCompanyMapperTest {

    private CerfCompanyMapper cerfCompanyMapper;

    @BeforeEach
    void setUp() {
        cerfCompanyMapper = new CerfCompanyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCerfCompanySample1();
        var actual = cerfCompanyMapper.toEntity(cerfCompanyMapper.toDto(expected));
        assertCerfCompanyAllPropertiesEquals(expected, actual);
    }
}
