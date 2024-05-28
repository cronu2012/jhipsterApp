package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CountryCertTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CountryCert getCountryCertSample1() {
        return new CountryCert().id(1L).relType("relType1");
    }

    public static CountryCert getCountryCertSample2() {
        return new CountryCert().id(2L).relType("relType2");
    }

    public static CountryCert getCountryCertRandomSampleGenerator() {
        return new CountryCert().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
