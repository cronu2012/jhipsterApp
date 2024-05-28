package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FeeProdCerfCompanyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FeeProdCerfCompany getFeeProdCerfCompanySample1() {
        return new FeeProdCerfCompany().id(1L).fee(1L).feeType("feeType1");
    }

    public static FeeProdCerfCompany getFeeProdCerfCompanySample2() {
        return new FeeProdCerfCompany().id(2L).fee(2L).feeType("feeType2");
    }

    public static FeeProdCerfCompany getFeeProdCerfCompanyRandomSampleGenerator() {
        return new FeeProdCerfCompany()
            .id(longCount.incrementAndGet())
            .fee(longCount.incrementAndGet())
            .feeType(UUID.randomUUID().toString());
    }
}
