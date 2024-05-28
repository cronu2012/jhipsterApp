package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CerfAsserts.*;
import static com.mycompany.myapp.domain.CerfTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CerfMapperTest {

    private CerfMapper cerfMapper;

    @BeforeEach
    void setUp() {
        cerfMapper = new CerfMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCerfSample1();
        var actual = cerfMapper.toEntity(cerfMapper.toDto(expected));
        assertCerfAllPropertiesEquals(expected, actual);
    }
}
