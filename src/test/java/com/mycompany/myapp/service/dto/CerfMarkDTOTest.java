package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfMarkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfMarkDTO.class);
        CerfMarkDTO cerfMarkDTO1 = new CerfMarkDTO();
        cerfMarkDTO1.setId(1L);
        CerfMarkDTO cerfMarkDTO2 = new CerfMarkDTO();
        assertThat(cerfMarkDTO1).isNotEqualTo(cerfMarkDTO2);
        cerfMarkDTO2.setId(cerfMarkDTO1.getId());
        assertThat(cerfMarkDTO1).isEqualTo(cerfMarkDTO2);
        cerfMarkDTO2.setId(2L);
        assertThat(cerfMarkDTO1).isNotEqualTo(cerfMarkDTO2);
        cerfMarkDTO1.setId(null);
        assertThat(cerfMarkDTO1).isNotEqualTo(cerfMarkDTO2);
    }
}
