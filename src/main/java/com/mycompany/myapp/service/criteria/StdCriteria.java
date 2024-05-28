package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Std} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter stdNo;

    private StringFilter stdVer;

    private StringFilter enName;

    private StringFilter chName;

    private StringFilter status;

    private LocalDateFilter issuDt;

    private LocalDateFilter expDt;

    private LongFilter prodStdId;

    private LongFilter cerfStdId;

    private LongFilter countryStdId;

    private Boolean distinct;

    public StdCriteria() {}

    public StdCriteria(StdCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.stdNo = other.optionalStdNo().map(StringFilter::copy).orElse(null);
        this.stdVer = other.optionalStdVer().map(StringFilter::copy).orElse(null);
        this.enName = other.optionalEnName().map(StringFilter::copy).orElse(null);
        this.chName = other.optionalChName().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.issuDt = other.optionalIssuDt().map(LocalDateFilter::copy).orElse(null);
        this.expDt = other.optionalExpDt().map(LocalDateFilter::copy).orElse(null);
        this.prodStdId = other.optionalProdStdId().map(LongFilter::copy).orElse(null);
        this.cerfStdId = other.optionalCerfStdId().map(LongFilter::copy).orElse(null);
        this.countryStdId = other.optionalCountryStdId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StdCriteria copy() {
        return new StdCriteria(this);
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

    public StringFilter getStdNo() {
        return stdNo;
    }

    public Optional<StringFilter> optionalStdNo() {
        return Optional.ofNullable(stdNo);
    }

    public StringFilter stdNo() {
        if (stdNo == null) {
            setStdNo(new StringFilter());
        }
        return stdNo;
    }

    public void setStdNo(StringFilter stdNo) {
        this.stdNo = stdNo;
    }

    public StringFilter getStdVer() {
        return stdVer;
    }

    public Optional<StringFilter> optionalStdVer() {
        return Optional.ofNullable(stdVer);
    }

    public StringFilter stdVer() {
        if (stdVer == null) {
            setStdVer(new StringFilter());
        }
        return stdVer;
    }

    public void setStdVer(StringFilter stdVer) {
        this.stdVer = stdVer;
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

    public StringFilter getStatus() {
        return status;
    }

    public Optional<StringFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public StringFilter status() {
        if (status == null) {
            setStatus(new StringFilter());
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getIssuDt() {
        return issuDt;
    }

    public Optional<LocalDateFilter> optionalIssuDt() {
        return Optional.ofNullable(issuDt);
    }

    public LocalDateFilter issuDt() {
        if (issuDt == null) {
            setIssuDt(new LocalDateFilter());
        }
        return issuDt;
    }

    public void setIssuDt(LocalDateFilter issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDateFilter getExpDt() {
        return expDt;
    }

    public Optional<LocalDateFilter> optionalExpDt() {
        return Optional.ofNullable(expDt);
    }

    public LocalDateFilter expDt() {
        if (expDt == null) {
            setExpDt(new LocalDateFilter());
        }
        return expDt;
    }

    public void setExpDt(LocalDateFilter expDt) {
        this.expDt = expDt;
    }

    public LongFilter getProdStdId() {
        return prodStdId;
    }

    public Optional<LongFilter> optionalProdStdId() {
        return Optional.ofNullable(prodStdId);
    }

    public LongFilter prodStdId() {
        if (prodStdId == null) {
            setProdStdId(new LongFilter());
        }
        return prodStdId;
    }

    public void setProdStdId(LongFilter prodStdId) {
        this.prodStdId = prodStdId;
    }

    public LongFilter getCerfStdId() {
        return cerfStdId;
    }

    public Optional<LongFilter> optionalCerfStdId() {
        return Optional.ofNullable(cerfStdId);
    }

    public LongFilter cerfStdId() {
        if (cerfStdId == null) {
            setCerfStdId(new LongFilter());
        }
        return cerfStdId;
    }

    public void setCerfStdId(LongFilter cerfStdId) {
        this.cerfStdId = cerfStdId;
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
        final StdCriteria that = (StdCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stdNo, that.stdNo) &&
            Objects.equals(stdVer, that.stdVer) &&
            Objects.equals(enName, that.enName) &&
            Objects.equals(chName, that.chName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(issuDt, that.issuDt) &&
            Objects.equals(expDt, that.expDt) &&
            Objects.equals(prodStdId, that.prodStdId) &&
            Objects.equals(cerfStdId, that.cerfStdId) &&
            Objects.equals(countryStdId, that.countryStdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stdNo, stdVer, enName, chName, status, issuDt, expDt, prodStdId, cerfStdId, countryStdId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StdCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStdNo().map(f -> "stdNo=" + f + ", ").orElse("") +
            optionalStdVer().map(f -> "stdVer=" + f + ", ").orElse("") +
            optionalEnName().map(f -> "enName=" + f + ", ").orElse("") +
            optionalChName().map(f -> "chName=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalIssuDt().map(f -> "issuDt=" + f + ", ").orElse("") +
            optionalExpDt().map(f -> "expDt=" + f + ", ").orElse("") +
            optionalProdStdId().map(f -> "prodStdId=" + f + ", ").orElse("") +
            optionalCerfStdId().map(f -> "cerfStdId=" + f + ", ").orElse("") +
            optionalCountryStdId().map(f -> "countryStdId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
