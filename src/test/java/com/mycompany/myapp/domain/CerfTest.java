package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfCompanyTestSamples.*;
import static com.mycompany.myapp.domain.CerfMarkTestSamples.*;
import static com.mycompany.myapp.domain.CerfProdTestSamples.*;
import static com.mycompany.myapp.domain.CerfStdTestSamples.*;
import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.CountryCertTestSamples.*;
import static com.mycompany.myapp.domain.FeeProdCerfCompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CerfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cerf.class);
        Cerf cerf1 = getCerfSample1();
        Cerf cerf2 = new Cerf();
        assertThat(cerf1).isNotEqualTo(cerf2);

        cerf2.setId(cerf1.getId());
        assertThat(cerf1).isEqualTo(cerf2);

        cerf2 = getCerfSample2();
        assertThat(cerf1).isNotEqualTo(cerf2);
    }

    @Test
    void cerfProdTest() throws Exception {
        Cerf cerf = getCerfRandomSampleGenerator();
        CerfProd cerfProdBack = getCerfProdRandomSampleGenerator();

        cerf.addCerfProd(cerfProdBack);
        assertThat(cerf.getCerfProds()).containsOnly(cerfProdBack);
        assertThat(cerfProdBack.getCerf()).isEqualTo(cerf);

        cerf.removeCerfProd(cerfProdBack);
        assertThat(cerf.getCerfProds()).doesNotContain(cerfProdBack);
        assertThat(cerfProdBack.getCerf()).isNull();

        cerf.cerfProds(new HashSet<>(Set.of(cerfProdBack)));
        assertThat(cerf.getCerfProds()).containsOnly(cerfProdBack);
        assertThat(cerfProdBack.getCerf()).isEqualTo(cerf);

        cerf.setCerfProds(new HashSet<>());
        assertThat(cerf.getCerfProds()).doesNotContain(cerfProdBack);
        assertThat(cerfProdBack.getCerf()).isNull();
    }

    @Test
    void cerfStdTest() throws Exception {
        Cerf cerf = getCerfRandomSampleGenerator();
        CerfStd cerfStdBack = getCerfStdRandomSampleGenerator();

        cerf.addCerfStd(cerfStdBack);
        assertThat(cerf.getCerfStds()).containsOnly(cerfStdBack);
        assertThat(cerfStdBack.getCerf()).isEqualTo(cerf);

        cerf.removeCerfStd(cerfStdBack);
        assertThat(cerf.getCerfStds()).doesNotContain(cerfStdBack);
        assertThat(cerfStdBack.getCerf()).isNull();

        cerf.cerfStds(new HashSet<>(Set.of(cerfStdBack)));
        assertThat(cerf.getCerfStds()).containsOnly(cerfStdBack);
        assertThat(cerfStdBack.getCerf()).isEqualTo(cerf);

        cerf.setCerfStds(new HashSet<>());
        assertThat(cerf.getCerfStds()).doesNotContain(cerfStdBack);
        assertThat(cerfStdBack.getCerf()).isNull();
    }

    @Test
    void cerfMarkTest() throws Exception {
        Cerf cerf = getCerfRandomSampleGenerator();
        CerfMark cerfMarkBack = getCerfMarkRandomSampleGenerator();

        cerf.addCerfMark(cerfMarkBack);
        assertThat(cerf.getCerfMarks()).containsOnly(cerfMarkBack);
        assertThat(cerfMarkBack.getCerf()).isEqualTo(cerf);

        cerf.removeCerfMark(cerfMarkBack);
        assertThat(cerf.getCerfMarks()).doesNotContain(cerfMarkBack);
        assertThat(cerfMarkBack.getCerf()).isNull();

        cerf.cerfMarks(new HashSet<>(Set.of(cerfMarkBack)));
        assertThat(cerf.getCerfMarks()).containsOnly(cerfMarkBack);
        assertThat(cerfMarkBack.getCerf()).isEqualTo(cerf);

        cerf.setCerfMarks(new HashSet<>());
        assertThat(cerf.getCerfMarks()).doesNotContain(cerfMarkBack);
        assertThat(cerfMarkBack.getCerf()).isNull();
    }

    @Test
    void cerfCompanyTest() throws Exception {
        Cerf cerf = getCerfRandomSampleGenerator();
        CerfCompany cerfCompanyBack = getCerfCompanyRandomSampleGenerator();

        cerf.addCerfCompany(cerfCompanyBack);
        assertThat(cerf.getCerfCompanies()).containsOnly(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCerf()).isEqualTo(cerf);

        cerf.removeCerfCompany(cerfCompanyBack);
        assertThat(cerf.getCerfCompanies()).doesNotContain(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCerf()).isNull();

        cerf.cerfCompanies(new HashSet<>(Set.of(cerfCompanyBack)));
        assertThat(cerf.getCerfCompanies()).containsOnly(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCerf()).isEqualTo(cerf);

        cerf.setCerfCompanies(new HashSet<>());
        assertThat(cerf.getCerfCompanies()).doesNotContain(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCerf()).isNull();
    }

    @Test
    void feeProdCerfCompanyTest() throws Exception {
        Cerf cerf = getCerfRandomSampleGenerator();
        FeeProdCerfCompany feeProdCerfCompanyBack = getFeeProdCerfCompanyRandomSampleGenerator();

        cerf.addFeeProdCerfCompany(feeProdCerfCompanyBack);
        assertThat(cerf.getFeeProdCerfCompanies()).containsOnly(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCerf()).isEqualTo(cerf);

        cerf.removeFeeProdCerfCompany(feeProdCerfCompanyBack);
        assertThat(cerf.getFeeProdCerfCompanies()).doesNotContain(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCerf()).isNull();

        cerf.feeProdCerfCompanies(new HashSet<>(Set.of(feeProdCerfCompanyBack)));
        assertThat(cerf.getFeeProdCerfCompanies()).containsOnly(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCerf()).isEqualTo(cerf);

        cerf.setFeeProdCerfCompanies(new HashSet<>());
        assertThat(cerf.getFeeProdCerfCompanies()).doesNotContain(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCerf()).isNull();
    }

    @Test
    void countryCertTest() throws Exception {
        Cerf cerf = getCerfRandomSampleGenerator();
        CountryCert countryCertBack = getCountryCertRandomSampleGenerator();

        cerf.addCountryCert(countryCertBack);
        assertThat(cerf.getCountryCerts()).containsOnly(countryCertBack);
        assertThat(countryCertBack.getCerf()).isEqualTo(cerf);

        cerf.removeCountryCert(countryCertBack);
        assertThat(cerf.getCountryCerts()).doesNotContain(countryCertBack);
        assertThat(countryCertBack.getCerf()).isNull();

        cerf.countryCerts(new HashSet<>(Set.of(countryCertBack)));
        assertThat(cerf.getCountryCerts()).containsOnly(countryCertBack);
        assertThat(countryCertBack.getCerf()).isEqualTo(cerf);

        cerf.setCountryCerts(new HashSet<>());
        assertThat(cerf.getCountryCerts()).doesNotContain(countryCertBack);
        assertThat(countryCertBack.getCerf()).isNull();
    }
}
