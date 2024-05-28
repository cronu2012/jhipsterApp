package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CountryCert} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CountryCertResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /country-certs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryCertCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter relType;

    private LongFilter countryId;

    private LongFilter cerfId;

    private Boolean distinct;

    public CountryCertCriteria() {}

    public CountryCertCriteria(CountryCertCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.relType = other.optionalRelType().map(StringFilter::copy).orElse(null);
        this.countryId = other.optionalCountryId().map(LongFilter::copy).orElse(null);
        this.cerfId = other.optionalCerfId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CountryCertCriteria copy() {
        return new CountryCertCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRelType() {
        return relType;
    }

    public Optional<StringFilter> optionalRelType() {
        return Optional.ofNullable(relType);
    }

    public StringFilter relType() {
        if (relType == null) {
            setRelType(new StringFilter());
        }
        return relType;
    }

    public void setRelType(StringFilter relType) {
        this.relType = relType;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public Optional<LongFilter> optionalCountryId() {
        return Optional.ofNullable(countryId);
    }

    public LongFilter countryId() {
        if (countryId == null) {
            setCountryId(new LongFilter());
        }
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getCerfId() {
        return cerfId;
    }

    public Optional<LongFilter> optionalCerfId() {
        return Optional.ofNullable(cerfId);
    }

    public LongFilter cerfId() {
        if (cerfId == null) {
            setCerfId(new LongFilter());
        }
        return cerfId;
    }

    public void setCerfId(LongFilter cerfId) {
        this.cerfId = cerfId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCertCriteria that = (CountryCertCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(relType, that.relType) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(cerfId, that.cerfId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relType, countryId, cerfId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCertCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRelType().map(f -> "relType=" + f + ", ").orElse("") +
            optionalCountryId().map(f -> "countryId=" + f + ", ").orElse("") +
            optionalCerfId().map(f -> "cerfId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
