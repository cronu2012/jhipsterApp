package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CerfCompanyAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCerfCompanyAllPropertiesEquals(CerfCompany expected, CerfCompany actual) {
        assertCerfCompanyAutoGeneratedPropertiesEquals(expected, actual);
        assertCerfCompanyAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCerfCompanyAllUpdatablePropertiesEquals(CerfCompany expected, CerfCompany actual) {
        assertCerfCompanyUpdatableFieldsEquals(expected, actual);
        assertCerfCompanyUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCerfCompanyAutoGeneratedPropertiesEquals(CerfCompany expected, CerfCompany actual) {
        assertThat(expected)
            .as("Verify CerfCompany auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCerfCompanyUpdatableFieldsEquals(CerfCompany expected, CerfCompany actual) {
        assertThat(expected)
            .as("Verify CerfCompany relevant properties")
            .satisfies(e -> assertThat(e.getRelType()).as("check relType").isEqualTo(actual.getRelType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCerfCompanyUpdatableRelationshipsEquals(CerfCompany expected, CerfCompany actual) {
        assertThat(expected)
            .as("Verify CerfCompany relationships")
            .satisfies(e -> assertThat(e.getCerf()).as("check cerf").isEqualTo(actual.getCerf()))
            .satisfies(e -> assertThat(e.getCompany()).as("check company").isEqualTo(actual.getCompany()));
    }
}