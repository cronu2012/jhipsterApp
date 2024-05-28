package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CountryMarkAsserts.*;
import static com.mycompany.myapp.domain.CountryMarkTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryMarkMapperTest {

    private CountryMarkMapper countryMarkMapper;

    @BeforeEach
    void setUp() {
        countryMarkMapper = new CountryMarkMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCountryMarkSample1();
        var actual = countryMarkMapper.toEntity(countryMarkMapper.toDto(expected));
        assertCountryMarkAllPropertiesEquals(expected, actual);
    }
}
