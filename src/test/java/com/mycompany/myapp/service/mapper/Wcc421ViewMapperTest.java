package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.Wcc421ViewAsserts.*;
import static com.mycompany.myapp.domain.Wcc421ViewTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Wcc421ViewMapperTest {

    private Wcc421ViewMapper wcc421ViewMapper;

    @BeforeEach
    void setUp() {
        wcc421ViewMapper = new Wcc421ViewMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWcc421ViewSample1();
        var actual = wcc421ViewMapper.toEntity(wcc421ViewMapper.toDto(expected));
        assertWcc421ViewAllPropertiesEquals(expected, actual);
    }
}
