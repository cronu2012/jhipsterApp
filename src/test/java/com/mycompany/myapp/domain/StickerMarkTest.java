package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.MarkTestSamples.*;
import static com.mycompany.myapp.domain.StickerMarkTestSamples.*;
import static com.mycompany.myapp.domain.StickerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StickerMarkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StickerMark.class);
        StickerMark stickerMark1 = getStickerMarkSample1();
        StickerMark stickerMark2 = new StickerMark();
        assertThat(stickerMark1).isNotEqualTo(stickerMark2);

        stickerMark2.setId(stickerMark1.getId());
        assertThat(stickerMark1).isEqualTo(stickerMark2);

        stickerMark2 = getStickerMarkSample2();
        assertThat(stickerMark1).isNotEqualTo(stickerMark2);
    }

    @Test
    void stickerTest() throws Exception {
        StickerMark stickerMark = getStickerMarkRandomSampleGenerator();
        Sticker stickerBack = getStickerRandomSampleGenerator();

        stickerMark.setSticker(stickerBack);
        assertThat(stickerMark.getSticker()).isEqualTo(stickerBack);

        stickerMark.sticker(null);
        assertThat(stickerMark.getSticker()).isNull();
    }

    @Test
    void markTest() throws Exception {
        StickerMark stickerMark = getStickerMarkRandomSampleGenerator();
        Mark markBack = getMarkRandomSampleGenerator();

        stickerMark.setMark(markBack);
        assertThat(stickerMark.getMark()).isEqualTo(markBack);

        stickerMark.mark(null);
        assertThat(stickerMark.getMark()).isNull();
    }
}
