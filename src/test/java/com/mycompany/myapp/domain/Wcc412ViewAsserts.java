package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class Wcc412ViewAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWcc412ViewAllPropertiesEquals(Wcc412View expected, Wcc412View actual) {
        assertWcc412ViewAutoGeneratedPropertiesEquals(expected, actual);
        assertWcc412ViewAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWcc412ViewAllUpdatablePropertiesEquals(Wcc412View expected, Wcc412View actual) {
        assertWcc412ViewUpdatableFieldsEquals(expected, actual);
        assertWcc412ViewUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWcc412ViewAutoGeneratedPropertiesEquals(Wcc412View expected, Wcc412View actual) {
        assertThat(expected)
            .as("Verify Wcc412View auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWcc412ViewUpdatableFieldsEquals(Wcc412View expected, Wcc412View actual) {
        assertThat(expected)
            .as("Verify Wcc412View relevant properties")
            .satisfies(e -> assertThat(e.getCerfId()).as("check cerfId").isEqualTo(actual.getCerfId()))
            .satisfies(e -> assertThat(e.getCountryChName()).as("check countryChName").isEqualTo(actual.getCountryChName()))
            .satisfies(e -> assertThat(e.getCerfNo()).as("check cerfNo").isEqualTo(actual.getCerfNo()))
            .satisfies(e -> assertThat(e.getCerfVer()).as("check cerfVer").isEqualTo(actual.getCerfVer()))
            .satisfies(e -> assertThat(e.getCerfStatus()).as("check cerfStatus").isEqualTo(actual.getCerfStatus()))
            .satisfies(e -> assertThat(e.getCountryId()).as("check countryId").isEqualTo(actual.getCountryId()))
            .satisfies(e -> assertThat(e.getProdNo()).as("check prodNo").isEqualTo(actual.getProdNo()))
            .satisfies(e -> assertThat(e.getProdChName()).as("check prodChName").isEqualTo(actual.getProdChName()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWcc412ViewUpdatableRelationshipsEquals(Wcc412View expected, Wcc412View actual) {}
}
