package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CountryStdCriteriaTest {

    @Test
    void newCountryStdCriteriaHasAllFiltersNullTest() {
        var countryStdCriteria = new CountryStdCriteria();
        assertThat(countryStdCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void countryStdCriteriaFluentMethodsCreatesFiltersTest() {
        var countryStdCriteria = new CountryStdCriteria();

        setAllFilters(countryStdCriteria);

        assertThat(countryStdCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void countryStdCriteriaCopyCreatesNullFilterTest() {
        var countryStdCriteria = new CountryStdCriteria();
        var copy = countryStdCriteria.copy();

        assertThat(countryStdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(countryStdCriteria)
        );
    }

    @Test
    void countryStdCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var countryStdCriteria = new CountryStdCriteria();
        setAllFilters(countryStdCriteria);

        var copy = countryStdCriteria.copy();

        assertThat(countryStdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(countryStdCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var countryStdCriteria = new CountryStdCriteria();

        assertThat(countryStdCriteria).hasToString("CountryStdCriteria{}");
    }

    private static void setAllFilters(CountryStdCriteria countryStdCriteria) {
        countryStdCriteria.id();
        countryStdCriteria.relType();
        countryStdCriteria.countryId();
        countryStdCriteria.stdId();
        countryStdCriteria.distinct();
    }

    private static Condition<CountryStdCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getCountryId()) &&
                condition.apply(criteria.getStdId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CountryStdCriteria> copyFiltersAre(CountryStdCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getCountryId(), copy.getCountryId()) &&
                condition.apply(criteria.getStdId(), copy.getStdId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
