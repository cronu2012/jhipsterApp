package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StickerMarkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StickerMark getStickerMarkSample1() {
        return new StickerMark().id(1L).relType("relType1");
    }

    public static StickerMark getStickerMarkSample2() {
        return new StickerMark().id(2L).relType("relType2");
    }

    public static StickerMark getStickerMarkRandomSampleGenerator() {
        return new StickerMark().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
