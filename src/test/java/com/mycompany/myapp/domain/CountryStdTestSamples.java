package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CountryStdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CountryStd getCountryStdSample1() {
        return new CountryStd().id(1L).relType("relType1");
    }

    public static CountryStd getCountryStdSample2() {
        return new CountryStd().id(2L).relType("relType2");
    }

    public static CountryStd getCountryStdRandomSampleGenerator() {
        return new CountryStd().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
