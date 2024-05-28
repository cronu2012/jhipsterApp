package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CountryMarkTestSamples.*;
import static com.mycompany.myapp.domain.CountryTestSamples.*;
import static com.mycompany.myapp.domain.MarkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryMarkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryMark.class);
        CountryMark countryMark1 = getCountryMarkSample1();
        CountryMark countryMark2 = new CountryMark();
        assertThat(countryMark1).isNotEqualTo(countryMark2);

        countryMark2.setId(countryMark1.getId());
        assertThat(countryMark1).isEqualTo(countryMark2);

        countryMark2 = getCountryMarkSample2();
        assertThat(countryMark1).isNotEqualTo(countryMark2);
    }

    @Test
    void countryTest() throws Exception {
        CountryMark countryMark = getCountryMarkRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        countryMark.setCountry(countryBack);
        assertThat(countryMark.getCountry()).isEqualTo(countryBack);

        countryMark.country(null);
        assertThat(countryMark.getCountry()).isNull();
    }

    @Test
    void markTest() throws Exception {
        CountryMark countryMark = getCountryMarkRandomSampleGenerator();
        Mark markBack = getMarkRandomSampleGenerator();

        countryMark.setMark(markBack);
        assertThat(countryMark.getMark()).isEqualTo(markBack);

        countryMark.mark(null);
        assertThat(countryMark.getMark()).isNull();
    }
}
