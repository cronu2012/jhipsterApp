package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProdCountryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProdCountry getProdCountrySample1() {
        return new ProdCountry().id(1L).relType("relType1");
    }

    public static ProdCountry getProdCountrySample2() {
        return new ProdCountry().id(2L).relType("relType2");
    }

    public static ProdCountry getProdCountryRandomSampleGenerator() {
        return new ProdCountry().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
