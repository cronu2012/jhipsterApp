package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ProdCountryAsserts.*;
import static com.mycompany.myapp.domain.ProdCountryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdCountryMapperTest {

    private ProdCountryMapper prodCountryMapper;

    @BeforeEach
    void setUp() {
        prodCountryMapper = new ProdCountryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProdCountrySample1();
        var actual = prodCountryMapper.toEntity(prodCountryMapper.toDto(expected));
        assertProdCountryAllPropertiesEquals(expected, actual);
    }
}
