package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ProdStdAsserts.*;
import static com.mycompany.myapp.domain.ProdStdTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdStdMapperTest {

    private ProdStdMapper prodStdMapper;

    @BeforeEach
    void setUp() {
        prodStdMapper = new ProdStdMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProdStdSample1();
        var actual = prodStdMapper.toEntity(prodStdMapper.toDto(expected));
        assertProdStdAllPropertiesEquals(expected, actual);
    }
}
