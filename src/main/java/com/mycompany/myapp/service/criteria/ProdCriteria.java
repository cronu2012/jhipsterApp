package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Prod} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prodNo;

    private StringFilter enName;

    private StringFilter chName;

    private StringFilter hsCode;

    private StringFilter cccCode;

    private LongFilter prodCountryId;

    private LongFilter prodStdId;

    private LongFilter cerfProdId;

    private LongFilter feeProdCerfCompanyId;

    private LongFilter prodStickerId;

    private Boolean distinct;

    public ProdCriteria() {}

    public ProdCriteria(ProdCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.prodNo = other.optionalProdNo().map(StringFilter::copy).orElse(null);
        this.enName = other.optionalEnName().map(StringFilter::copy).orElse(null);
        this.chName = other.optionalChName().map(StringFilter::copy).orElse(null);
        this.hsCode = other.optionalHsCode().map(StringFilter::copy).orElse(null);
        this.cccCode = other.optionalCccCode().map(StringFilter::copy).orElse(null);
        this.prodCountryId = other.optionalProdCountryId().map(LongFilter::copy).orElse(null);
        this.prodStdId = other.optionalProdStdId().map(LongFilter::copy).orElse(null);
        this.cerfProdId = other.optionalCerfProdId().map(LongFilter::copy).orElse(null);
        this.feeProdCerfCompanyId = other.optionalFeeProdCerfCompanyId().map(LongFilter::copy).orElse(null);
        this.prodStickerId = other.optionalProdStickerId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProdCriteria copy() {
        return new ProdCriteria(this);
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

    public StringFilter getProdNo() {
        return prodNo;
    }

    public Optional<StringFilter> optionalProdNo() {
        return Optional.ofNullable(prodNo);
    }

    public StringFilter prodNo() {
        if (prodNo == null) {
            setProdNo(new StringFilter());
        }
        return prodNo;
    }

    public void setProdNo(StringFilter prodNo) {
        this.prodNo = prodNo;
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

    public StringFilter getHsCode() {
        return hsCode;
    }

    public Optional<StringFilter> optionalHsCode() {
        return Optional.ofNullable(hsCode);
    }

    public StringFilter hsCode() {
        if (hsCode == null) {
            setHsCode(new StringFilter());
        }
        return hsCode;
    }

    public void setHsCode(StringFilter hsCode) {
        this.hsCode = hsCode;
    }

    public StringFilter getCccCode() {
        return cccCode;
    }

    public Optional<StringFilter> optionalCccCode() {
        return Optional.ofNullable(cccCode);
    }

    public StringFilter cccCode() {
        if (cccCode == null) {
            setCccCode(new StringFilter());
        }
        return cccCode;
    }

    public void setCccCode(StringFilter cccCode) {
        this.cccCode = cccCode;
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

    public LongFilter getProdStickerId() {
        return prodStickerId;
    }

    public Optional<LongFilter> optionalProdStickerId() {
        return Optional.ofNullable(prodStickerId);
    }

    public LongFilter prodStickerId() {
        if (prodStickerId == null) {
            setProdStickerId(new LongFilter());
        }
        return prodStickerId;
    }

    public void setProdStickerId(LongFilter prodStickerId) {
        this.prodStickerId = prodStickerId;
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
        final ProdCriteria that = (ProdCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(prodNo, that.prodNo) &&
            Objects.equals(enName, that.enName) &&
            Objects.equals(chName, that.chName) &&
            Objects.equals(hsCode, that.hsCode) &&
            Objects.equals(cccCode, that.cccCode) &&
            Objects.equals(prodCountryId, that.prodCountryId) &&
            Objects.equals(prodStdId, that.prodStdId) &&
            Objects.equals(cerfProdId, that.cerfProdId) &&
            Objects.equals(feeProdCerfCompanyId, that.feeProdCerfCompanyId) &&
            Objects.equals(prodStickerId, that.prodStickerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            prodNo,
            enName,
            chName,
            hsCode,
            cccCode,
            prodCountryId,
            prodStdId,
            cerfProdId,
            feeProdCerfCompanyId,
            prodStickerId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalProdNo().map(f -> "prodNo=" + f + ", ").orElse("") +
            optionalEnName().map(f -> "enName=" + f + ", ").orElse("") +
            optionalChName().map(f -> "chName=" + f + ", ").orElse("") +
            optionalHsCode().map(f -> "hsCode=" + f + ", ").orElse("") +
            optionalCccCode().map(f -> "cccCode=" + f + ", ").orElse("") +
            optionalProdCountryId().map(f -> "prodCountryId=" + f + ", ").orElse("") +
            optionalProdStdId().map(f -> "prodStdId=" + f + ", ").orElse("") +
            optionalCerfProdId().map(f -> "cerfProdId=" + f + ", ").orElse("") +
            optionalFeeProdCerfCompanyId().map(f -> "feeProdCerfCompanyId=" + f + ", ").orElse("") +
            optionalProdStickerId().map(f -> "prodStickerId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
