package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryStdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryStdDTO.class);
        CountryStdDTO countryStdDTO1 = new CountryStdDTO();
        countryStdDTO1.setId(1L);
        CountryStdDTO countryStdDTO2 = new CountryStdDTO();
        assertThat(countryStdDTO1).isNotEqualTo(countryStdDTO2);
        countryStdDTO2.setId(countryStdDTO1.getId());
        assertThat(countryStdDTO1).isEqualTo(countryStdDTO2);
        countryStdDTO2.setId(2L);
        assertThat(countryStdDTO1).isNotEqualTo(countryStdDTO2);
        countryStdDTO1.setId(null);
        assertThat(countryStdDTO1).isNotEqualTo(countryStdDTO2);
    }
}
