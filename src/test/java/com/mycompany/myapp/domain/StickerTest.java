package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ProdStickerTestSamples.*;
import static com.mycompany.myapp.domain.StickerMarkTestSamples.*;
import static com.mycompany.myapp.domain.StickerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StickerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sticker.class);
        Sticker sticker1 = getStickerSample1();
        Sticker sticker2 = new Sticker();
        assertThat(sticker1).isNotEqualTo(sticker2);

        sticker2.setId(sticker1.getId());
        assertThat(sticker1).isEqualTo(sticker2);

        sticker2 = getStickerSample2();
        assertThat(sticker1).isNotEqualTo(sticker2);
    }

    @Test
    void stickerMarkTest() throws Exception {
        Sticker sticker = getStickerRandomSampleGenerator();
        StickerMark stickerMarkBack = getStickerMarkRandomSampleGenerator();

        sticker.addStickerMark(stickerMarkBack);
        assertThat(sticker.getStickerMarks()).containsOnly(stickerMarkBack);
        assertThat(stickerMarkBack.getSticker()).isEqualTo(sticker);

        sticker.removeStickerMark(stickerMarkBack);
        assertThat(sticker.getStickerMarks()).doesNotContain(stickerMarkBack);
        assertThat(stickerMarkBack.getSticker()).isNull();

        sticker.stickerMarks(new HashSet<>(Set.of(stickerMarkBack)));
        assertThat(sticker.getStickerMarks()).containsOnly(stickerMarkBack);
        assertThat(stickerMarkBack.getSticker()).isEqualTo(sticker);

        sticker.setStickerMarks(new HashSet<>());
        assertThat(sticker.getStickerMarks()).doesNotContain(stickerMarkBack);
        assertThat(stickerMarkBack.getSticker()).isNull();
    }

    @Test
    void prodStickerTest() throws Exception {
        Sticker sticker = getStickerRandomSampleGenerator();
        ProdSticker prodStickerBack = getProdStickerRandomSampleGenerator();

        sticker.addProdSticker(prodStickerBack);
        assertThat(sticker.getProdStickers()).containsOnly(prodStickerBack);
        assertThat(prodStickerBack.getSticker()).isEqualTo(sticker);

        sticker.removeProdSticker(prodStickerBack);
        assertThat(sticker.getProdStickers()).doesNotContain(prodStickerBack);
        assertThat(prodStickerBack.getSticker()).isNull();

        sticker.prodStickers(new HashSet<>(Set.of(prodStickerBack)));
        assertThat(sticker.getProdStickers()).containsOnly(prodStickerBack);
        assertThat(prodStickerBack.getSticker()).isEqualTo(sticker);

        sticker.setProdStickers(new HashSet<>());
        assertThat(sticker.getProdStickers()).doesNotContain(prodStickerBack);
        assertThat(prodStickerBack.getSticker()).isNull();
    }
}
