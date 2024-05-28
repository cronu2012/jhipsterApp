package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdStdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdStdDTO.class);
        ProdStdDTO prodStdDTO1 = new ProdStdDTO();
        prodStdDTO1.setId(1L);
        ProdStdDTO prodStdDTO2 = new ProdStdDTO();
        assertThat(prodStdDTO1).isNotEqualTo(prodStdDTO2);
        prodStdDTO2.setId(prodStdDTO1.getId());
        assertThat(prodStdDTO1).isEqualTo(prodStdDTO2);
        prodStdDTO2.setId(2L);
        assertThat(prodStdDTO1).isNotEqualTo(prodStdDTO2);
        prodStdDTO1.setId(null);
        assertThat(prodStdDTO1).isNotEqualTo(prodStdDTO2);
    }
}
