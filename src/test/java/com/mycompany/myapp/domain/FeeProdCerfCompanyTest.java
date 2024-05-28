package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfTestSamples.*;
import static com.mycompany.myapp.domain.CompanyTestSamples.*;
import static com.mycompany.myapp.domain.FeeProdCerfCompanyTestSamples.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeeProdCerfCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeProdCerfCompany.class);
        FeeProdCerfCompany feeProdCerfCompany1 = getFeeProdCerfCompanySample1();
        FeeProdCerfCompany feeProdCerfCompany2 = new FeeProdCerfCompany();
        assertThat(feeProdCerfCompany1).isNotEqualTo(feeProdCerfCompany2);

        feeProdCerfCompany2.setId(feeProdCerfCompany1.getId());
        assertThat(feeProdCerfCompany1).isEqualTo(feeProdCerfCompany2);

        feeProdCerfCompany2 = getFeeProdCerfCompanySample2();
        assertThat(feeProdCerfCompany1).isNotEqualTo(feeProdCerfCompany2);
    }

    @Test
    void prodTest() throws Exception {
        FeeProdCerfCompany feeProdCerfCompany = getFeeProdCerfCompanyRandomSampleGenerator();
        Prod prodBack = getProdRandomSampleGenerator();

        feeProdCerfCompany.setProd(prodBack);
        assertThat(feeProdCerfCompany.getProd()).isEqualTo(prodBack);

        feeProdCerfCompany.prod(null);
        assertThat(feeProdCerfCompany.getProd()).isNull();
    }

    @Test
    void cerfTest() throws Exception {
        FeeProdCerfCompany feeProdCerfCompany = getFeeProdCerfCompanyRandomSampleGenerator();
        Cerf cerfBack = getCerfRandomSampleGenerator();

        feeProdCerfCompany.setCerf(cerfBack);
        assertThat(feeProdCerfCompany.getCerf()).isEqualTo(cerfBack);

        feeProdCerfCompany.cerf(null);
        assertThat(feeProdCerfCompany.getCerf()).isNull();
    }

    @Test
    void companyTest() throws Exception {
        FeeProdCerfCompany feeProdCerfCompany = getFeeProdCerfCompanyRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        feeProdCerfCompany.setCompany(companyBack);
        assertThat(feeProdCerfCompany.getCompany()).isEqualTo(companyBack);

        feeProdCerfCompany.company(null);
        assertThat(feeProdCerfCompany.getCompany()).isNull();
    }
}
