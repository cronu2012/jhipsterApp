package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StickerMarkCriteriaTest {

    @Test
    void newStickerMarkCriteriaHasAllFiltersNullTest() {
        var stickerMarkCriteria = new StickerMarkCriteria();
        assertThat(stickerMarkCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void stickerMarkCriteriaFluentMethodsCreatesFiltersTest() {
        var stickerMarkCriteria = new StickerMarkCriteria();

        setAllFilters(stickerMarkCriteria);

        assertThat(stickerMarkCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void stickerMarkCriteriaCopyCreatesNullFilterTest() {
        var stickerMarkCriteria = new StickerMarkCriteria();
        var copy = stickerMarkCriteria.copy();

        assertThat(stickerMarkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(stickerMarkCriteria)
        );
    }

    @Test
    void stickerMarkCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var stickerMarkCriteria = new StickerMarkCriteria();
        setAllFilters(stickerMarkCriteria);

        var copy = stickerMarkCriteria.copy();

        assertThat(stickerMarkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(stickerMarkCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var stickerMarkCriteria = new StickerMarkCriteria();

        assertThat(stickerMarkCriteria).hasToString("StickerMarkCriteria{}");
    }

    private static void setAllFilters(StickerMarkCriteria stickerMarkCriteria) {
        stickerMarkCriteria.id();
        stickerMarkCriteria.relType();
        stickerMarkCriteria.stickerId();
        stickerMarkCriteria.markId();
        stickerMarkCriteria.distinct();
    }

    private static Condition<StickerMarkCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getStickerId()) &&
                condition.apply(criteria.getMarkId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StickerMarkCriteria> copyFiltersAre(StickerMarkCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getStickerId(), copy.getStickerId()) &&
                condition.apply(criteria.getMarkId(), copy.getMarkId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
