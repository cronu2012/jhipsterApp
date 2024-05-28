package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.CountryCertTestSamples.*;
import static com.mycompany.myapp.domain.CountryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryCertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryCert.class);
        CountryCert countryCert1 = getCountryCertSample1();
        CountryCert countryCert2 = new CountryCert();
        assertThat(countryCert1).isNotEqualTo(countryCert2);

        countryCert2.setId(countryCert1.getId());
        assertThat(countryCert1).isEqualTo(countryCert2);

        countryCert2 = getCountryCertSample2();
        assertThat(countryCert1).isNotEqualTo(countryCert2);
    }

    @Test
    void countryTest() throws Exception {
        CountryCert countryCert = getCountryCertRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        countryCert.setCountry(countryBack);
        assertThat(countryCert.getCountry()).isEqualTo(countryBack);

        countryCert.country(null);
        assertThat(countryCert.getCountry()).isNull();
    }

    @Test
    void cerfTest() throws Exception {
        CountryCert countryCert = getCountryCertRandomSampleGenerator();
        Cerf cerfBack = getCerfRandomSampleGenerator();

        countryCert.setCerf(cerfBack);
        assertThat(countryCert.getCerf()).isEqualTo(cerfBack);

        countryCert.cerf(null);
        assertThat(countryCert.getCerf()).isNull();
    }
}
