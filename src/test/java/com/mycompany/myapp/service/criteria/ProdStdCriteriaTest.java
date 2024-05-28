package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProdStdCriteriaTest {

    @Test
    void newProdStdCriteriaHasAllFiltersNullTest() {
        var prodStdCriteria = new ProdStdCriteria();
        assertThat(prodStdCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void prodStdCriteriaFluentMethodsCreatesFiltersTest() {
        var prodStdCriteria = new ProdStdCriteria();

        setAllFilters(prodStdCriteria);

        assertThat(prodStdCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void prodStdCriteriaCopyCreatesNullFilterTest() {
        var prodStdCriteria = new ProdStdCriteria();
        var copy = prodStdCriteria.copy();

        assertThat(prodStdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(prodStdCriteria)
        );
    }

    @Test
    void prodStdCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var prodStdCriteria = new ProdStdCriteria();
        setAllFilters(prodStdCriteria);

        var copy = prodStdCriteria.copy();

        assertThat(prodStdCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(prodStdCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var prodStdCriteria = new ProdStdCriteria();

        assertThat(prodStdCriteria).hasToString("ProdStdCriteria{}");
    }

    private static void setAllFilters(ProdStdCriteria prodStdCriteria) {
        prodStdCriteria.id();
        prodStdCriteria.relType();
        prodStdCriteria.prodId();
        prodStdCriteria.stdId();
        prodStdCriteria.distinct();
    }

    private static Condition<ProdStdCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getProdId()) &&
                condition.apply(criteria.getStdId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProdStdCriteria> copyFiltersAre(ProdStdCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getProdId(), copy.getProdId()) &&
                condition.apply(criteria.getStdId(), copy.getStdId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
