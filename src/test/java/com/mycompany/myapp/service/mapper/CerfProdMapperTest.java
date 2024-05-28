package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CerfProdAsserts.*;
import static com.mycompany.myapp.domain.CerfProdTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CerfProdMapperTest {

    private CerfProdMapper cerfProdMapper;

    @BeforeEach
    void setUp() {
        cerfProdMapper = new CerfProdMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCerfProdSample1();
        var actual = cerfProdMapper.toEntity(cerfProdMapper.toDto(expected));
        assertCerfProdAllPropertiesEquals(expected, actual);
    }
}
