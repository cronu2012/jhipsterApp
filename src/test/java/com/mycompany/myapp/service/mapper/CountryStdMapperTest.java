package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CountryStdAsserts.*;
import static com.mycompany.myapp.domain.CountryStdTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryStdMapperTest {

    private CountryStdMapper countryStdMapper;

    @BeforeEach
    void setUp() {
        countryStdMapper = new CountryStdMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCountryStdSample1();
        var actual = countryStdMapper.toEntity(countryStdMapper.toDto(expected));
        assertCountryStdAllPropertiesEquals(expected, actual);
    }
}
