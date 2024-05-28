package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.StickerAsserts.*;
import static com.mycompany.myapp.domain.StickerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StickerMapperTest {

    private StickerMapper stickerMapper;

    @BeforeEach
    void setUp() {
        stickerMapper = new StickerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStickerSample1();
        var actual = stickerMapper.toEntity(stickerMapper.toDto(expected));
        assertStickerAllPropertiesEquals(expected, actual);
    }
}
