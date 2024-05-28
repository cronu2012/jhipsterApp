package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CerfMarkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CerfMark getCerfMarkSample1() {
        return new CerfMark().id(1L).relType("relType1");
    }

    public static CerfMark getCerfMarkSample2() {
        return new CerfMark().id(2L).relType("relType2");
    }

    public static CerfMark getCerfMarkRandomSampleGenerator() {
        return new CerfMark().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
