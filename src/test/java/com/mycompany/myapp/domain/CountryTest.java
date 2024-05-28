package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CountryCertTestSamples.*;
import static com.mycompany.myapp.domain.CountryMarkTestSamples.*;
import static com.mycompany.myapp.domain.CountryStdTestSamples.*;
import static com.mycompany.myapp.domain.CountryTestSamples.*;
import static com.mycompany.myapp.domain.ProdCountryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void prodCountryTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        ProdCountry prodCountryBack = getProdCountryRandomSampleGenerator();

        country.addProdCountry(prodCountryBack);
        assertThat(country.getProdCountries()).containsOnly(prodCountryBack);
        assertThat(prodCountryBack.getCountry()).isEqualTo(country);

        country.removeProdCountry(prodCountryBack);
        assertThat(country.getProdCountries()).doesNotContain(prodCountryBack);
        assertThat(prodCountryBack.getCountry()).isNull();

        country.prodCountries(new HashSet<>(Set.of(prodCountryBack)));
        assertThat(country.getProdCountries()).containsOnly(prodCountryBack);
        assertThat(prodCountryBack.getCountry()).isEqualTo(country);

        country.setProdCountries(new HashSet<>());
        assertThat(country.getProdCountries()).doesNotContain(prodCountryBack);
        assertThat(prodCountryBack.getCountry()).isNull();
    }

    @Test
    void countryStdTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        CountryStd countryStdBack = getCountryStdRandomSampleGenerator();

        country.addCountryStd(countryStdBack);
        assertThat(country.getCountryStds()).containsOnly(countryStdBack);
        assertThat(countryStdBack.getCountry()).isEqualTo(country);

        country.removeCountryStd(countryStdBack);
        assertThat(country.getCountryStds()).doesNotContain(countryStdBack);
        assertThat(countryStdBack.getCountry()).isNull();

        country.countryStds(new HashSet<>(Set.of(countryStdBack)));
        assertThat(country.getCountryStds()).containsOnly(countryStdBack);
        assertThat(countryStdBack.getCountry()).isEqualTo(country);

        country.setCountryStds(new HashSet<>());
        assertThat(country.getCountryStds()).doesNotContain(countryStdBack);
        assertThat(countryStdBack.getCountry()).isNull();
    }

    @Test
    void countryCertTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        CountryCert countryCertBack = getCountryCertRandomSampleGenerator();

        country.addCountryCert(countryCertBack);
        assertThat(country.getCountryCerts()).containsOnly(countryCertBack);
        assertThat(countryCertBack.getCountry()).isEqualTo(country);

        country.removeCountryCert(countryCertBack);
        assertThat(country.getCountryCerts()).doesNotContain(countryCertBack);
        assertThat(countryCertBack.getCountry()).isNull();

        country.countryCerts(new HashSet<>(Set.of(countryCertBack)));
        assertThat(country.getCountryCerts()).containsOnly(countryCertBack);
        assertThat(countryCertBack.getCountry()).isEqualTo(country);

        country.setCountryCerts(new HashSet<>());
        assertThat(country.getCountryCerts()).doesNotContain(countryCertBack);
        assertThat(countryCertBack.getCountry()).isNull();
    }

    @Test
    void countryMarkTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        CountryMark countryMarkBack = getCountryMarkRandomSampleGenerator();

        country.addCountryMark(countryMarkBack);
        assertThat(country.getCountryMarks()).containsOnly(countryMarkBack);
        assertThat(countryMarkBack.getCountry()).isEqualTo(country);

        country.removeCountryMark(countryMarkBack);
        assertThat(country.getCountryMarks()).doesNotContain(countryMarkBack);
        assertThat(countryMarkBack.getCountry()).isNull();

        country.countryMarks(new HashSet<>(Set.of(countryMarkBack)));
        assertThat(country.getCountryMarks()).containsOnly(countryMarkBack);
        assertThat(countryMarkBack.getCountry()).isEqualTo(country);

        country.setCountryMarks(new HashSet<>());
        assertThat(country.getCountryMarks()).doesNotContain(countryMarkBack);
        assertThat(countryMarkBack.getCountry()).isNull();
    }
}
