package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Country} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter countryNo;

    private StringFilter enName;

    private StringFilter chName;

    private LongFilter prodCountryId;

    private LongFilter countryStdId;

    private LongFilter countryCertId;

    private LongFilter countryMarkId;

    private Boolean distinct;

    public CountryCriteria() {}

    public CountryCriteria(CountryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.countryNo = other.optionalCountryNo().map(StringFilter::copy).orElse(null);
        this.enName = other.optionalEnName().map(StringFilter::copy).orElse(null);
        this.chName = other.optionalChName().map(StringFilter::copy).orElse(null);
        this.prodCountryId = other.optionalProdCountryId().map(LongFilter::copy).orElse(null);
        this.countryStdId = other.optionalCountryStdId().map(LongFilter::copy).orElse(null);
        this.countryCertId = other.optionalCountryCertId().map(LongFilter::copy).orElse(null);
        this.countryMarkId = other.optionalCountryMarkId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
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

    public StringFilter getCountryNo() {
        return countryNo;
    }

    public Optional<StringFilter> optionalCountryNo() {
        return Optional.ofNullable(countryNo);
    }

    public StringFilter countryNo() {
        if (countryNo == null) {
            setCountryNo(new StringFilter());
        }
        return countryNo;
    }

    public void setCountryNo(StringFilter countryNo) {
        this.countryNo = countryNo;
    }

    public StringFilter getEnName() {
        return enName;
    }

    public Optional<StringFilter> optionalEnName() {
        return Optional.ofNullable(enName);
    }

    public StringFilter enName() {
        if (enName == null) {
            setEnName(new StringFilter());
        }
        return enName;
    }

    public void setEnName(StringFilter enName) {
        this.enName = enName;
    }

    public StringFilter getChName() {
        return chName;
    }

    public Optional<StringFilter> optionalChName() {
        return Optional.ofNullable(chName);
    }

    public StringFilter chName() {
        if (chName == null) {
            setChName(new StringFilter());
        }
        return chName;
    }

    public void setChName(StringFilter chName) {
        this.chName = chName;
    }

    public LongFilter getProdCountryId() {
        return prodCountryId;
    }

    public Optional<LongFilter> optionalProdCountryId() {
        return Optional.ofNullable(prodCountryId);
    }

    public LongFilter prodCountryId() {
        if (prodCountryId == null) {
            setProdCountryId(new LongFilter());
        }
        return prodCountryId;
    }

    public void setProdCountryId(LongFilter prodCountryId) {
        this.prodCountryId = prodCountryId;
    }

    public LongFilter getCountryStdId() {
        return countryStdId;
    }

    public Optional<LongFilter> optionalCountryStdId() {
        return Optional.ofNullable(countryStdId);
    }

    public LongFilter countryStdId() {
        if (countryStdId == null) {
            setCountryStdId(new LongFilter());
        }
        return countryStdId;
    }

    public void setCountryStdId(LongFilter countryStdId) {
        this.countryStdId = countryStdId;
    }

    public LongFilter getCountryCertId() {
        return countryCertId;
    }

    public Optional<LongFilter> optionalCountryCertId() {
        return Optional.ofNullable(countryCertId);
    }

    public LongFilter countryCertId() {
        if (countryCertId == null) {
            setCountryCertId(new LongFilter());
        }
        return countryCertId;
    }

    public void setCountryCertId(LongFilter countryCertId) {
        this.countryCertId = countryCertId;
    }

    public LongFilter getCountryMarkId() {
        return countryMarkId;
    }

    public Optional<LongFilter> optionalCountryMarkId() {
        return Optional.ofNullable(countryMarkId);
    }

    public LongFilter countryMarkId() {
        if (countryMarkId == null) {
            setCountryMarkId(new LongFilter());
        }
        return countryMarkId;
    }

    public void setCountryMarkId(LongFilter countryMarkId) {
        this.countryMarkId = countryMarkId;
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
        final CountryCriteria that = (CountryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(countryNo, that.countryNo) &&
            Objects.equals(enName, that.enName) &&
            Objects.equals(chName, that.chName) &&
            Objects.equals(prodCountryId, that.prodCountryId) &&
            Objects.equals(countryStdId, that.countryStdId) &&
            Objects.equals(countryCertId, that.countryCertId) &&
            Objects.equals(countryMarkId, that.countryMarkId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryNo, enName, chName, prodCountryId, countryStdId, countryCertId, countryMarkId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCountryNo().map(f -> "countryNo=" + f + ", ").orElse("") +
            optionalEnName().map(f -> "enName=" + f + ", ").orElse("") +
            optionalChName().map(f -> "chName=" + f + ", ").orElse("") +
            optionalProdCountryId().map(f -> "prodCountryId=" + f + ", ").orElse("") +
            optionalCountryStdId().map(f -> "countryStdId=" + f + ", ").orElse("") +
            optionalCountryCertId().map(f -> "countryCertId=" + f + ", ").orElse("") +
            optionalCountryMarkId().map(f -> "countryMarkId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
