package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProdStickerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProdSticker getProdStickerSample1() {
        return new ProdSticker().id(1L).relType("relType1");
    }

    public static ProdSticker getProdStickerSample2() {
        return new ProdSticker().id(2L).relType("relType2");
    }

    public static ProdSticker getProdStickerRandomSampleGenerator() {
        return new ProdSticker().id(longCount.incrementAndGet()).relType(UUID.randomUUID().toString());
    }
}
