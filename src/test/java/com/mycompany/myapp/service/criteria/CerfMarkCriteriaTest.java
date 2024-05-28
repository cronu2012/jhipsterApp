package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CerfMarkCriteriaTest {

    @Test
    void newCerfMarkCriteriaHasAllFiltersNullTest() {
        var cerfMarkCriteria = new CerfMarkCriteria();
        assertThat(cerfMarkCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void cerfMarkCriteriaFluentMethodsCreatesFiltersTest() {
        var cerfMarkCriteria = new CerfMarkCriteria();

        setAllFilters(cerfMarkCriteria);

        assertThat(cerfMarkCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void cerfMarkCriteriaCopyCreatesNullFilterTest() {
        var cerfMarkCriteria = new CerfMarkCriteria();
        var copy = cerfMarkCriteria.copy();

        assertThat(cerfMarkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(cerfMarkCriteria)
        );
    }

    @Test
    void cerfMarkCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cerfMarkCriteria = new CerfMarkCriteria();
        setAllFilters(cerfMarkCriteria);

        var copy = cerfMarkCriteria.copy();

        assertThat(cerfMarkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(cerfMarkCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cerfMarkCriteria = new CerfMarkCriteria();

        assertThat(cerfMarkCriteria).hasToString("CerfMarkCriteria{}");
    }

    private static void setAllFilters(CerfMarkCriteria cerfMarkCriteria) {
        cerfMarkCriteria.id();
        cerfMarkCriteria.relType();
        cerfMarkCriteria.cerfId();
        cerfMarkCriteria.markId();
        cerfMarkCriteria.distinct();
    }

    private static Condition<CerfMarkCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getMarkId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CerfMarkCriteria> copyFiltersAre(CerfMarkCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getMarkId(), copy.getMarkId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
