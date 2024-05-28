package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdCountryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdCountryDTO.class);
        ProdCountryDTO prodCountryDTO1 = new ProdCountryDTO();
        prodCountryDTO1.setId(1L);
        ProdCountryDTO prodCountryDTO2 = new ProdCountryDTO();
        assertThat(prodCountryDTO1).isNotEqualTo(prodCountryDTO2);
        prodCountryDTO2.setId(prodCountryDTO1.getId());
        assertThat(prodCountryDTO1).isEqualTo(prodCountryDTO2);
        prodCountryDTO2.setId(2L);
        assertThat(prodCountryDTO1).isNotEqualTo(prodCountryDTO2);
        prodCountryDTO1.setId(null);
        assertThat(prodCountryDTO1).isNotEqualTo(prodCountryDTO2);
    }
}
