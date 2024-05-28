package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Wcc412ViewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wcc412View getWcc412ViewSample1() {
        return new Wcc412View()
            .id(1L)
            .cerfId(1L)
            .countryChName("countryChName1")
            .cerfNo("cerfNo1")
            .cerfVer("cerfVer1")
            .cerfStatus("cerfStatus1")
            .countryId(1L)
            .prodNo("prodNo1")
            .prodChName("prodChName1");
    }

    public static Wcc412View getWcc412ViewSample2() {
        return new Wcc412View()
            .id(2L)
            .cerfId(2L)
            .countryChName("countryChName2")
            .cerfNo("cerfNo2")
            .cerfVer("cerfVer2")
            .cerfStatus("cerfStatus2")
            .countryId(2L)
            .prodNo("prodNo2")
            .prodChName("prodChName2");
    }

    public static Wcc412View getWcc412ViewRandomSampleGenerator() {
        return new Wcc412View()
            .id(longCount.incrementAndGet())
            .cerfId(longCount.incrementAndGet())
            .countryChName(UUID.randomUUID().toString())
            .cerfNo(UUID.randomUUID().toString())
            .cerfVer(UUID.randomUUID().toString())
            .cerfStatus(UUID.randomUUID().toString())
            .countryId(longCount.incrementAndGet())
            .prodNo(UUID.randomUUID().toString())
            .prodChName(UUID.randomUUID().toString());
    }
}
