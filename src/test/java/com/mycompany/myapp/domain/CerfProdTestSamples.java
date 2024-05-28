package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CerfProdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CerfProd getCerfProdSample1() {
        return new CerfProd().id(1L).relType("relType1");
    }

    public static CerfProd getCerfProdSample2() {
        return new CerfProd().id(2L).relType("relType2");
    }

    public static CerfProd getCerfProdRandomSampleGenerator() {
        return new CerfProd().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
