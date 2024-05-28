package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StickerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sticker getStickerSample1() {
        return new Sticker().id(1L).stickerNo("stickerNo1");
    }

    public static Sticker getStickerSample2() {
        return new Sticker().id(2L).stickerNo("stickerNo2");
    }

    public static Sticker getStickerRandomSampleGenerator() {
        return new Sticker().id(longCount.incrementAndGet()).stickerNo(UUID.randomUUID().toString());
    }
}
