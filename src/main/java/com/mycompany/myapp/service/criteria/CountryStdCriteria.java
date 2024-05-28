package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CountryStd} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CountryStdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /country-stds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryStdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter relType;

    private LongFilter countryId;

    private LongFilter stdId;

    private Boolean distinct;

    public CountryStdCriteria() {}

    public CountryStdCriteria(CountryStdCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.relType = other.optionalRelType().map(StringFilter::copy).orElse(null);
        this.countryId = other.optionalCountryId().map(LongFilter::copy).orElse(null);
        this.stdId = other.optionalStdId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CountryStdCriteria copy() {
        return new CountryStdCriteria(this);
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

    public LongFilter getStdId() {
        return stdId;
    }

    public Optional<LongFilter> optionalStdId() {
        return Optional.ofNullable(stdId);
    }

    public LongFilter stdId() {
        if (stdId == null) {
            setStdId(new LongFilter());
        }
        return stdId;
    }

    public void setStdId(LongFilter stdId) {
        this.stdId = stdId;
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
        final CountryStdCriteria that = (CountryStdCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(relType, that.relType) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(stdId, that.stdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relType, countryId, stdId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryStdCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRelType().map(f -> "relType=" + f + ", ").orElse("") +
            optionalCountryId().map(f -> "countryId=" + f + ", ").orElse("") +
            optionalStdId().map(f -> "stdId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
