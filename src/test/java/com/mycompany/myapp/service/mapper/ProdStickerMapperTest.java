package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ProdStickerAsserts.*;
import static com.mycompany.myapp.domain.ProdStickerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdStickerMapperTest {

    private ProdStickerMapper prodStickerMapper;

    @BeforeEach
    void setUp() {
        prodStickerMapper = new ProdStickerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProdStickerSample1();
        var actual = prodStickerMapper.toEntity(prodStickerMapper.toDto(expected));
        assertProdStickerAllPropertiesEquals(expected, actual);
    }
}
