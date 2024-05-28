package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CompanyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Company getCompanySample1() {
        return new Company()
            .id(1L)
            .companyNo("companyNo1")
            .enName("enName1")
            .chName("chName1")
            .tel("tel1")
            .addr("addr1")
            .email("email1")
            .peopleName("peopleName1");
    }

    public static Company getCompanySample2() {
        return new Company()
            .id(2L)
            .companyNo("companyNo2")
            .enName("enName2")
            .chName("chName2")
            .tel("tel2")
            .addr("addr2")
            .email("email2")
            .peopleName("peopleName2");
    }

    public static Company getCompanyRandomSampleGenerator() {
        return new Company()
            .id(longCount.incrementAndGet())
            .companyNo(UUID.randomUUID().toString())
            .enName(UUID.randomUUID().toString())
            .chName(UUID.randomUUID().toString())
            .tel(UUID.randomUUID().toString())
            .addr(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .peopleName(UUID.randomUUID().toString());
    }
}
