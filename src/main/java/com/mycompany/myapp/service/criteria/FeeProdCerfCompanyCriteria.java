package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.FeeProdCerfCompany} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.FeeProdCerfCompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fee-prod-cerf-companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeeProdCerfCompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter fee;

    private StringFilter feeType;

    private LocalDateFilter feeDt;

    private LongFilter prodId;

    private LongFilter cerfId;

    private LongFilter companyId;

    private Boolean distinct;

    public FeeProdCerfCompanyCriteria() {}

    public FeeProdCerfCompanyCriteria(FeeProdCerfCompanyCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fee = other.optionalFee().map(LongFilter::copy).orElse(null);
        this.feeType = other.optionalFeeType().map(StringFilter::copy).orElse(null);
        this.feeDt = other.optionalFeeDt().map(LocalDateFilter::copy).orElse(null);
        this.prodId = other.optionalProdId().map(LongFilter::copy).orElse(null);
        this.cerfId = other.optionalCerfId().map(LongFilter::copy).orElse(null);
        this.companyId = other.optionalCompanyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FeeProdCerfCompanyCriteria copy() {
        return new FeeProdCerfCompanyCriteria(this);
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

    public LongFilter getFee() {
        return fee;
    }

    public Optional<LongFilter> optionalFee() {
        return Optional.ofNullable(fee);
    }

    public LongFilter fee() {
        if (fee == null) {
            setFee(new LongFilter());
        }
        return fee;
    }

    public void setFee(LongFilter fee) {
        this.fee = fee;
    }

    public StringFilter getFeeType() {
        return feeType;
    }

    public Optional<StringFilter> optionalFeeType() {
        return Optional.ofNullable(feeType);
    }

    public StringFilter feeType() {
        if (feeType == null) {
            setFeeType(new StringFilter());
        }
        return feeType;
    }

    public void setFeeType(StringFilter feeType) {
        this.feeType = feeType;
    }

    public LocalDateFilter getFeeDt() {
        return feeDt;
    }

    public Optional<LocalDateFilter> optionalFeeDt() {
        return Optional.ofNullable(feeDt);
    }

    public LocalDateFilter feeDt() {
        if (feeDt == null) {
            setFeeDt(new LocalDateFilter());
        }
        return feeDt;
    }

    public void setFeeDt(LocalDateFilter feeDt) {
        this.feeDt = feeDt;
    }

    public LongFilter getProdId() {
        return prodId;
    }

    public Optional<LongFilter> optionalProdId() {
        return Optional.ofNullable(prodId);
    }

    public LongFilter prodId() {
        if (prodId == null) {
            setProdId(new LongFilter());
        }
        return prodId;
    }

    public void setProdId(LongFilter prodId) {
        this.prodId = prodId;
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

    public LongFilter getCompanyId() {
        return companyId;
    }

    public Optional<LongFilter> optionalCompanyId() {
        return Optional.ofNullable(companyId);
    }

    public LongFilter companyId() {
        if (companyId == null) {
            setCompanyId(new LongFilter());
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final FeeProdCerfCompanyCriteria that = (FeeProdCerfCompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fee, that.fee) &&
            Objects.equals(feeType, that.feeType) &&
            Objects.equals(feeDt, that.feeDt) &&
            Objects.equals(prodId, that.prodId) &&
            Objects.equals(cerfId, that.cerfId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fee, feeType, feeDt, prodId, cerfId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeeProdCerfCompanyCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFee().map(f -> "fee=" + f + ", ").orElse("") +
            optionalFeeType().map(f -> "feeType=" + f + ", ").orElse("") +
            optionalFeeDt().map(f -> "feeDt=" + f + ", ").orElse("") +
            optionalProdId().map(f -> "prodId=" + f + ", ").orElse("") +
            optionalCerfId().map(f -> "cerfId=" + f + ", ").orElse("") +
            optionalCompanyId().map(f -> "companyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
