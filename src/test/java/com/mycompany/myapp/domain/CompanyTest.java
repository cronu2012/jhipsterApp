package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfCompanyTestSamples.*;
import static com.mycompany.myapp.domain.CompanyTestSamples.*;
import static com.mycompany.myapp.domain.FeeProdCerfCompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = getCompanySample1();
        Company company2 = new Company();
        assertThat(company1).isNotEqualTo(company2);

        company2.setId(company1.getId());
        assertThat(company1).isEqualTo(company2);

        company2 = getCompanySample2();
        assertThat(company1).isNotEqualTo(company2);
    }

    @Test
    void cerfCompanyTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        CerfCompany cerfCompanyBack = getCerfCompanyRandomSampleGenerator();

        company.addCerfCompany(cerfCompanyBack);
        assertThat(company.getCerfCompanies()).containsOnly(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCompany()).isEqualTo(company);

        company.removeCerfCompany(cerfCompanyBack);
        assertThat(company.getCerfCompanies()).doesNotContain(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCompany()).isNull();

        company.cerfCompanies(new HashSet<>(Set.of(cerfCompanyBack)));
        assertThat(company.getCerfCompanies()).containsOnly(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCompany()).isEqualTo(company);

        company.setCerfCompanies(new HashSet<>());
        assertThat(company.getCerfCompanies()).doesNotContain(cerfCompanyBack);
        assertThat(cerfCompanyBack.getCompany()).isNull();
    }

    @Test
    void feeProdCerfCompanyTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        FeeProdCerfCompany feeProdCerfCompanyBack = getFeeProdCerfCompanyRandomSampleGenerator();

        company.addFeeProdCerfCompany(feeProdCerfCompanyBack);
        assertThat(company.getFeeProdCerfCompanies()).containsOnly(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCompany()).isEqualTo(company);

        company.removeFeeProdCerfCompany(feeProdCerfCompanyBack);
        assertThat(company.getFeeProdCerfCompanies()).doesNotContain(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCompany()).isNull();

        company.feeProdCerfCompanies(new HashSet<>(Set.of(feeProdCerfCompanyBack)));
        assertThat(company.getFeeProdCerfCompanies()).containsOnly(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCompany()).isEqualTo(company);

        company.setFeeProdCerfCompanies(new HashSet<>());
        assertThat(company.getFeeProdCerfCompanies()).doesNotContain(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getCompany()).isNull();
    }
}
