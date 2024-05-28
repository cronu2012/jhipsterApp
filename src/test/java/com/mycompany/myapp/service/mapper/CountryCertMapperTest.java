package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CountryCertAsserts.*;
import static com.mycompany.myapp.domain.CountryCertTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryCertMapperTest {

    private CountryCertMapper countryCertMapper;

    @BeforeEach
    void setUp() {
        countryCertMapper = new CountryCertMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCountryCertSample1();
        var actual = countryCertMapper.toEntity(countryCertMapper.toDto(expected));
        assertCountryCertAllPropertiesEquals(expected, actual);
    }
}
