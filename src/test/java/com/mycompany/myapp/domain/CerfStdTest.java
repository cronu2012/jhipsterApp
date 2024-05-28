package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfStdTestSamples.*;
import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.StdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfStdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfStd.class);
        CerfStd cerfStd1 = getCerfStdSample1();
        CerfStd cerfStd2 = new CerfStd();
        assertThat(cerfStd1).isNotEqualTo(cerfStd2);

        cerfStd2.setId(cerfStd1.getId());
        assertThat(cerfStd1).isEqualTo(cerfStd2);

        cerfStd2 = getCerfStdSample2();
        assertThat(cerfStd1).isNotEqualTo(cerfStd2);
    }

    @Test
    void cerfTest() throws Exception {
        CerfStd cerfStd = getCerfStdRandomSampleGenerator();
        Cerf cerfBack = getCerfRandomSampleGenerator();

        cerfStd.setCerf(cerfBack);
        assertThat(cerfStd.getCerf()).isEqualTo(cerfBack);

        cerfStd.cerf(null);
        assertThat(cerfStd.getCerf()).isNull();
    }

    @Test
    void stdTest() throws Exception {
        CerfStd cerfStd = getCerfStdRandomSampleGenerator();
        Std stdBack = getStdRandomSampleGenerator();

        cerfStd.setStd(stdBack);
        assertThat(cerfStd.getStd()).isEqualTo(stdBack);

        cerfStd.std(null);
        assertThat(cerfStd.getStd()).isNull();
    }
}
