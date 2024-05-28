package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CerfCriteriaTest {

    @Test
    void newCerfCriteriaHasAllFiltersNullTest() {
        var cerfCriteria = new CerfCriteria();
        assertThat(cerfCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void cerfCriteriaFluentMethodsCreatesFiltersTest() {
        var cerfCriteria = new CerfCriteria();

        setAllFilters(cerfCriteria);

        assertThat(cerfCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void cerfCriteriaCopyCreatesNullFilterTest() {
        var cerfCriteria = new CerfCriteria();
        var copy = cerfCriteria.copy();

        assertThat(cerfCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(cerfCriteria)
        );
    }

    @Test
    void cerfCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cerfCriteria = new CerfCriteria();
        setAllFilters(cerfCriteria);

        var copy = cerfCriteria.copy();

        assertThat(cerfCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(cerfCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cerfCriteria = new CerfCriteria();

        assertThat(cerfCriteria).hasToString("CerfCriteria{}");
    }

    private static void setAllFilters(CerfCriteria cerfCriteria) {
        cerfCriteria.id();
        cerfCriteria.cerfNo();
        cerfCriteria.cerfVer();
        cerfCriteria.status();
        cerfCriteria.issuDt();
        cerfCriteria.expDt();
        cerfCriteria.cerfProdId();
        cerfCriteria.cerfStdId();
        cerfCriteria.cerfMarkId();
        cerfCriteria.cerfCompanyId();
        cerfCriteria.feeProdCerfCompanyId();
        cerfCriteria.countryCertId();
        cerfCriteria.distinct();
    }

    private static Condition<CerfCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCerfNo()) &&
                condition.apply(criteria.getCerfVer()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getIssuDt()) &&
                condition.apply(criteria.getExpDt()) &&
                condition.apply(criteria.getCerfProdId()) &&
                condition.apply(criteria.getCerfStdId()) &&
                condition.apply(criteria.getCerfMarkId()) &&
                condition.apply(criteria.getCerfCompanyId()) &&
                condition.apply(criteria.getFeeProdCerfCompanyId()) &&
                condition.apply(criteria.getCountryCertId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CerfCriteria> copyFiltersAre(CerfCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCerfNo(), copy.getCerfNo()) &&
                condition.apply(criteria.getCerfVer(), copy.getCerfVer()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getIssuDt(), copy.getIssuDt()) &&
                condition.apply(criteria.getExpDt(), copy.getExpDt()) &&
                condition.apply(criteria.getCerfProdId(), copy.getCerfProdId()) &&
                condition.apply(criteria.getCerfStdId(), copy.getCerfStdId()) &&
                condition.apply(criteria.getCerfMarkId(), copy.getCerfMarkId()) &&
                condition.apply(criteria.getCerfCompanyId(), copy.getCerfCompanyId()) &&
                condition.apply(criteria.getFeeProdCerfCompanyId(), copy.getFeeProdCerfCompanyId()) &&
                condition.apply(criteria.getCountryCertId(), copy.getCountryCertId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
