package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MarkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mark getMarkSample1() {
        return new Mark().id(1L).markNo("markNo1").enName("enName1").chName("chName1");
    }

    public static Mark getMarkSample2() {
        return new Mark().id(2L).markNo("markNo2").enName("enName2").chName("chName2");
    }

    public static Mark getMarkRandomSampleGenerator() {
        return new Mark()
            .id(longCount.incrementAndGet())
            .markNo(UUID.randomUUID().toString())
            .enName(UUID.randomUUID().toString())
            .chName(UUID.randomUUID().toString());
    }
}
