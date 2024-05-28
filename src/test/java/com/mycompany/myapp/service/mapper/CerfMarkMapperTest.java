package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CerfMarkAsserts.*;
import static com.mycompany.myapp.domain.CerfMarkTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CerfMarkMapperTest {

    private CerfMarkMapper cerfMarkMapper;

    @BeforeEach
    void setUp() {
        cerfMarkMapper = new CerfMarkMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCerfMarkSample1();
        var actual = cerfMarkMapper.toEntity(cerfMarkMapper.toDto(expected));
        assertCerfMarkAllPropertiesEquals(expected, actual);
    }
}
