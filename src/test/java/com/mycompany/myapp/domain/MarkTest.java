package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfMarkTestSamples.*;
import static com.mycompany.myapp.domain.CountryMarkTestSamples.*;
import static com.mycompany.myapp.domain.MarkTestSamples.*;
import static com.mycompany.myapp.domain.StickerMarkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MarkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mark.class);
        Mark mark1 = getMarkSample1();
        Mark mark2 = new Mark();
        assertThat(mark1).isNotEqualTo(mark2);

        mark2.setId(mark1.getId());
        assertThat(mark1).isEqualTo(mark2);

        mark2 = getMarkSample2();
        assertThat(mark1).isNotEqualTo(mark2);
    }

    @Test
    void cerfMarkTest() throws Exception {
        Mark mark = getMarkRandomSampleGenerator();
        CerfMark cerfMarkBack = getCerfMarkRandomSampleGenerator();

        mark.addCerfMark(cerfMarkBack);
        assertThat(mark.getCerfMarks()).containsOnly(cerfMarkBack);
        assertThat(cerfMarkBack.getMark()).isEqualTo(mark);

        mark.removeCerfMark(cerfMarkBack);
        assertThat(mark.getCerfMarks()).doesNotContain(cerfMarkBack);
        assertThat(cerfMarkBack.getMark()).isNull();

        mark.cerfMarks(new HashSet<>(Set.of(cerfMarkBack)));
        assertThat(mark.getCerfMarks()).containsOnly(cerfMarkBack);
        assertThat(cerfMarkBack.getMark()).isEqualTo(mark);

        mark.setCerfMarks(new HashSet<>());
        assertThat(mark.getCerfMarks()).doesNotContain(cerfMarkBack);
        assertThat(cerfMarkBack.getMark()).isNull();
    }

    @Test
    void stickerMarkTest() throws Exception {
        Mark mark = getMarkRandomSampleGenerator();
        StickerMark stickerMarkBack = getStickerMarkRandomSampleGenerator();

        mark.addStickerMark(stickerMarkBack);
        assertThat(mark.getStickerMarks()).containsOnly(stickerMarkBack);
        assertThat(stickerMarkBack.getMark()).isEqualTo(mark);

        mark.removeStickerMark(stickerMarkBack);
        assertThat(mark.getStickerMarks()).doesNotContain(stickerMarkBack);
        assertThat(stickerMarkBack.getMark()).isNull();

        mark.stickerMarks(new HashSet<>(Set.of(stickerMarkBack)));
        assertThat(mark.getStickerMarks()).containsOnly(stickerMarkBack);
        assertThat(stickerMarkBack.getMark()).isEqualTo(mark);

        mark.setStickerMarks(new HashSet<>());
        assertThat(mark.getStickerMarks()).doesNotContain(stickerMarkBack);
        assertThat(stickerMarkBack.getMark()).isNull();
    }

    @Test
    void countryMarkTest() throws Exception {
        Mark mark = getMarkRandomSampleGenerator();
        CountryMark countryMarkBack = getCountryMarkRandomSampleGenerator();

        mark.addCountryMark(countryMarkBack);
        assertThat(mark.getCountryMarks()).containsOnly(countryMarkBack);
        assertThat(countryMarkBack.getMark()).isEqualTo(mark);

        mark.removeCountryMark(countryMarkBack);
        assertThat(mark.getCountryMarks()).doesNotContain(countryMarkBack);
        assertThat(countryMarkBack.getMark()).isNull();

        mark.countryMarks(new HashSet<>(Set.of(countryMarkBack)));
        assertThat(mark.getCountryMarks()).containsOnly(countryMarkBack);
        assertThat(countryMarkBack.getMark()).isEqualTo(mark);

        mark.setCountryMarks(new HashSet<>());
        assertThat(mark.getCountryMarks()).doesNotContain(countryMarkBack);
        assertThat(countryMarkBack.getMark()).isNull();
    }
}
