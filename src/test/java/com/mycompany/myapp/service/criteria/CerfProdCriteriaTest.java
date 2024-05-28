package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CerfProdCriteriaTest {

    @Test
    void newCerfProdCriteriaHasAllFiltersNullTest() {
        var cerfProdCriteria = new CerfProdCriteria();
        assertThat(cerfProdCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void cerfProdCriteriaFluentMethodsCreatesFiltersTest() {
        var cerfProdCriteria = new CerfProdCriteria();

        setAllFilters(cerfProdCriteria);

        assertThat(cerfProdCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void cerfProdCriteriaCopyCreatesNullFilterTest() {
        var cerfProdCriteria = new CerfProdCriteria();
        var copy = cerfProdCriteria.copy();

        assertThat(cerfProdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(cerfProdCriteria)
        );
    }

    @Test
    void cerfProdCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cerfProdCriteria = new CerfProdCriteria();
        setAllFilters(cerfProdCriteria);

        var copy = cerfProdCriteria.copy();

        assertThat(cerfProdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(cerfProdCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cerfProdCriteria = new CerfProdCriteria();

        assertThat(cerfProdCriteria).hasToString("CerfProdCriteria{}");
    }

    private static void setAllFilters(CerfProdCriteria cerfProdCriteria) {
        cerfProdCriteria.id();
        cerfProdCriteria.relType();
        cerfProdCriteria.cerfId();
        cerfProdCriteria.prodId();
        cerfProdCriteria.distinct();
    }

    private static Condition<CerfProdCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getProdId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CerfProdCriteria> copyFiltersAre(CerfProdCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getProdId(), copy.getProdId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
