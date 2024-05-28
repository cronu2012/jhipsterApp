package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CountryCertCriteriaTest {

    @Test
    void newCountryCertCriteriaHasAllFiltersNullTest() {
        var countryCertCriteria = new CountryCertCriteria();
        assertThat(countryCertCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void countryCertCriteriaFluentMethodsCreatesFiltersTest() {
        var countryCertCriteria = new CountryCertCriteria();

        setAllFilters(countryCertCriteria);

        assertThat(countryCertCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void countryCertCriteriaCopyCreatesNullFilterTest() {
        var countryCertCriteria = new CountryCertCriteria();
        var copy = countryCertCriteria.copy();

        assertThat(countryCertCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(countryCertCriteria)
        );
    }

    @Test
    void countryCertCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var countryCertCriteria = new CountryCertCriteria();
        setAllFilters(countryCertCriteria);

        var copy = countryCertCriteria.copy();

        assertThat(countryCertCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(countryCertCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var countryCertCriteria = new CountryCertCriteria();

        assertThat(countryCertCriteria).hasToString("CountryCertCriteria{}");
    }

    private static void setAllFilters(CountryCertCriteria countryCertCriteria) {
        countryCertCriteria.id();
        countryCertCriteria.relType();
        countryCertCriteria.countryId();
        countryCertCriteria.cerfId();
        countryCertCriteria.distinct();
    }

    private static Condition<CountryCertCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getCountryId()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CountryCertCriteria> copyFiltersAre(CountryCertCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getCountryId(), copy.getCountryId()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
