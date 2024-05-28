package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StdDTO.class);
        StdDTO stdDTO1 = new StdDTO();
        stdDTO1.setId(1L);
        StdDTO stdDTO2 = new StdDTO();
        assertThat(stdDTO1).isNotEqualTo(stdDTO2);
        stdDTO2.setId(stdDTO1.getId());
        assertThat(stdDTO1).isEqualTo(stdDTO2);
        stdDTO2.setId(2L);
        assertThat(stdDTO1).isNotEqualTo(stdDTO2);
        stdDTO1.setId(null);
        assertThat(stdDTO1).isNotEqualTo(stdDTO2);
    }
}
