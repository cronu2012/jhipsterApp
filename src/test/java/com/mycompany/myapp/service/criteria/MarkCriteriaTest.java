package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MarkCriteriaTest {

    @Test
    void newMarkCriteriaHasAllFiltersNullTest() {
        var markCriteria = new MarkCriteria();
        assertThat(markCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void markCriteriaFluentMethodsCreatesFiltersTest() {
        var markCriteria = new MarkCriteria();

        setAllFilters(markCriteria);

        assertThat(markCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void markCriteriaCopyCreatesNullFilterTest() {
        var markCriteria = new MarkCriteria();
        var copy = markCriteria.copy();

        assertThat(markCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(markCriteria)
        );
    }

    @Test
    void markCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var markCriteria = new MarkCriteria();
        setAllFilters(markCriteria);

        var copy = markCriteria.copy();

        assertThat(markCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(markCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var markCriteria = new MarkCriteria();

        assertThat(markCriteria).hasToString("MarkCriteria{}");
    }

    private static void setAllFilters(MarkCriteria markCriteria) {
        markCriteria.id();
        markCriteria.markNo();
        markCriteria.enName();
        markCriteria.chName();
        markCriteria.cerfMarkId();
        markCriteria.stickerMarkId();
        markCriteria.countryMarkId();
        markCriteria.distinct();
    }

    private static Condition<MarkCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMarkNo()) &&
                condition.apply(criteria.getEnName()) &&
                condition.apply(criteria.getChName()) &&
                condition.apply(criteria.getCerfMarkId()) &&
                condition.apply(criteria.getStickerMarkId()) &&
                condition.apply(criteria.getCountryMarkId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MarkCriteria> copyFiltersAre(MarkCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMarkNo(), copy.getMarkNo()) &&
                condition.apply(criteria.getEnName(), copy.getEnName()) &&
                condition.apply(criteria.getChName(), copy.getChName()) &&
                condition.apply(criteria.getCerfMarkId(), copy.getCerfMarkId()) &&
                condition.apply(criteria.getStickerMarkId(), copy.getStickerMarkId()) &&
                condition.apply(criteria.getCountryMarkId(), copy.getCountryMarkId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
