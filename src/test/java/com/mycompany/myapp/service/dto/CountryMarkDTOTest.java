package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryMarkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryMarkDTO.class);
        CountryMarkDTO countryMarkDTO1 = new CountryMarkDTO();
        countryMarkDTO1.setId(1L);
        CountryMarkDTO countryMarkDTO2 = new CountryMarkDTO();
        assertThat(countryMarkDTO1).isNotEqualTo(countryMarkDTO2);
        countryMarkDTO2.setId(countryMarkDTO1.getId());
        assertThat(countryMarkDTO1).isEqualTo(countryMarkDTO2);
        countryMarkDTO2.setId(2L);
        assertThat(countryMarkDTO1).isNotEqualTo(countryMarkDTO2);
        countryMarkDTO1.setId(null);
        assertThat(countryMarkDTO1).isNotEqualTo(countryMarkDTO2);
    }
}
