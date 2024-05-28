package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfStdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfStdDTO.class);
        CerfStdDTO cerfStdDTO1 = new CerfStdDTO();
        cerfStdDTO1.setId(1L);
        CerfStdDTO cerfStdDTO2 = new CerfStdDTO();
        assertThat(cerfStdDTO1).isNotEqualTo(cerfStdDTO2);
        cerfStdDTO2.setId(cerfStdDTO1.getId());
        assertThat(cerfStdDTO1).isEqualTo(cerfStdDTO2);
        cerfStdDTO2.setId(2L);
        assertThat(cerfStdDTO1).isNotEqualTo(cerfStdDTO2);
        cerfStdDTO1.setId(null);
        assertThat(cerfStdDTO1).isNotEqualTo(cerfStdDTO2);
    }
}
