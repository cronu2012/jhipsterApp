package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id(1L).mainTitle("mainTitle1").subTitle("subTitle1").price("price1").createBy("createBy1").status(1);
    }

    public static Product getProductSample2() {
        return new Product().id(2L).mainTitle("mainTitle2").subTitle("subTitle2").price("price2").createBy("createBy2").status(2);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .mainTitle(UUID.randomUUID().toString())
            .subTitle(UUID.randomUUID().toString())
            .price(UUID.randomUUID().toString())
            .createBy(UUID.randomUUID().toString())
            .status(intCount.incrementAndGet());
    }
}
