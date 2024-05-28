package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProdCountryCriteriaTest {

    @Test
    void newProdCountryCriteriaHasAllFiltersNullTest() {
        var prodCountryCriteria = new ProdCountryCriteria();
        assertThat(prodCountryCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void prodCountryCriteriaFluentMethodsCreatesFiltersTest() {
        var prodCountryCriteria = new ProdCountryCriteria();

        setAllFilters(prodCountryCriteria);

        assertThat(prodCountryCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void prodCountryCriteriaCopyCreatesNullFilterTest() {
        var prodCountryCriteria = new ProdCountryCriteria();
        var copy = prodCountryCriteria.copy();

        assertThat(prodCountryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(prodCountryCriteria)
        );
    }

    @Test
    void prodCountryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var prodCountryCriteria = new ProdCountryCriteria();
        setAllFilters(prodCountryCriteria);

        var copy = prodCountryCriteria.copy();

        assertThat(prodCountryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(prodCountryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var prodCountryCriteria = new ProdCountryCriteria();

        assertThat(prodCountryCriteria).hasToString("ProdCountryCriteria{}");
    }

    private static void setAllFilters(ProdCountryCriteria prodCountryCriteria) {
        prodCountryCriteria.id();
        prodCountryCriteria.relType();
        prodCountryCriteria.prodId();
        prodCountryCriteria.countryId();
        prodCountryCriteria.distinct();
    }

    private static Condition<ProdCountryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getProdId()) &&
                condition.apply(criteria.getCountryId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProdCountryCriteria> copyFiltersAre(ProdCountryCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getProdId(), copy.getProdId()) &&
                condition.apply(criteria.getCountryId(), copy.getCountryId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
