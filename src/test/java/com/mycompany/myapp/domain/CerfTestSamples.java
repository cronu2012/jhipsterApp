package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CerfTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cerf getCerfSample1() {
        return new Cerf().id(1L).cerfNo("cerfNo1").cerfVer("cerfVer1").status("status1");
    }

    public static Cerf getCerfSample2() {
        return new Cerf().id(2L).cerfNo("cerfNo2").cerfVer("cerfVer2").status("status2");
    }

    public static Cerf getCerfRandomSampleGenerator() {
        return new Cerf()
            .id(longCount.incrementAndGet())
            .cerfNo(UUID.randomUUID().toString())
            .cerfVer(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
