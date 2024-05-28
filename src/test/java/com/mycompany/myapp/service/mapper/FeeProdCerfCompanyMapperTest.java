package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.FeeProdCerfCompanyAsserts.*;
import static com.mycompany.myapp.domain.FeeProdCerfCompanyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeeProdCerfCompanyMapperTest {

    private FeeProdCerfCompanyMapper feeProdCerfCompanyMapper;

    @BeforeEach
    void setUp() {
        feeProdCerfCompanyMapper = new FeeProdCerfCompanyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFeeProdCerfCompanySample1();
        var actual = feeProdCerfCompanyMapper.toEntity(feeProdCerfCompanyMapper.toDto(expected));
        assertFeeProdCerfCompanyAllPropertiesEquals(expected, actual);
    }
}
