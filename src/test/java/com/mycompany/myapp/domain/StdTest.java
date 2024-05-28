package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfStdTestSamples.*;
import static com.mycompany.myapp.domain.CountryStdTestSamples.*;
import static com.mycompany.myapp.domain.ProdStdTestSamples.*;
import static com.mycompany.myapp.domain.StdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Std.class);
        Std std1 = getStdSample1();
        Std std2 = new Std();
        assertThat(std1).isNotEqualTo(std2);

        std2.setId(std1.getId());
        assertThat(std1).isEqualTo(std2);

        std2 = getStdSample2();
        assertThat(std1).isNotEqualTo(std2);
    }

    @Test
    void prodStdTest() throws Exception {
        Std std = getStdRandomSampleGenerator();
        ProdStd prodStdBack = getProdStdRandomSampleGenerator();

        std.addProdStd(prodStdBack);
        assertThat(std.getProdStds()).containsOnly(prodStdBack);
        assertThat(prodStdBack.getStd()).isEqualTo(std);

        std.removeProdStd(prodStdBack);
        assertThat(std.getProdStds()).doesNotContain(prodStdBack);
        assertThat(prodStdBack.getStd()).isNull();

        std.prodStds(new HashSet<>(Set.of(prodStdBack)));
        assertThat(std.getProdStds()).containsOnly(prodStdBack);
        assertThat(prodStdBack.getStd()).isEqualTo(std);

        std.setProdStds(new HashSet<>());
        assertThat(std.getProdStds()).doesNotContain(prodStdBack);
        assertThat(prodStdBack.getStd()).isNull();
    }

    @Test
    void cerfStdTest() throws Exception {
        Std std = getStdRandomSampleGenerator();
        CerfStd cerfStdBack = getCerfStdRandomSampleGenerator();

        std.addCerfStd(cerfStdBack);
        assertThat(std.getCerfStds()).containsOnly(cerfStdBack);
        assertThat(cerfStdBack.getStd()).isEqualTo(std);

        std.removeCerfStd(cerfStdBack);
        assertThat(std.getCerfStds()).doesNotContain(cerfStdBack);
        assertThat(cerfStdBack.getStd()).isNull();

        std.cerfStds(new HashSet<>(Set.of(cerfStdBack)));
        assertThat(std.getCerfStds()).containsOnly(cerfStdBack);
        assertThat(cerfStdBack.getStd()).isEqualTo(std);

        std.setCerfStds(new HashSet<>());
        assertThat(std.getCerfStds()).doesNotContain(cerfStdBack);
        assertThat(cerfStdBack.getStd()).isNull();
    }

    @Test
    void countryStdTest() throws Exception {
        Std std = getStdRandomSampleGenerator();
        CountryStd countryStdBack = getCountryStdRandomSampleGenerator();

        std.addCountryStd(countryStdBack);
        assertThat(std.getCountryStds()).containsOnly(countryStdBack);
        assertThat(countryStdBack.getStd()).isEqualTo(std);

        std.removeCountryStd(countryStdBack);
        assertThat(std.getCountryStds()).doesNotContain(countryStdBack);
        assertThat(countryStdBack.getStd()).isNull();

        std.countryStds(new HashSet<>(Set.of(countryStdBack)));
        assertThat(std.getCountryStds()).containsOnly(countryStdBack);
        assertThat(countryStdBack.getStd()).isEqualTo(std);

        std.setCountryStds(new HashSet<>());
        assertThat(std.getCountryStds()).doesNotContain(countryStdBack);
        assertThat(countryStdBack.getStd()).isNull();
    }
}
