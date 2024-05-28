package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Company} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyNo;

    private StringFilter enName;

    private StringFilter chName;

    private StringFilter tel;

    private StringFilter addr;

    private StringFilter email;

    private StringFilter peopleName;

    private LongFilter cerfCompanyId;

    private LongFilter feeProdCerfCompanyId;

    private Boolean distinct;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.companyNo = other.optionalCompanyNo().map(StringFilter::copy).orElse(null);
        this.enName = other.optionalEnName().map(StringFilter::copy).orElse(null);
        this.chName = other.optionalChName().map(StringFilter::copy).orElse(null);
        this.tel = other.optionalTel().map(StringFilter::copy).orElse(null);
        this.addr = other.optionalAddr().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.peopleName = other.optionalPeopleName().map(StringFilter::copy).orElse(null);
        this.cerfCompanyId = other.optionalCerfCompanyId().map(LongFilter::copy).orElse(null);
        this.feeProdCerfCompanyId = other.optionalFeeProdCerfCompanyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public StringFilter getCompanyNo() {
        return companyNo;
    }

    public Optional<StringFilter> optionalCompanyNo() {
        return Optional.ofNullable(companyNo);
    }

    public StringFilter companyNo() {
        if (companyNo == null) {
            setCompanyNo(new StringFilter());
        }
        return companyNo;
    }

    public void setCompanyNo(StringFilter companyNo) {
        this.companyNo = companyNo;
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

    public StringFilter getTel() {
        return tel;
    }

    public Optional<StringFilter> optionalTel() {
        return Optional.ofNullable(tel);
    }

    public StringFilter tel() {
        if (tel == null) {
            setTel(new StringFilter());
        }
        return tel;
    }

    public void setTel(StringFilter tel) {
        this.tel = tel;
    }

    public StringFilter getAddr() {
        return addr;
    }

    public Optional<StringFilter> optionalAddr() {
        return Optional.ofNullable(addr);
    }

    public StringFilter addr() {
        if (addr == null) {
            setAddr(new StringFilter());
        }
        return addr;
    }

    public void setAddr(StringFilter addr) {
        this.addr = addr;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPeopleName() {
        return peopleName;
    }

    public Optional<StringFilter> optionalPeopleName() {
        return Optional.ofNullable(peopleName);
    }

    public StringFilter peopleName() {
        if (peopleName == null) {
            setPeopleName(new StringFilter());
        }
        return peopleName;
    }

    public void setPeopleName(StringFilter peopleName) {
        this.peopleName = peopleName;
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
        final CompanyCriteria that = (CompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(companyNo, that.companyNo) &&
            Objects.equals(enName, that.enName) &&
            Objects.equals(chName, that.chName) &&
            Objects.equals(tel, that.tel) &&
            Objects.equals(addr, that.addr) &&
            Objects.equals(email, that.email) &&
            Objects.equals(peopleName, that.peopleName) &&
            Objects.equals(cerfCompanyId, that.cerfCompanyId) &&
            Objects.equals(feeProdCerfCompanyId, that.feeProdCerfCompanyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyNo, enName, chName, tel, addr, email, peopleName, cerfCompanyId, feeProdCerfCompanyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCompanyNo().map(f -> "companyNo=" + f + ", ").orElse("") +
            optionalEnName().map(f -> "enName=" + f + ", ").orElse("") +
            optionalChName().map(f -> "chName=" + f + ", ").orElse("") +
            optionalTel().map(f -> "tel=" + f + ", ").orElse("") +
            optionalAddr().map(f -> "addr=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalPeopleName().map(f -> "peopleName=" + f + ", ").orElse("") +
            optionalCerfCompanyId().map(f -> "cerfCompanyId=" + f + ", ").orElse("") +
            optionalFeeProdCerfCompanyId().map(f -> "feeProdCerfCompanyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
