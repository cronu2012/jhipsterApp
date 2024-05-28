package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CerfStdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CerfStd getCerfStdSample1() {
        return new CerfStd().id(1L).relType("relType1");
    }

    public static CerfStd getCerfStdSample2() {
        return new CerfStd().id(2L).relType("relType2");
    }

    public static CerfStd getCerfStdRandomSampleGenerator() {
        return new CerfStd().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
