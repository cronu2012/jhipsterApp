package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.Wcc412ViewAsserts.*;
import static com.mycompany.myapp.domain.Wcc412ViewTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Wcc412ViewMapperTest {

    private Wcc412ViewMapper wcc412ViewMapper;

    @BeforeEach
    void setUp() {
        wcc412ViewMapper = new Wcc412ViewMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWcc412ViewSample1();
        var actual = wcc412ViewMapper.toEntity(wcc412ViewMapper.toDto(expected));
        assertWcc412ViewAllPropertiesEquals(expected, actual);
    }
}
