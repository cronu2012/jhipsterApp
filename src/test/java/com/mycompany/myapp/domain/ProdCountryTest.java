package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CountryTestSamples.*;
import static com.mycompany.myapp.domain.ProdCountryTestSamples.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdCountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdCountry.class);
        ProdCountry prodCountry1 = getProdCountrySample1();
        ProdCountry prodCountry2 = new ProdCountry();
        assertThat(prodCountry1).isNotEqualTo(prodCountry2);

        prodCountry2.setId(prodCountry1.getId());
        assertThat(prodCountry1).isEqualTo(prodCountry2);

        prodCountry2 = getProdCountrySample2();
        assertThat(prodCountry1).isNotEqualTo(prodCountry2);
    }

    @Test
    void prodTest() throws Exception {
        ProdCountry prodCountry = getProdCountryRandomSampleGenerator();
        Prod prodBack = getProdRandomSampleGenerator();

        prodCountry.setProd(prodBack);
        assertThat(prodCountry.getProd()).isEqualTo(prodBack);

        prodCountry.prod(null);
        assertThat(prodCountry.getProd()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        ProdCountry prodCountry = getProdCountryRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        prodCountry.setCountry(countryBack);
        assertThat(prodCountry.getCountry()).isEqualTo(countryBack);

        prodCountry.country(null);
        assertThat(prodCountry.getCountry()).isNull();
    }
}
