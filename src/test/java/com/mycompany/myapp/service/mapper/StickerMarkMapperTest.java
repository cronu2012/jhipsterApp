package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.StickerMarkAsserts.*;
import static com.mycompany.myapp.domain.StickerMarkTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StickerMarkMapperTest {

    private StickerMarkMapper stickerMarkMapper;

    @BeforeEach
    void setUp() {
        stickerMarkMapper = new StickerMarkMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStickerMarkSample1();
        var actual = stickerMarkMapper.toEntity(stickerMarkMapper.toDto(expected));
        assertStickerMarkAllPropertiesEquals(expected, actual);
    }
}
