package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ProdAsserts.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdMapperTest {

    private ProdMapper prodMapper;

    @BeforeEach
    void setUp() {
        prodMapper = new ProdMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProdSample1();
        var actual = prodMapper.toEntity(prodMapper.toDto(expected));
        assertProdAllPropertiesEquals(expected, actual);
    }
}
