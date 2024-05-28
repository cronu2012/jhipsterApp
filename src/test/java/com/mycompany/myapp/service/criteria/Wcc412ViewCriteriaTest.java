package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class Wcc412ViewCriteriaTest {

    @Test
    void newWcc412ViewCriteriaHasAllFiltersNullTest() {
        var wcc412ViewCriteria = new Wcc412ViewCriteria();
        assertThat(wcc412ViewCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void wcc412ViewCriteriaFluentMethodsCreatesFiltersTest() {
        var wcc412ViewCriteria = new Wcc412ViewCriteria();

        setAllFilters(wcc412ViewCriteria);

        assertThat(wcc412ViewCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void wcc412ViewCriteriaCopyCreatesNullFilterTest() {
        var wcc412ViewCriteria = new Wcc412ViewCriteria();
        var copy = wcc412ViewCriteria.copy();

        assertThat(wcc412ViewCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(wcc412ViewCriteria)
        );
    }

    @Test
    void wcc412ViewCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var wcc412ViewCriteria = new Wcc412ViewCriteria();
        setAllFilters(wcc412ViewCriteria);

        var copy = wcc412ViewCriteria.copy();

        assertThat(wcc412ViewCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(wcc412ViewCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var wcc412ViewCriteria = new Wcc412ViewCriteria();

        assertThat(wcc412ViewCriteria).hasToString("Wcc412ViewCriteria{}");
    }

    private static void setAllFilters(Wcc412ViewCriteria wcc412ViewCriteria) {
        wcc412ViewCriteria.id();
        wcc412ViewCriteria.cerfId();
        wcc412ViewCriteria.countryChName();
        wcc412ViewCriteria.cerfNo();
        wcc412ViewCriteria.cerfVer();
        wcc412ViewCriteria.cerfStatus();
        wcc412ViewCriteria.countryId();
        wcc412ViewCriteria.prodNo();
        wcc412ViewCriteria.prodChName();
        wcc412ViewCriteria.distinct();
    }

    private static Condition<Wcc412ViewCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getCountryChName()) &&
                condition.apply(criteria.getCerfNo()) &&
                condition.apply(criteria.getCerfVer()) &&
                condition.apply(criteria.getCerfStatus()) &&
                condition.apply(criteria.getCountryId()) &&
                condition.apply(criteria.getProdNo()) &&
                condition.apply(criteria.getProdChName()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<Wcc412ViewCriteria> copyFiltersAre(Wcc412ViewCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getCountryChName(), copy.getCountryChName()) &&
                condition.apply(criteria.getCerfNo(), copy.getCerfNo()) &&
                condition.apply(criteria.getCerfVer(), copy.getCerfVer()) &&
                condition.apply(criteria.getCerfStatus(), copy.getCerfStatus()) &&
                condition.apply(criteria.getCountryId(), copy.getCountryId()) &&
                condition.apply(criteria.getProdNo(), copy.getProdNo()) &&
                condition.apply(criteria.getProdChName(), copy.getProdChName()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
