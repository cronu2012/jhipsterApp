package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StdCriteriaTest {

    @Test
    void newStdCriteriaHasAllFiltersNullTest() {
        var stdCriteria = new StdCriteria();
        assertThat(stdCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void stdCriteriaFluentMethodsCreatesFiltersTest() {
        var stdCriteria = new StdCriteria();

        setAllFilters(stdCriteria);

        assertThat(stdCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void stdCriteriaCopyCreatesNullFilterTest() {
        var stdCriteria = new StdCriteria();
        var copy = stdCriteria.copy();

        assertThat(stdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(stdCriteria)
        );
    }

    @Test
    void stdCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var stdCriteria = new StdCriteria();
        setAllFilters(stdCriteria);

        var copy = stdCriteria.copy();

        assertThat(stdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(stdCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var stdCriteria = new StdCriteria();

        assertThat(stdCriteria).hasToString("StdCriteria{}");
    }

    private static void setAllFilters(StdCriteria stdCriteria) {
        stdCriteria.id();
        stdCriteria.stdNo();
        stdCriteria.stdVer();
        stdCriteria.enName();
        stdCriteria.chName();
        stdCriteria.status();
        stdCriteria.issuDt();
        stdCriteria.expDt();
        stdCriteria.prodStdId();
        stdCriteria.cerfStdId();
        stdCriteria.countryStdId();
        stdCriteria.distinct();
    }

    private static Condition<StdCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStdNo()) &&
                condition.apply(criteria.getStdVer()) &&
                condition.apply(criteria.getEnName()) &&
                condition.apply(criteria.getChName()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getIssuDt()) &&
                condition.apply(criteria.getExpDt()) &&
                condition.apply(criteria.getProdStdId()) &&
                condition.apply(criteria.getCerfStdId()) &&
                condition.apply(criteria.getCountryStdId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StdCriteria> copyFiltersAre(StdCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStdNo(), copy.getStdNo()) &&
                condition.apply(criteria.getStdVer(), copy.getStdVer()) &&
                condition.apply(criteria.getEnName(), copy.getEnName()) &&
                condition.apply(criteria.getChName(), copy.getChName()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getIssuDt(), copy.getIssuDt()) &&
                condition.apply(criteria.getExpDt(), copy.getExpDt()) &&
                condition.apply(criteria.getProdStdId(), copy.getProdStdId()) &&
                condition.apply(criteria.getCerfStdId(), copy.getCerfStdId()) &&
                condition.apply(criteria.getCountryStdId(), copy.getCountryStdId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
