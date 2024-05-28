package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfMarkTestSamples.*;
import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.MarkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfMarkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfMark.class);
        CerfMark cerfMark1 = getCerfMarkSample1();
        CerfMark cerfMark2 = new CerfMark();
        assertThat(cerfMark1).isNotEqualTo(cerfMark2);

        cerfMark2.setId(cerfMark1.getId());
        assertThat(cerfMark1).isEqualTo(cerfMark2);

        cerfMark2 = getCerfMarkSample2();
        assertThat(cerfMark1).isNotEqualTo(cerfMark2);
    }

    @Test
    void cerfTest() throws Exception {
        CerfMark cerfMark = getCerfMarkRandomSampleGenerator();
        Cerf cerfBack = getCerfRandomSampleGenerator();

        cerfMark.setCerf(cerfBack);
        assertThat(cerfMark.getCerf()).isEqualTo(cerfBack);

        cerfMark.cerf(null);
        assertThat(cerfMark.getCerf()).isNull();
    }

    @Test
    void markTest() throws Exception {
        CerfMark cerfMark = getCerfMarkRandomSampleGenerator();
        Mark markBack = getMarkRandomSampleGenerator();

        cerfMark.setMark(markBack);
        assertThat(cerfMark.getMark()).isEqualTo(markBack);

        cerfMark.mark(null);
        assertThat(cerfMark.getMark()).isNull();
    }
}
