package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StickerCriteriaTest {

    @Test
    void newStickerCriteriaHasAllFiltersNullTest() {
        var stickerCriteria = new StickerCriteria();
        assertThat(stickerCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void stickerCriteriaFluentMethodsCreatesFiltersTest() {
        var stickerCriteria = new StickerCriteria();

        setAllFilters(stickerCriteria);

        assertThat(stickerCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void stickerCriteriaCopyCreatesNullFilterTest() {
        var stickerCriteria = new StickerCriteria();
        var copy = stickerCriteria.copy();

        assertThat(stickerCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(stickerCriteria)
        );
    }

    @Test
    void stickerCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var stickerCriteria = new StickerCriteria();
        setAllFilters(stickerCriteria);

        var copy = stickerCriteria.copy();

        assertThat(stickerCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(stickerCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var stickerCriteria = new StickerCriteria();

        assertThat(stickerCriteria).hasToString("StickerCriteria{}");
    }

    private static void setAllFilters(StickerCriteria stickerCriteria) {
        stickerCriteria.id();
        stickerCriteria.stickerNo();
        stickerCriteria.stickerMarkId();
        stickerCriteria.prodStickerId();
        stickerCriteria.distinct();
    }

    private static Condition<StickerCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStickerNo()) &&
                condition.apply(criteria.getStickerMarkId()) &&
                condition.apply(criteria.getProdStickerId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StickerCriteria> copyFiltersAre(StickerCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStickerNo(), copy.getStickerNo()) &&
                condition.apply(criteria.getStickerMarkId(), copy.getStickerMarkId()) &&
                condition.apply(criteria.getProdStickerId(), copy.getProdStickerId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
