package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CerfStdCriteriaTest {

    @Test
    void newCerfStdCriteriaHasAllFiltersNullTest() {
        var cerfStdCriteria = new CerfStdCriteria();
        assertThat(cerfStdCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void cerfStdCriteriaFluentMethodsCreatesFiltersTest() {
        var cerfStdCriteria = new CerfStdCriteria();

        setAllFilters(cerfStdCriteria);

        assertThat(cerfStdCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void cerfStdCriteriaCopyCreatesNullFilterTest() {
        var cerfStdCriteria = new CerfStdCriteria();
        var copy = cerfStdCriteria.copy();

        assertThat(cerfStdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(cerfStdCriteria)
        );
    }

    @Test
    void cerfStdCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cerfStdCriteria = new CerfStdCriteria();
        setAllFilters(cerfStdCriteria);

        var copy = cerfStdCriteria.copy();

        assertThat(cerfStdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(cerfStdCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cerfStdCriteria = new CerfStdCriteria();

        assertThat(cerfStdCriteria).hasToString("CerfStdCriteria{}");
    }

    private static void setAllFilters(CerfStdCriteria cerfStdCriteria) {
        cerfStdCriteria.id();
        cerfStdCriteria.relType();
        cerfStdCriteria.cerfId();
        cerfStdCriteria.stdId();
        cerfStdCriteria.distinct();
    }

    private static Condition<CerfStdCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getStdId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CerfStdCriteria> copyFiltersAre(CerfStdCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getStdId(), copy.getStdId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
