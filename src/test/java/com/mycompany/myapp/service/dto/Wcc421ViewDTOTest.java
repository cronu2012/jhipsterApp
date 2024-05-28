package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Wcc421ViewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wcc421ViewDTO.class);
        Wcc421ViewDTO wcc421ViewDTO1 = new Wcc421ViewDTO();
        wcc421ViewDTO1.setId(1L);
        Wcc421ViewDTO wcc421ViewDTO2 = new Wcc421ViewDTO();
        assertThat(wcc421ViewDTO1).isNotEqualTo(wcc421ViewDTO2);
        wcc421ViewDTO2.setId(wcc421ViewDTO1.getId());
        assertThat(wcc421ViewDTO1).isEqualTo(wcc421ViewDTO2);
        wcc421ViewDTO2.setId(2L);
        assertThat(wcc421ViewDTO1).isNotEqualTo(wcc421ViewDTO2);
        wcc421ViewDTO1.setId(null);
        assertThat(wcc421ViewDTO1).isNotEqualTo(wcc421ViewDTO2);
    }
}
