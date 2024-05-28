package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FeeProdCerfCompanyCriteriaTest {

    @Test
    void newFeeProdCerfCompanyCriteriaHasAllFiltersNullTest() {
        var feeProdCerfCompanyCriteria = new FeeProdCerfCompanyCriteria();
        assertThat(feeProdCerfCompanyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void feeProdCerfCompanyCriteriaFluentMethodsCreatesFiltersTest() {
        var feeProdCerfCompanyCriteria = new FeeProdCerfCompanyCriteria();

        setAllFilters(feeProdCerfCompanyCriteria);

        assertThat(feeProdCerfCompanyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void feeProdCerfCompanyCriteriaCopyCreatesNullFilterTest() {
        var feeProdCerfCompanyCriteria = new FeeProdCerfCompanyCriteria();
        var copy = feeProdCerfCompanyCriteria.copy();

        assertThat(feeProdCerfCompanyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(feeProdCerfCompanyCriteria)
        );
    }

    @Test
    void feeProdCerfCompanyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var feeProdCerfCompanyCriteria = new FeeProdCerfCompanyCriteria();
        setAllFilters(feeProdCerfCompanyCriteria);

        var copy = feeProdCerfCompanyCriteria.copy();

        assertThat(feeProdCerfCompanyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(feeProdCerfCompanyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var feeProdCerfCompanyCriteria = new FeeProdCerfCompanyCriteria();

        assertThat(feeProdCerfCompanyCriteria).hasToString("FeeProdCerfCompanyCriteria{}");
    }

    private static void setAllFilters(FeeProdCerfCompanyCriteria feeProdCerfCompanyCriteria) {
        feeProdCerfCompanyCriteria.id();
        feeProdCerfCompanyCriteria.fee();
        feeProdCerfCompanyCriteria.feeType();
        feeProdCerfCompanyCriteria.feeDt();
        feeProdCerfCompanyCriteria.prodId();
        feeProdCerfCompanyCriteria.cerfId();
        feeProdCerfCompanyCriteria.companyId();
        feeProdCerfCompanyCriteria.distinct();
    }

    private static Condition<FeeProdCerfCompanyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFee()) &&
                condition.apply(criteria.getFeeType()) &&
                condition.apply(criteria.getFeeDt()) &&
                condition.apply(criteria.getProdId()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getCompanyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FeeProdCerfCompanyCriteria> copyFiltersAre(
        FeeProdCerfCompanyCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFee(), copy.getFee()) &&
                condition.apply(criteria.getFeeType(), copy.getFeeType()) &&
                condition.apply(criteria.getFeeDt(), copy.getFeeDt()) &&
                condition.apply(criteria.getProdId(), copy.getProdId()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getCompanyId(), copy.getCompanyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
