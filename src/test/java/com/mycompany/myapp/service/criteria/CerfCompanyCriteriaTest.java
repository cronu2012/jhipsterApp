package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CerfCompanyCriteriaTest {

    @Test
    void newCerfCompanyCriteriaHasAllFiltersNullTest() {
        var cerfCompanyCriteria = new CerfCompanyCriteria();
        assertThat(cerfCompanyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void cerfCompanyCriteriaFluentMethodsCreatesFiltersTest() {
        var cerfCompanyCriteria = new CerfCompanyCriteria();

        setAllFilters(cerfCompanyCriteria);

        assertThat(cerfCompanyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void cerfCompanyCriteriaCopyCreatesNullFilterTest() {
        var cerfCompanyCriteria = new CerfCompanyCriteria();
        var copy = cerfCompanyCriteria.copy();

        assertThat(cerfCompanyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(cerfCompanyCriteria)
        );
    }

    @Test
    void cerfCompanyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cerfCompanyCriteria = new CerfCompanyCriteria();
        setAllFilters(cerfCompanyCriteria);

        var copy = cerfCompanyCriteria.copy();

        assertThat(cerfCompanyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(cerfCompanyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cerfCompanyCriteria = new CerfCompanyCriteria();

        assertThat(cerfCompanyCriteria).hasToString("CerfCompanyCriteria{}");
    }

    private static void setAllFilters(CerfCompanyCriteria cerfCompanyCriteria) {
        cerfCompanyCriteria.id();
        cerfCompanyCriteria.relType();
        cerfCompanyCriteria.cerfId();
        cerfCompanyCriteria.companyId();
        cerfCompanyCriteria.distinct();
    }

    private static Condition<CerfCompanyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRelType()) &&
                condition.apply(criteria.getCerfId()) &&
                condition.apply(criteria.getCompanyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CerfCompanyCriteria> copyFiltersAre(CerfCompanyCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRelType(), copy.getRelType()) &&
                condition.apply(criteria.getCerfId(), copy.getCerfId()) &&
                condition.apply(criteria.getCompanyId(), copy.getCompanyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
