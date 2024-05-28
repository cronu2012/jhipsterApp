package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prod getProdSample1() {
        return new Prod().id(1L).prodNo("prodNo1").enName("enName1").chName("chName1").hsCode("hsCode1").cccCode("cccCode1");
    }

    public static Prod getProdSample2() {
        return new Prod().id(2L).prodNo("prodNo2").enName("enName2").chName("chName2").hsCode("hsCode2").cccCode("cccCode2");
    }

    public static Prod getProdRandomSampleGenerator() {
        return new Prod()
            .id(longCount.incrementAndGet())
            .prodNo(UUID.randomUUID().toString())
            .enName(UUID.randomUUID().toString())
            .chName(UUID.randomUUID().toString())
            .hsCode(UUID.randomUUID().toString())
            .cccCode(UUID.randomUUID().toString());
    }
}
