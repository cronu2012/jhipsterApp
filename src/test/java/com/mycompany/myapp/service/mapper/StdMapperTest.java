package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.StdAsserts.*;
import static com.mycompany.myapp.domain.StdTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StdMapperTest {

    private StdMapper stdMapper;

    @BeforeEach
    void setUp() {
        stdMapper = new StdMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStdSample1();
        var actual = stdMapper.toEntity(stdMapper.toDto(expected));
        assertStdAllPropertiesEquals(expected, actual);
    }
}
