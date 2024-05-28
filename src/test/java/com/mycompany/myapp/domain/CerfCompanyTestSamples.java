package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CerfCompanyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CerfCompany getCerfCompanySample1() {
        return new CerfCompany().id(1L).relType("relType1");
    }

    public static CerfCompany getCerfCompanySample2() {
        return new CerfCompany().id(2L).relType("relType2");
    }

    public static CerfCompany getCerfCompanyRandomSampleGenerator() {
        return new CerfCompany().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
