package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProdStickerCriteriaTest {

    @Test
    void newProdStickerCriteriaHasAllFiltersNullTest() {
        var prodStickerCriteria = new ProdStickerCriteria();
        assertThat(prodStickerCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void prodStickerCriteriaFluentMethodsCreatesFiltersTest() {
        var prodStickerCriteria = new ProdStickerCriteria();

        setAllFilters(prodStickerCriteria);

        assertThat(prodStickerCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void prodStickerCriteriaCopyCreatesNullFilterTest() {
        var prodStickerCriteria = new ProdStickerCriteria();
        var copy = prodStickerCriteria.copy();

        assertThat(prodStickerCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(prodStickerCriteria)
        );
    }

    @Test
    void prodStickerCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var prodStickerCriteria = new ProdStickerCriteria();
        setAllFilters(prodStickerCriteria);

        var copy = prodStickerCriteria.copy();

        assertThat(prodStickerCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(prodStickerCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var prodStickerCriteria = new ProdStickerCriteria();

        assertThat(prodStickerCriteria).hasToString("ProdStickerCriteria{}");
    }

    private static void setAllFilters(ProdStickerCriteria prodStickerCriteria) {
        prodStickerCriteria.id();
        prodStickerCriteria.relType();
        prodStickerCriteria.prodId();
        prodStickerCriteria.stickerId();
        prodStickerCriteria.distinct();
    }

    private static Condition<ProdStickerCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getProdId()) &&
                condition.apply(criteria.getStickerId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProdStickerCriteria> copyFiltersAre(ProdStickerCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getProdId(), copy.getProdId()) &&
                condition.apply(criteria.getStickerId(), copy.getStickerId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
