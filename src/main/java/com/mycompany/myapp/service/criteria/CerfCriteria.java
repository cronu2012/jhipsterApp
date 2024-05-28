package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Cerf} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CerfResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cerfs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cerfNo;

    private StringFilter cerfVer;

    private StringFilter status;

    private LocalDateFilter issuDt;

    private LocalDateFilter expDt;

    private LongFilter cerfProdId;

    private LongFilter cerfStdId;

    private LongFilter cerfMarkId;

    private LongFilter cerfCompanyId;

    private LongFilter feeProdCerfCompanyId;

    private LongFilter countryCertId;

    private Boolean distinct;

    public CerfCriteria() {}

    public CerfCriteria(CerfCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.cerfNo = other.optionalCerfNo().map(StringFilter::copy).orElse(null);
        this.cerfVer = other.optionalCerfVer().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.issuDt = other.optionalIssuDt().map(LocalDateFilter::copy).orElse(null);
        this.expDt = other.optionalExpDt().map(LocalDateFilter::copy).orElse(null);
        this.cerfProdId = other.optionalCerfProdId().map(LongFilter::copy).orElse(null);
        this.cerfStdId = other.optionalCerfStdId().map(LongFilter::copy).orElse(null);
        this.cerfMarkId = other.optionalCerfMarkId().map(LongFilter::copy).orElse(null);
        this.cerfCompanyId = other.optionalCerfCompanyId().map(LongFilter::copy).orElse(null);
        this.feeProdCerfCompanyId = other.optionalFeeProdCerfCompanyId().map(LongFilter::copy).orElse(null);
        this.countryCertId = other.optionalCountryCertId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CerfCriteria copy() {
        return new CerfCriteria(this);
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

    public LongFilter getCerfProdId() {
        return cerfProdId;
    }

    public Optional<LongFilter> optionalCerfProdId() {
        return Optional.ofNullable(cerfProdId);
    }

    public LongFilter cerfProdId() {
        if (cerfProdId == null) {
            setCerfProdId(new LongFilter());
        }
        return cerfProdId;
    }

    public void setCerfProdId(LongFilter cerfProdId) {
        this.cerfProdId = cerfProdId;
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

    public LongFilter getCerfMarkId() {
        return cerfMarkId;
    }

    public Optional<LongFilter> optionalCerfMarkId() {
        return Optional.ofNullable(cerfMarkId);
    }

    public LongFilter cerfMarkId() {
        if (cerfMarkId == null) {
            setCerfMarkId(new LongFilter());
        }
        return cerfMarkId;
    }

    public void setCerfMarkId(LongFilter cerfMarkId) {
        this.cerfMarkId = cerfMarkId;
    }

    public LongFilter getCerfCompanyId() {
        return cerfCompanyId;
    }

    public Optional<LongFilter> optionalCerfCompanyId() {
        return Optional.ofNullable(cerfCompanyId);
    }

    public LongFilter cerfCompanyId() {
        if (cerfCompanyId == null) {
            setCerfCompanyId(new LongFilter());
        }
        return cerfCompanyId;
    }

    public void setCerfCompanyId(LongFilter cerfCompanyId) {
        this.cerfCompanyId = cerfCompanyId;
    }

    public LongFilter getFeeProdCerfCompanyId() {
        return feeProdCerfCompanyId;
    }

    public Optional<LongFilter> optionalFeeProdCerfCompanyId() {
        return Optional.ofNullable(feeProdCerfCompanyId);
    }

    public LongFilter feeProdCerfCompanyId() {
        if (feeProdCerfCompanyId == null) {
            setFeeProdCerfCompanyId(new LongFilter());
        }
        return feeProdCerfCompanyId;
    }

    public void setFeeProdCerfCompanyId(LongFilter feeProdCerfCompanyId) {
        this.feeProdCerfCompanyId = feeProdCerfCompanyId;
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
        final CerfCriteria that = (CerfCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cerfNo, that.cerfNo) &&
            Objects.equals(cerfVer, that.cerfVer) &&
            Objects.equals(status, that.status) &&
            Objects.equals(issuDt, that.issuDt) &&
            Objects.equals(expDt, that.expDt) &&
            Objects.equals(cerfProdId, that.cerfProdId) &&
            Objects.equals(cerfStdId, that.cerfStdId) &&
            Objects.equals(cerfMarkId, that.cerfMarkId) &&
            Objects.equals(cerfCompanyId, that.cerfCompanyId) &&
            Objects.equals(feeProdCerfCompanyId, that.feeProdCerfCompanyId) &&
            Objects.equals(countryCertId, that.countryCertId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cerfNo,
            cerfVer,
            status,
            issuDt,
            expDt,
            cerfProdId,
            cerfStdId,
            cerfMarkId,
            cerfCompanyId,
            feeProdCerfCompanyId,
            countryCertId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCerfNo().map(f -> "cerfNo=" + f + ", ").orElse("") +
            optionalCerfVer().map(f -> "cerfVer=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalIssuDt().map(f -> "issuDt=" + f + ", ").orElse("") +
            optionalExpDt().map(f -> "expDt=" + f + ", ").orElse("") +
            optionalCerfProdId().map(f -> "cerfProdId=" + f + ", ").orElse("") +
            optionalCerfStdId().map(f -> "cerfStdId=" + f + ", ").orElse("") +
            optionalCerfMarkId().map(f -> "cerfMarkId=" + f + ", ").orElse("") +
            optionalCerfCompanyId().map(f -> "cerfCompanyId=" + f + ", ").orElse("") +
            optionalFeeProdCerfCompanyId().map(f -> "feeProdCerfCompanyId=" + f + ", ").orElse("") +
            optionalCountryCertId().map(f -> "countryCertId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
