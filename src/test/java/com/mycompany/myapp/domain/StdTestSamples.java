package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Std getStdSample1() {
        return new Std().id(1L).stdNo("stdNo1").stdVer("stdVer1").enName("enName1").chName("chName1").status("status1");
    }

    public static Std getStdSample2() {
        return new Std().id(2L).stdNo("stdNo2").stdVer("stdVer2").enName("enName2").chName("chName2").status("status2");
    }

    public static Std getStdRandomSampleGenerator() {
        return new Std()
            .id(longCount.incrementAndGet())
            .stdNo(UUID.randomUUID().toString())
            .stdVer(UUID.randomUUID().toString())
            .enName(UUID.randomUUID().toString())
            .chName(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
