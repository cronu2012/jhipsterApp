package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfCompanyDTO.class);
        CerfCompanyDTO cerfCompanyDTO1 = new CerfCompanyDTO();
        cerfCompanyDTO1.setId(1L);
        CerfCompanyDTO cerfCompanyDTO2 = new CerfCompanyDTO();
        assertThat(cerfCompanyDTO1).isNotEqualTo(cerfCompanyDTO2);
        cerfCompanyDTO2.setId(cerfCompanyDTO1.getId());
        assertThat(cerfCompanyDTO1).isEqualTo(cerfCompanyDTO2);
        cerfCompanyDTO2.setId(2L);
        assertThat(cerfCompanyDTO1).isNotEqualTo(cerfCompanyDTO2);
        cerfCompanyDTO1.setId(null);
        assertThat(cerfCompanyDTO1).isNotEqualTo(cerfCompanyDTO2);
    }
}
