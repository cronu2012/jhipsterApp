package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ProdStickerTestSamples.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;
import static com.mycompany.myapp.domain.StickerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdStickerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdSticker.class);
        ProdSticker prodSticker1 = getProdStickerSample1();
        ProdSticker prodSticker2 = new ProdSticker();
        assertThat(prodSticker1).isNotEqualTo(prodSticker2);

        prodSticker2.setId(prodSticker1.getId());
        assertThat(prodSticker1).isEqualTo(prodSticker2);

        prodSticker2 = getProdStickerSample2();
        assertThat(prodSticker1).isNotEqualTo(prodSticker2);
    }

    @Test
    void prodTest() throws Exception {
        ProdSticker prodSticker = getProdStickerRandomSampleGenerator();
        Prod prodBack = getProdRandomSampleGenerator();

        prodSticker.setProd(prodBack);
        assertThat(prodSticker.getProd()).isEqualTo(prodBack);

        prodSticker.prod(null);
        assertThat(prodSticker.getProd()).isNull();
    }

    @Test
    void stickerTest() throws Exception {
        ProdSticker prodSticker = getProdStickerRandomSampleGenerator();
        Sticker stickerBack = getStickerRandomSampleGenerator();

        prodSticker.setSticker(stickerBack);
        assertThat(prodSticker.getSticker()).isEqualTo(stickerBack);

        prodSticker.sticker(null);
        assertThat(prodSticker.getSticker()).isNull();
    }
}
