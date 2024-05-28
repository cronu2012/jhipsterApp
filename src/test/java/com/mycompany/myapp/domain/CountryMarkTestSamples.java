package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CountryMarkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CountryMark getCountryMarkSample1() {
        return new CountryMark().id(1L).relType("relType1");
    }

    public static CountryMark getCountryMarkSample2() {
        return new CountryMark().id(2L).relType("relType2");
    }

    public static CountryMark getCountryMarkRandomSampleGenerator() {
        return new CountryMark().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
