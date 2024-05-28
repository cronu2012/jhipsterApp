package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdDTO.class);
        ProdDTO prodDTO1 = new ProdDTO();
        prodDTO1.setId(1L);
        ProdDTO prodDTO2 = new ProdDTO();
        assertThat(prodDTO1).isNotEqualTo(prodDTO2);
        prodDTO2.setId(prodDTO1.getId());
        assertThat(prodDTO1).isEqualTo(prodDTO2);
        prodDTO2.setId(2L);
        assertThat(prodDTO1).isNotEqualTo(prodDTO2);
        prodDTO1.setId(null);
        assertThat(prodDTO1).isNotEqualTo(prodDTO2);
    }
}
