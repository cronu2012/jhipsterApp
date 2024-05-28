package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CountryStdTestSamples.*;
import static com.mycompany.myapp.domain.CountryTestSamples.*;
import static com.mycompany.myapp.domain.StdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryStdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryStd.class);
        CountryStd countryStd1 = getCountryStdSample1();
        CountryStd countryStd2 = new CountryStd();
        assertThat(countryStd1).isNotEqualTo(countryStd2);

        countryStd2.setId(countryStd1.getId());
        assertThat(countryStd1).isEqualTo(countryStd2);

        countryStd2 = getCountryStdSample2();
        assertThat(countryStd1).isNotEqualTo(countryStd2);
    }

    @Test
    void countryTest() throws Exception {
        CountryStd countryStd = getCountryStdRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        countryStd.setCountry(countryBack);
        assertThat(countryStd.getCountry()).isEqualTo(countryBack);

        countryStd.country(null);
        assertThat(countryStd.getCountry()).isNull();
    }

    @Test
    void stdTest() throws Exception {
        CountryStd countryStd = getCountryStdRandomSampleGenerator();
        Std stdBack = getStdRandomSampleGenerator();

        countryStd.setStd(stdBack);
        assertThat(countryStd.getStd()).isEqualTo(stdBack);

        countryStd.std(null);
        assertThat(countryStd.getStd()).isNull();
    }
}
