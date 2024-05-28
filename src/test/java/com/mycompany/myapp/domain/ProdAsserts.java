package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProdAllPropertiesEquals(Prod expected, Prod actual) {
        assertProdAutoGeneratedPropertiesEquals(expected, actual);
        assertProdAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProdAllUpdatablePropertiesEquals(Prod expected, Prod actual) {
        assertProdUpdatableFieldsEquals(expected, actual);
        assertProdUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProdAutoGeneratedPropertiesEquals(Prod expected, Prod actual) {
        assertThat(expected)
            .as("Verify Prod auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProdUpdatableFieldsEquals(Prod expected, Prod actual) {
        assertThat(expected)
            .as("Verify Prod relevant properties")
            .satisfies(e -> assertThat(e.getProdNo()).as("check prodNo").isEqualTo(actual.getProdNo()))
            .satisfies(e -> assertThat(e.getEnName()).as("check enName").isEqualTo(actual.getEnName()))
            .satisfies(e -> assertThat(e.getChName()).as("check chName").isEqualTo(actual.getChName()))
            .satisfies(e -> assertThat(e.getHsCode()).as("check hsCode").isEqualTo(actual.getHsCode()))
            .satisfies(e -> assertThat(e.getCccCode()).as("check cccCode").isEqualTo(actual.getCccCode()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProdUpdatableRelationshipsEquals(Prod expected, Prod actual) {}
}
