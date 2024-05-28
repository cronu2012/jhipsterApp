package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProdCriteriaTest {

    @Test
    void newProdCriteriaHasAllFiltersNullTest() {
        var prodCriteria = new ProdCriteria();
        assertThat(prodCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void prodCriteriaFluentMethodsCreatesFiltersTest() {
        var prodCriteria = new ProdCriteria();

        setAllFilters(prodCriteria);

        assertThat(prodCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void prodCriteriaCopyCreatesNullFilterTest() {
        var prodCriteria = new ProdCriteria();
        var copy = prodCriteria.copy();

        assertThat(prodCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(prodCriteria)
        );
    }

    @Test
    void prodCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var prodCriteria = new ProdCriteria();
        setAllFilters(prodCriteria);

        var copy = prodCriteria.copy();

        assertThat(prodCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(prodCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var prodCriteria = new ProdCriteria();

        assertThat(prodCriteria).hasToString("ProdCriteria{}");
    }

    private static void setAllFilters(ProdCriteria prodCriteria) {
        prodCriteria.id();
        prodCriteria.prodNo();
        prodCriteria.enName();
        prodCriteria.chName();
        prodCriteria.hsCode();
        prodCriteria.cccCode();
        prodCriteria.prodCountryId();
        prodCriteria.prodStdId();
        prodCriteria.cerfProdId();
        prodCriteria.feeProdCerfCompanyId();
        prodCriteria.prodStickerId();
        prodCriteria.distinct();
    }

    private static Condition<ProdCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getProdNo()) &&
                condition.apply(criteria.getEnName()) &&
                condition.apply(criteria.getChName()) &&
                condition.apply(criteria.getHsCode()) &&
                condition.apply(criteria.getCccCode()) &&
                condition.apply(criteria.getProdCountryId()) &&
                condition.apply(criteria.getProdStdId()) &&
                condition.apply(criteria.getCerfProdId()) &&
                condition.apply(criteria.getFeeProdCerfCompanyId()) &&
                condition.apply(criteria.getProdStickerId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProdCriteria> copyFiltersAre(ProdCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getProdNo(), copy.getProdNo()) &&
                condition.apply(criteria.getEnName(), copy.getEnName()) &&
                condition.apply(criteria.getChName(), copy.getChName()) &&
                condition.apply(criteria.getHsCode(), copy.getHsCode()) &&
                condition.apply(criteria.getCccCode(), copy.getCccCode()) &&
                condition.apply(criteria.getProdCountryId(), copy.getProdCountryId()) &&
                condition.apply(criteria.getProdStdId(), copy.getProdStdId()) &&
                condition.apply(criteria.getCerfProdId(), copy.getCerfProdId()) &&
                condition.apply(criteria.getFeeProdCerfCompanyId(), copy.getFeeProdCerfCompanyId()) &&
                condition.apply(criteria.getProdStickerId(), copy.getProdStickerId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
