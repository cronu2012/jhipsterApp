package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryCertDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryCertDTO.class);
        CountryCertDTO countryCertDTO1 = new CountryCertDTO();
        countryCertDTO1.setId(1L);
        CountryCertDTO countryCertDTO2 = new CountryCertDTO();
        assertThat(countryCertDTO1).isNotEqualTo(countryCertDTO2);
        countryCertDTO2.setId(countryCertDTO1.getId());
        assertThat(countryCertDTO1).isEqualTo(countryCertDTO2);
        countryCertDTO2.setId(2L);
        assertThat(countryCertDTO1).isNotEqualTo(countryCertDTO2);
        countryCertDTO1.setId(null);
        assertThat(countryCertDTO1).isNotEqualTo(countryCertDTO2);
    }
}
