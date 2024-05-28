package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CerfProdTestSamples.*;
import static com.mycompany.myapp.domain.FeeProdCerfCompanyTestSamples.*;
import static com.mycompany.myapp.domain.ProdCountryTestSamples.*;
import static com.mycompany.myapp.domain.ProdStdTestSamples.*;
import static com.mycompany.myapp.domain.ProdStickerTestSamples.*;
import static com.mycompany.myapp.domain.ProdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prod.class);
        Prod prod1 = getProdSample1();
        Prod prod2 = new Prod();
        assertThat(prod1).isNotEqualTo(prod2);

        prod2.setId(prod1.getId());
        assertThat(prod1).isEqualTo(prod2);

        prod2 = getProdSample2();
        assertThat(prod1).isNotEqualTo(prod2);
    }

    @Test
    void prodCountryTest() throws Exception {
        Prod prod = getProdRandomSampleGenerator();
        ProdCountry prodCountryBack = getProdCountryRandomSampleGenerator();

        prod.addProdCountry(prodCountryBack);
        assertThat(prod.getProdCountries()).containsOnly(prodCountryBack);
        assertThat(prodCountryBack.getProd()).isEqualTo(prod);

        prod.removeProdCountry(prodCountryBack);
        assertThat(prod.getProdCountries()).doesNotContain(prodCountryBack);
        assertThat(prodCountryBack.getProd()).isNull();

        prod.prodCountries(new HashSet<>(Set.of(prodCountryBack)));
        assertThat(prod.getProdCountries()).containsOnly(prodCountryBack);
        assertThat(prodCountryBack.getProd()).isEqualTo(prod);

        prod.setProdCountries(new HashSet<>());
        assertThat(prod.getProdCountries()).doesNotContain(prodCountryBack);
        assertThat(prodCountryBack.getProd()).isNull();
    }

    @Test
    void prodStdTest() throws Exception {
        Prod prod = getProdRandomSampleGenerator();
        ProdStd prodStdBack = getProdStdRandomSampleGenerator();

        prod.addProdStd(prodStdBack);
        assertThat(prod.getProdStds()).containsOnly(prodStdBack);
        assertThat(prodStdBack.getProd()).isEqualTo(prod);

        prod.removeProdStd(prodStdBack);
        assertThat(prod.getProdStds()).doesNotContain(prodStdBack);
        assertThat(prodStdBack.getProd()).isNull();

        prod.prodStds(new HashSet<>(Set.of(prodStdBack)));
        assertThat(prod.getProdStds()).containsOnly(prodStdBack);
        assertThat(prodStdBack.getProd()).isEqualTo(prod);

        prod.setProdStds(new HashSet<>());
        assertThat(prod.getProdStds()).doesNotContain(prodStdBack);
        assertThat(prodStdBack.getProd()).isNull();
    }

    @Test
    void cerfProdTest() throws Exception {
        Prod prod = getProdRandomSampleGenerator();
        CerfProd cerfProdBack = getCerfProdRandomSampleGenerator();

        prod.addCerfProd(cerfProdBack);
        assertThat(prod.getCerfProds()).containsOnly(cerfProdBack);
        assertThat(cerfProdBack.getProd()).isEqualTo(prod);

        prod.removeCerfProd(cerfProdBack);
        assertThat(prod.getCerfProds()).doesNotContain(cerfProdBack);
        assertThat(cerfProdBack.getProd()).isNull();

        prod.cerfProds(new HashSet<>(Set.of(cerfProdBack)));
        assertThat(prod.getCerfProds()).containsOnly(cerfProdBack);
        assertThat(cerfProdBack.getProd()).isEqualTo(prod);

        prod.setCerfProds(new HashSet<>());
        assertThat(prod.getCerfProds()).doesNotContain(cerfProdBack);
        assertThat(cerfProdBack.getProd()).isNull();
    }

    @Test
    void feeProdCerfCompanyTest() throws Exception {
        Prod prod = getProdRandomSampleGenerator();
        FeeProdCerfCompany feeProdCerfCompanyBack = getFeeProdCerfCompanyRandomSampleGenerator();

        prod.addFeeProdCerfCompany(feeProdCerfCompanyBack);
        assertThat(prod.getFeeProdCerfCompanies()).containsOnly(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getProd()).isEqualTo(prod);

        prod.removeFeeProdCerfCompany(feeProdCerfCompanyBack);
        assertThat(prod.getFeeProdCerfCompanies()).doesNotContain(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getProd()).isNull();

        prod.feeProdCerfCompanies(new HashSet<>(Set.of(feeProdCerfCompanyBack)));
        assertThat(prod.getFeeProdCerfCompanies()).containsOnly(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getProd()).isEqualTo(prod);

        prod.setFeeProdCerfCompanies(new HashSet<>());
        assertThat(prod.getFeeProdCerfCompanies()).doesNotContain(feeProdCerfCompanyBack);
        assertThat(feeProdCerfCompanyBack.getProd()).isNull();
    }

    @Test
    void prodStickerTest() throws Exception {
        Prod prod = getProdRandomSampleGenerator();
        ProdSticker prodStickerBack = getProdStickerRandomSampleGenerator();

        prod.addProdSticker(prodStickerBack);
        assertThat(prod.getProdStickers()).containsOnly(prodStickerBack);
        assertThat(prodStickerBack.getProd()).isEqualTo(prod);

        prod.removeProdSticker(prodStickerBack);
        assertThat(prod.getProdStickers()).doesNotContain(prodStickerBack);
        assertThat(prodStickerBack.getProd()).isNull();

        prod.prodStickers(new HashSet<>(Set.of(prodStickerBack)));
        assertThat(prod.getProdStickers()).containsOnly(prodStickerBack);
        assertThat(prodStickerBack.getProd()).isEqualTo(prod);

        prod.setProdStickers(new HashSet<>());
        assertThat(prod.getProdStickers()).doesNotContain(prodStickerBack);
        assertThat(prodStickerBack.getProd()).isNull();
    }
}
