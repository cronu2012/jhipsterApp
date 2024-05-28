package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Wcc421View} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.Wcc421ViewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wcc-421-views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wcc421ViewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter cerfId;

    private StringFilter countryChName;

    private StringFilter cerfNo;

    private StringFilter cerfVer;

    private StringFilter cerfStatus;

    private StringFilter companyChName;

    private StringFilter relType;

    private Boolean distinct;

    public Wcc421ViewCriteria() {}

    public Wcc421ViewCriteria(Wcc421ViewCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.cerfId = other.optionalCerfId().map(LongFilter::copy).orElse(null);
        this.countryChName = other.optionalCountryChName().map(StringFilter::copy).orElse(null);
        this.cerfNo = other.optionalCerfNo().map(StringFilter::copy).orElse(null);
        this.cerfVer = other.optionalCerfVer().map(StringFilter::copy).orElse(null);
        this.cerfStatus = other.optionalCerfStatus().map(StringFilter::copy).orElse(null);
        this.companyChName = other.optionalCompanyChName().map(StringFilter::copy).orElse(null);
        this.relType = other.optionalRelType().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public Wcc421ViewCriteria copy() {
        return new Wcc421ViewCriteria(this);
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

    public StringFilter getCountryChName() {
        return countryChName;
    }

    public Optional<StringFilter> optionalCountryChName() {
        return Optional.ofNullable(countryChName);
    }

    public StringFilter countryChName() {
        if (countryChName == null) {
            setCountryChName(new StringFilter());
        }
        return countryChName;
    }

    public void setCountryChName(StringFilter countryChName) {
        this.countryChName = countryChName;
    }

    public StringFilter getCerfNo() {
        return cerfNo;
    }

    public Optional<StringFilter> optionalCerfNo() {
        return Optional.ofNullable(cerfNo);
    }

    public StringFilter cerfNo() {
        if (cerfNo == null) {
            setCerfNo(new StringFilter());
        }
        return cerfNo;
    }

    public void setCerfNo(StringFilter cerfNo) {
        this.cerfNo = cerfNo;
    }

    public StringFilter getCerfVer() {
        return cerfVer;
    }

    public Optional<StringFilter> optionalCerfVer() {
        return Optional.ofNullable(cerfVer);
    }

    public StringFilter cerfVer() {
        if (cerfVer == null) {
            setCerfVer(new StringFilter());
        }
        return cerfVer;
    }

    public void setCerfVer(StringFilter cerfVer) {
        this.cerfVer = cerfVer;
    }

    public StringFilter getCerfStatus() {
        return cerfStatus;
    }

    public Optional<StringFilter> optionalCerfStatus() {
        return Optional.ofNullable(cerfStatus);
    }

    public StringFilter cerfStatus() {
        if (cerfStatus == null) {
            setCerfStatus(new StringFilter());
        }
        return cerfStatus;
    }

    public void setCerfStatus(StringFilter cerfStatus) {
        this.cerfStatus = cerfStatus;
    }

    public StringFilter getCompanyChName() {
        return companyChName;
    }

    public Optional<StringFilter> optionalCompanyChName() {
        return Optional.ofNullable(companyChName);
    }

    public StringFilter companyChName() {
        if (companyChName == null) {
            setCompanyChName(new StringFilter());
        }
        return companyChName;
    }

    public void setCompanyChName(StringFilter companyChName) {
        this.companyChName = companyChName;
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
        final Wcc421ViewCriteria that = (Wcc421ViewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cerfId, that.cerfId) &&
            Objects.equals(countryChName, that.countryChName) &&
            Objects.equals(cerfNo, that.cerfNo) &&
            Objects.equals(cerfVer, that.cerfVer) &&
            Objects.equals(cerfStatus, that.cerfStatus) &&
            Objects.equals(companyChName, that.companyChName) &&
            Objects.equals(relType, that.relType) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cerfId, countryChName, cerfNo, cerfVer, cerfStatus, companyChName, relType, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wcc421ViewCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCerfId().map(f -> "cerfId=" + f + ", ").orElse("") +
            optionalCountryChName().map(f -> "countryChName=" + f + ", ").orElse("") +
            optionalCerfNo().map(f -> "cerfNo=" + f + ", ").orElse("") +
            optionalCerfVer().map(f -> "cerfVer=" + f + ", ").orElse("") +
            optionalCerfStatus().map(f -> "cerfStatus=" + f + ", ").orElse("") +
            optionalCompanyChName().map(f -> "companyChName=" + f + ", ").orElse("") +
            optionalRelType().map(f -> "relType=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
