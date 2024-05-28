package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProdStdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProdStd getProdStdSample1() {
        return new ProdStd().id(1L).relType("relType1");
    }

    public static ProdStd getProdStdSample2() {
        return new ProdStd().id(2L).relType("relType2");
    }

    public static ProdStd getProdStdRandomSampleGenerator() {
        return new ProdStd().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
