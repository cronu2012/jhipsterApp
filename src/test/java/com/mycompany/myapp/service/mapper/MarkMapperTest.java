package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.MarkAsserts.*;
import static com.mycompany.myapp.domain.MarkTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarkMapperTest {

    private MarkMapper markMapper;

    @BeforeEach
    void setUp() {
        markMapper = new MarkMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMarkSample1();
        var actual = markMapper.toEntity(markMapper.toDto(expected));
        assertMarkAllPropertiesEquals(expected, actual);
    }
}
