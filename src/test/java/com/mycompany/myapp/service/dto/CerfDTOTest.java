package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfDTO.class);
        CerfDTO cerfDTO1 = new CerfDTO();
        cerfDTO1.setId(1L);
        CerfDTO cerfDTO2 = new CerfDTO();
        assertThat(cerfDTO1).isNotEqualTo(cerfDTO2);
        cerfDTO2.setId(cerfDTO1.getId());
        assertThat(cerfDTO1).isEqualTo(cerfDTO2);
        cerfDTO2.setId(2L);
        assertThat(cerfDTO1).isNotEqualTo(cerfDTO2);
        cerfDTO1.setId(null);
        assertThat(cerfDTO1).isNotEqualTo(cerfDTO2);
    }
}
