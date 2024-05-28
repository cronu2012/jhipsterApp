package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class Wcc421ViewCriteriaTest {

    @Test
    void newWcc421ViewCriteriaHasAllFiltersNullTest() {
        var wcc421ViewCriteria = new Wcc421ViewCriteria();
        assertThat(wcc421ViewCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void wcc421ViewCriteriaFluentMethodsCreatesFiltersTest() {
        var wcc421ViewCriteria = new Wcc421ViewCriteria();

        setAllFilters(wcc421ViewCriteria);

        assertThat(wcc421ViewCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void wcc421ViewCriteriaCopyCreatesNullFilterTest() {
        var wcc421ViewCriteria = new Wcc421ViewCriteria();
        var copy = wcc421ViewCriteria.copy();

        assertThat(wcc421ViewCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(wcc421ViewCriteria)
        );
    }

    @Test
    void wcc421ViewCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var wcc421ViewCriteria = new Wcc421ViewCriteria();
        setAllFilters(wcc421ViewCriteria);

        var copy = wcc421ViewCriteria.copy();

        assertThat(wcc421ViewCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(wcc421ViewCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var wcc421ViewCriteria = new Wcc421ViewCriteria();

        assertThat(wcc421ViewCriteria).hasToString("Wcc421ViewCriteria{}");
    }

    private static void setAllFilters(Wcc421ViewCriteria wcc421ViewCriteria) {
        wcc421ViewCriteria.id();
        wcc421ViewCriteria.cerfId();
        wcc421ViewCriteria.countryChName();
        wcc421ViewCriteria.cerfNo();
        wcc421ViewCriteria.cerfVer();
        wcc421ViewCriteria.cerfStatus();
        wcc421ViewCriteria.companyChName();
        wcc421ViewCriteria.relType();
        wcc421ViewCriteria.distinct();
    }

    private static Condition<Wcc421ViewCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getCountryChName()) &&
                condition.apply(criteria.getCerfNo()) &&
                condition.apply(criteria.getCerfVer()) &&
                condition.apply(criteria.getCerfStatus()) &&
                condition.apply(criteria.getCompanyChName()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<Wcc421ViewCriteria> copyFiltersAre(Wcc421ViewCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getCountryChName(), copy.getCountryChName()) &&
                condition.apply(criteria.getCerfNo(), copy.getCerfNo()) &&
                condition.apply(criteria.getCerfVer(), copy.getCerfVer()) &&
                condition.apply(criteria.getCerfStatus(), copy.getCerfStatus()) &&
                condition.apply(criteria.getCompanyChName(), copy.getCompanyChName()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
