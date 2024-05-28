package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeeProdCerfCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeProdCerfCompanyDTO.class);
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO1 = new FeeProdCerfCompanyDTO();
        feeProdCerfCompanyDTO1.setId(1L);
        FeeProdCerfCompanyDTO feeProdCerfCompanyDTO2 = new FeeProdCerfCompanyDTO();
        assertThat(feeProdCerfCompanyDTO1).isNotEqualTo(feeProdCerfCompanyDTO2);
        feeProdCerfCompanyDTO2.setId(feeProdCerfCompanyDTO1.getId());
        assertThat(feeProdCerfCompanyDTO1).isEqualTo(feeProdCerfCompanyDTO2);
        feeProdCerfCompanyDTO2.setId(2L);
        assertThat(feeProdCerfCompanyDTO1).isNotEqualTo(feeProdCerfCompanyDTO2);
        feeProdCerfCompanyDTO1.setId(null);
        assertThat(feeProdCerfCompanyDTO1).isNotEqualTo(feeProdCerfCompanyDTO2);
    }
}
