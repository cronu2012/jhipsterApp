package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Wcc421ViewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wcc421View getWcc421ViewSample1() {
        return new Wcc421View()
            .id(1L)
            .cerfId(1L)
            .countryChName("countryChName1")
            .cerfNo("cerfNo1")
            .cerfVer("cerfVer1")
            .cerfStatus("cerfStatus1")
            .companyChName("companyChName1")
            .relType("relType1");
    }

    public static Wcc421View getWcc421ViewSample2() {
        return new Wcc421View()
            .id(2L)
            .cerfId(2L)
            .countryChName("countryChName2")
            .cerfNo("cerfNo2")
            .cerfVer("cerfVer2")
            .cerfStatus("cerfStatus2")
            .companyChName("companyChName2")
            .relType("relType2");
    }

    public static Wcc421View getWcc421ViewRandomSampleGenerator() {
        return new Wcc421View()
            .id(longCount.incrementAndGet())
            .cerfId(longCount.incrementAndGet())
            .countryChName(UUID.randomUUID().toString())
            .cerfNo(UUID.randomUUID().toString())
            .cerfVer(UUID.randomUUID().toString())
            .cerfStatus(UUID.randomUUID().toString())
            .companyChName(UUID.randomUUID().toString())
            .relType(UUID.randomUUID().toString());
    }
}
