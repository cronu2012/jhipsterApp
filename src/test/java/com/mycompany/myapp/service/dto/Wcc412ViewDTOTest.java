package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Wcc412ViewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wcc412ViewDTO.class);
        Wcc412ViewDTO wcc412ViewDTO1 = new Wcc412ViewDTO();
        wcc412ViewDTO1.setId(1L);
        Wcc412ViewDTO wcc412ViewDTO2 = new Wcc412ViewDTO();
        assertThat(wcc412ViewDTO1).isNotEqualTo(wcc412ViewDTO2);
        wcc412ViewDTO2.setId(wcc412ViewDTO1.getId());
        assertThat(wcc412ViewDTO1).isEqualTo(wcc412ViewDTO2);
        wcc412ViewDTO2.setId(2L);
        assertThat(wcc412ViewDTO1).isNotEqualTo(wcc412ViewDTO2);
        wcc412ViewDTO1.setId(null);
        assertThat(wcc412ViewDTO1).isNotEqualTo(wcc412ViewDTO2);
    }
}
