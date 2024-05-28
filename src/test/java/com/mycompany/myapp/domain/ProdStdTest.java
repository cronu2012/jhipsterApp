package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ProdStdTestSamples.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;
import static com.mycompany.myapp.domain.StdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdStdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdStd.class);
        ProdStd prodStd1 = getProdStdSample1();
        ProdStd prodStd2 = new ProdStd();
        assertThat(prodStd1).isNotEqualTo(prodStd2);

        prodStd2.setId(prodStd1.getId());
        assertThat(prodStd1).isEqualTo(prodStd2);

        prodStd2 = getProdStdSample2();
        assertThat(prodStd1).isNotEqualTo(prodStd2);
    }

    @Test
    void prodTest() throws Exception {
        ProdStd prodStd = getProdStdRandomSampleGenerator();
        Prod prodBack = getProdRandomSampleGenerator();

        prodStd.setProd(prodBack);
        assertThat(prodStd.getProd()).isEqualTo(prodBack);

        prodStd.prod(null);
        assertThat(prodStd.getProd()).isNull();
    }

    @Test
    void stdTest() throws Exception {
        ProdStd prodStd = getProdStdRandomSampleGenerator();
        Std stdBack = getStdRandomSampleGenerator();

        prodStd.setStd(stdBack);
        assertThat(prodStd.getStd()).isEqualTo(stdBack);

        prodStd.std(null);
        assertThat(prodStd.getStd()).isNull();
    }
}
