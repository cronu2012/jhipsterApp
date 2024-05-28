package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfProdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfProdDTO.class);
        CerfProdDTO cerfProdDTO1 = new CerfProdDTO();
        cerfProdDTO1.setId(1L);
        CerfProdDTO cerfProdDTO2 = new CerfProdDTO();
        assertThat(cerfProdDTO1).isNotEqualTo(cerfProdDTO2);
        cerfProdDTO2.setId(cerfProdDTO1.getId());
        assertThat(cerfProdDTO1).isEqualTo(cerfProdDTO2);
        cerfProdDTO2.setId(2L);
        assertThat(cerfProdDTO1).isNotEqualTo(cerfProdDTO2);
        cerfProdDTO1.setId(null);
        assertThat(cerfProdDTO1).isNotEqualTo(cerfProdDTO2);
    }
}
