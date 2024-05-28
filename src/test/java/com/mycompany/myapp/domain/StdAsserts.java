package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StdAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStdAllPropertiesEquals(Std expected, Std actual) {
        assertStdAutoGeneratedPropertiesEquals(expected, actual);
        assertStdAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStdAllUpdatablePropertiesEquals(Std expected, Std actual) {
        assertStdUpdatableFieldsEquals(expected, actual);
        assertStdUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStdAutoGeneratedPropertiesEquals(Std expected, Std actual) {
        assertThat(expected)
            .as("Verify Std auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStdUpdatableFieldsEquals(Std expected, Std actual) {
        assertThat(expected)
            .as("Verify Std relevant properties")
            .satisfies(e -> assertThat(e.getStdNo()).as("check stdNo").isEqualTo(actual.getStdNo()))
            .satisfies(e -> assertThat(e.getStdVer()).as("check stdVer").isEqualTo(actual.getStdVer()))
            .satisfies(e -> assertThat(e.getEnName()).as("check enName").isEqualTo(actual.getEnName()))
            .satisfies(e -> assertThat(e.getChName()).as("check chName").isEqualTo(actual.getChName()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getIssuDt()).as("check issuDt").isEqualTo(actual.getIssuDt()))
            .satisfies(e -> assertThat(e.getExpDt()).as("check expDt").isEqualTo(actual.getExpDt()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStdUpdatableRelationshipsEquals(Std expected, Std actual) {}
}