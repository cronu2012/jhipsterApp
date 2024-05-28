package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CerfStdAsserts.*;
import static com.mycompany.myapp.domain.CerfStdTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CerfStdMapperTest {

    private CerfStdMapper cerfStdMapper;

    @BeforeEach
    void setUp() {
        cerfStdMapper = new CerfStdMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCerfStdSample1();
        var actual = cerfStdMapper.toEntity(cerfStdMapper.toDto(expected));
        assertCerfStdAllPropertiesEquals(expected, actual);
    }
}
