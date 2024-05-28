package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfCompanyTestSamples.*;
import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.CompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CerfCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CerfCompany.class);
        CerfCompany cerfCompany1 = getCerfCompanySample1();
        CerfCompany cerfCompany2 = new CerfCompany();
        assertThat(cerfCompany1).isNotEqualTo(cerfCompany2);

        cerfCompany2.setId(cerfCompany1.getId());
        assertThat(cerfCompany1).isEqualTo(cerfCompany2);

        cerfCompany2 = getCerfCompanySample2();
        assertThat(cerfCompany1).isNotEqualTo(cerfCompany2);
    }

    @Test
    void cerfTest() throws Exception {
        CerfCompany cerfCompany = getCerfCompanyRandomSampleGenerator();
        Cerf cerfBack = getCerfRandomSampleGenerator();

        cerfCompany.setCerf(cerfBack);
        assertThat(cerfCompany.getCerf()).isEqualTo(cerfBack);

        cerfCompany.cerf(null);
        assertThat(cerfCompany.getCerf()).isNull();
    }

    @Test
    void companyTest() throws Exception {
        CerfCompany cerfCompany = getCerfCompanyRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        cerfCompany.setCompany(companyBack);
        assertThat(cerfCompany.getCompany()).isEqualTo(companyBack);

        cerfCompany.company(null);
        assertThat(cerfCompany.getCompany()).isNull();
    }
}
