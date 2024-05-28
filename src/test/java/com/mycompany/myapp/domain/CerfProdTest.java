package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfProdTestSamples.*;
import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfProdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfProd.class);
        CerfProd cerfProd1 = getCerfProdSample1();
        CerfProd cerfProd2 = new CerfProd();
        assertThat(cerfProd1).isNotEqualTo(cerfProd2);

        cerfProd2.setId(cerfProd1.getId());
        assertThat(cerfProd1).isEqualTo(cerfProd2);

        cerfProd2 = getCerfProdSample2();
        assertThat(cerfProd1).isNotEqualTo(cerfProd2);
    }

    @Test
    void cerfTest() throws Exception {
        CerfProd cerfProd = getCerfProdRandomSampleGenerator();
        Cerf cerfBack = getCerfRandomSampleGenerator();

        cerfProd.setCerf(cerfBack);
        assertThat(cerfProd.getCerf()).isEqualTo(cerfBack);

        cerfProd.cerf(null);
        assertThat(cerfProd.getCerf()).isNull();
    }

    @Test
    void prodTest() throws Exception {
        CerfProd cerfProd = getCerfProdRandomSampleGenerator();
        Prod prodBack = getProdRandomSampleGenerator();

        cerfProd.setProd(prodBack);
        assertThat(cerfProd.getProd()).isEqualTo(prodBack);

        cerfProd.prod(null);
        assertThat(cerfProd.getProd()).isNull();
    }
}
